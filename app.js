const express = require('express');
const app = express();
const mongoClient = require('mongodb').MongoClient;
const URL = "mongodb://localhost:27017";

const AWS = require("aws-sdk");
const awsConfig = {
    "region": "us-west-2",
    "endpoint": "http://dynamodb.us-west-2.amazonaws.com",
    "accessKeyId": "AKIAJCYUA5STOSUGIPWA", "secretAccessKey": "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"
};

AWS.config.update(awsConfig);
const docClient = new AWS.DynamoDB.DocumentClient();

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
                    collection.insertOne(newUser, (__, ___) => {
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

app.post('/queryLatest', (_, res)=> {
    console.log("Querying for last one record of data in <2021/12/3> for device id2.");
    // get current date
    let date_ob = new Date();
    let date = "" + date_ob.getFullYear() + "/" + (date_ob.getMonth() + 1) + "/" + date_ob.getDate();

    var params = {
        TableName : "fakecomp",
        KeyConditionExpression: "id = :id",
        FilterExpression: "begins_with(#dt, :dt)",
        ExpressionAttributeNames:{
            "#dt": "date"
        },
        ExpressionAttributeValues: {
            ":id": "2",
            ":dt": "2021/12/3"
        }
    };

    docClient.query(params, function(error, data) {
        if (error) {
            console.log("Unable to query. Error:", JSON.stringify(error, null, 2));
            res.status(400).send();
        } else {
            console.log("queryLatest success - " + JSON.stringify(data.Items[data.Count - 1], null, 2));
            res.status(200).send(JSON.stringify(data.Items[data.Count - 1]));
        }
    });
    console.log("Query finished.");
});

app.listen(3000, () => {
    console.log("Listening on port 3000...");
});
