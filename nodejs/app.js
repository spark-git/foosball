// Module dependencies
var express = require('express');
var routes = require('./routes');

var app = module.exports = express.createServer();

// Configuration

app.configure(
    function(){
        app.set('views', __dirname + '/views');
        app.set('view engine', 'jade');
        app.use(express.bodyParser());
        app.use(express.methodOverride());
        app.use(require('stylus').middleware({ src: __dirname + '/public' }));
        app.use(app.router);
        app.use(express.static(__dirname + '/public'));
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
    '/', 
    function(req, res) {
        playerProvider.findAll(
            function(error, docs) {
                res.render('index.jade', { locals: { title: 'Foosball', players: docs}});
            }
        );
    }
);

app.post(
    '/player/new',
    function(req, res) {
        console.log(req.body.user);
        var player = { first_name: req.param('first_name', null), last_name: req.param('last_name', null) };
        playerProvider.save(
            player,
            function(error, docs) {
                res.redirect('/');
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
