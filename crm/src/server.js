const express = require('express');
require('dotenv').config();
const app = express();
const bodyParser = require('body-parser');
const cors = require('cors')

const dbManager = require('./db-manager');
const {router} = require('./router');
const HTTP_PORT = process.env.HTTP_PORT;


app.use(cors({credentials: true, origin: 'http://localhost:8080'}));
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

dbManager.init();

// Insert here other API endpoints

app.use(router);


app.listen(HTTP_PORT, () => {
    console.log("Server running on port %PORT%".replace("%PORT%", HTTP_PORT))
});

