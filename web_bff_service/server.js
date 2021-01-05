'use strict';

const http = require('http');
const express = require('express');
const fs = require('fs');

// Constants
const PORT = 49160;
const HOST = '0.0.0.0';
const TASK_SERVICE_ADDRESS = 'http://task-app:8080/tasks/';

// App
const app = express();

app.use(express.static("public"));

app.get('/', (req, res) => {
    fs.readFile("./index.html", "UTF-8", function (err, html) {
        res.writeHead(200, { "Content-Type": "text/html" });
        res.end(html);
    })
});

app.get('/tasks', (clientReq, clientRes) => {

    http.get(TASK_SERVICE_ADDRESS, (res) => {

        let data = '';

        res.on('data', (chunk) => {
            data += chunk;
        });

        res.on('end', () => {
            console.log('Returning: ' + data);
            clientRes.writeHead(200, { "Content-Type": "application/json" });
            clientRes.end(data);
        });

    }).on("error", (err) => {
        console.log("Error: " + err.message);
    })
});

app.listen(PORT, HOST, () => {
    console.log(`Running on http://${HOST}:${PORT}`);
});