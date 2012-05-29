// Mongoose setup
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/node-mongo-foosball');

var Schema = mongoose.Schema;
var ObjectId = Schema.ObjectId;


var Player = new Schema(
    {
        first_name: { type: String },
        last_name: {  type:String },
        created_at : { type: Date },
        updated_at : { type: Date }
    }
);
mongoose.model('Player', Player);

var League = new Schema(
    {
        name    : { type: String }, 
        teams: [Player],
        created_at : { type: Date },
        updated_at : { type: Date }     
    }
);
mongoose.model('League', League);

var player = mongoose.model('Player');

var PlayerProvider = function(){};

// Find all players
PlayerProvider.prototype.findAll = function(callback) {
  player.find({}, function (err, posts) {
    callback( null, posts );
  });
};

// Find player by id.
PlayerProvider.prototype.findById = function(id, callback) {
    player.findById(
        id, 
        function (err, post) {
            if (!err) {
                callback(null, post);
            }
        }
    );
};

// Update player by id.
PlayerProvider.prototype.updateById = function(id, body, callback) {
    player.findById(
        id,
        function (err, player) {
            if (!err) {
                player.first_name = body.first_name;
                player.last_name = body.last_name;
                post.save(function (err) { callback(); });
            }
        }
    );
};

// Create a new player
PlayerProvider.prototype.save = function(form, callback) {
    var p = new player(form);
    p.save(function (err) { callback(); });
};

exports.PlayerProvider = PlayerProvider;
