This is the node.js implementation of the foosball league manager.

## Set up the app
### Set up MongoDB

* Download mongodb, and unzip it
* Create a folder to store the data, e.g. C:/tmp/data
* Start mongodb server:

    mongod --dbpath C:/tmp/data

### Set up the Node.js app

* Start server:
    nodejs app.js

### set up sass

* Download sass
* go to the views directory
* run the command: 
		sass --watch main.scss:main.css


## Some MongoDB Shell commands

* Select Schema: `use node-mongo-foosball`
* Return all players: `db.players.find()`
* Remove players with `first_name` equal to `null`: `db.players.remove({first_name: null})`

# Reference

* http://shapeshed.com/creating-a-basic-site-with-node-and-express/
* http://howtonode.org/express-mongodb

# Openid

* using passport-openid for nodejs
* the google openid is http://www.google.com/accounts/o8/id

## Useful Examples

* https://github.com/cmarin/MongoDB-Node-Express-Blog


## Libraries used

* Nodejs: http://nodejs.org/
* Express: http://expressjs.com/
* Stylus: http://learnboost.github.com/stylus/
* Jade: https://github.com/arden/jade-js
* MongoDB: http://www.mongodb.org/