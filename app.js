const express = require('express');
const app = express();
const mongoClient = require('mongodb').MongoClient;

const URL = "mongodb://localhost:27017";

app.use(express.json());

mongoClient.connect(URL, (err, db) => {

    if (err) {
        console.log("Error while connecting mongo client");
    } else {

        const myDb = db.db('AppLocalDb');
        const collection = myDb.collection('UserTable');

        app.post('/signup', (req, res) => {

            const newUser = {
                _id: req.body.email,
                name: req.body.name,
                password: req.body.password
            };

            const query = { _id: newUser._id };

            collection.findOne(query, (_, result) => {

                if (result == null) {
                    collection.insertOne(newUser, (_, __) => {
                        res.status(200).send();
                    });
                } else {
                    res.status(400).send();
                }

            });

        });

        app.post('/login', (req, res) => {

            const query = {
                _id: req.body.email,
                password: req.body.password
            };

            collection.findOne(query, (_, result) => {

                if (result != null) {

                    const objToSend = {
                        name: result.name,
                        email: result._id
                    }

                    res.status(200).send(JSON.stringify(objToSend));

                } else {
                    res.status(404).send();
                }

            });

        });

    }

});

app.listen(3000, () => {
    console.log("Listening on port 3000...");
});