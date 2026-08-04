const express = require('express');
const redis = require('redis');

const app = express();
const client = redis.createClient({ url: 'redis://redis:6379' });

client.connect();

app.get('/', async (req, res) => {
    const count = await client.incr('counter');
    res.send(`Page viewed ${count} times`);
});

app.listen(3000, () => {
    console.log('App is running on http://localhost:3000');
});

