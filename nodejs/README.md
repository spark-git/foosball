This is the node.js implementation of the foosball league manager.


## Some MongoDB Shell commands

* Select Schema: `use node-mongo-foosball`
* Return all players: `db.players.find()`
* Remove players with `first_name` equal to `null`: `db.players.remove({first_name: null})`

# Reference

* http://shapeshed.com/creating-a-basic-site-with-node-and-express/
