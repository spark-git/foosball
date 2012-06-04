// Module dependencies
var express = require('express')
  , passport = require('passport')
  , util = require('util')
  , OpenIDStrategy = require('passport-openid').Strategy;
  
  
var routes = require('./routes');

var app = module.exports = express.createServer();

// Passport session setup.
//   To support persistent login sessions, Passport needs to be able to
//   serialize users into and deserialize users out of the session.  Typically,
//   this will be as simple as storing the user ID when serializing, and finding
//   the user by ID when deserializing.  However, since this example does not
//   have a database of user records, the OpenID identifier is serialized and
//   deserialized.
passport.serializeUser(function(user, done) {
  done(null, user.identifier);
});

passport.deserializeUser(function(identifier, done) {
  done(null, { identifier: identifier });
});

// Use the OpenIDStrategy within Passport.
//   Strategies in passport require a `validate` function, which accept
//   credentials (in this case, an OpenID identifier), and invoke a callback
//   with a user object.
passport.use(new OpenIDStrategy({
    returnURL: 'http://localhost:3000/auth/openid/return',
    realm: 'http://localhost:3000/'
  },
  function(identifier, done) {
    // asynchronous verification, for effect...
    process.nextTick(function () {
      
      // To keep the example simple, the user's OpenID identifier is returned to
      // represent the logged-in user.  In a typical application, you would want
      // to associate the OpenID identifier with a user record in your database,
      // and return that user instead.
      return done(null, { identifier: identifier })
    });
  }
));

// Configuration

app.configure(
    function(){
        app.set('views', __dirname + '/views');
//        app.set('view engine', 'jade');
		app.set('view engine', 'handlebars');
	    app.set("view options", { layout: false }) 
        app.register('.hbs', require('handlebars'));
        app.use(express.bodyParser());
        app.use(express.methodOverride());
        app.use(require('stylus').middleware({ src: __dirname + '/public' }));
        
        app.use(express.logger());
	    app.use(express.cookieParser());
        app.use(express.bodyParser());
        app.use(express.methodOverride());
	    app.use(express.session({ secret: 'keyboard cat' }));
	    // Initialize Passport!  Also use passport.session() middleware, to support
	    // persistent login sessions (recommended).
		app.use(passport.initialize());
	    app.use(passport.session());  
        
        app.use(app.router);
        app.use("/public", express.static(__dirname + '/public'));
    }
);

app.configure(
    'development',
    function() {
        app.use(express.errorHandler({ dumpExceptions: true, showStack: true }));
    }
);

app.configure(
    'production',
    function() {
        app.use(express.errorHandler());
    }
);

var PlayerProvider = require('./playerprovider').PlayerProvider;
var playerProvider = new PlayerProvider();


// Routes
app.get(
    '/home', ensureAuthenticated, 
    function(req, res) {
        playerProvider.findAll(
            function(error, docs) {
                res.render('home.hbs', { locals: { title: 'Foosball', players: docs}});
            }
        );
    }
);

app.get(
    '/', 
    function(req, res) {
        playerProvider.findAll(
            function(error, docs) {
                res.render('login.hbs', { locals: { title: 'Foosball', players: docs, user: req.user}});
            }
        );
    }
);

app.post(
    '/player/new',
    function(req, res) {
        var player = { first_name: req.param('first_name', null), last_name: req.param('last_name', null) };
        playerProvider.save(
            player,
            function(error, docs) {
                res.redirect('/');
            }
        );
    }
);

app.get('/account', ensureAuthenticated, function(req, res){
  res.render('account', { user: req.user });
});

app.get('/login', function(req, res){
  res.render('login', { user: req.user });
});

// POST /auth/openid
//   Use passport.authenticate() as route middleware to authenticate the
//   request.  The first step in OpenID authentication will involve redirecting
//   the user to their OpenID provider.  After authenticating, the OpenID
//   provider will redirect the user back to this application at
//   /auth/openid/return
app.post('/auth/openid', 
  passport.authenticate('openid', { failureRedirect: '/login' }),
  function(req, res) {
    res.redirect('/home');
  });

// GET /auth/openid/return
//   Use passport.authenticate() as route middleware to authenticate the
//   request.  If authentication fails, the user will be redirected back to the
//   login page.  Otherwise, the primary route function function will be called,
//   which, in this example, will redirect the user to the home page.
app.get('/auth/openid/return', 
  passport.authenticate('openid', { failureRedirect: '/login' }),
  function(req, res) {
    res.redirect('/home');
  });

app.get('/logout', function(req, res){
  req.logout();
  res.redirect('/');
});

app.get(
    '/player/:id',
    ensureAuthenticated,
    function(req, res) {
        playerProvider.findById(
            req.param('id'),
            function(error, docs) {
                res.render('player.hbs', { locals: { title: 'Foosball', player: docs }});
            }
        );
    }
);


app.listen(
    3000, 
    function() {
        console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
    }
);

// Simple route middleware to ensure user is authenticated.
//   Use this route middleware on any resource that needs to be protected.  If
//   the request is authenticated (typically via a persistent login session),
//   the request will proceed.  Otherwise, the user will be redirected to the
//   login page.  Google openid https://www.google.com/accounts/o8/id
function ensureAuthenticated(req, res, next) {
  if (req.isAuthenticated()) { return next(); }
  res.redirect('/')
}
