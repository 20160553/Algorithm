// your code goes here
const fs = require('fs');

let input = fs.readFileSync('/dev/stdin').toString().trim().split('\n');

input[0] = input[0].trim().split(' ');

let N = parseInt(input[0][0]);
let M = parseInt(input[0][1]);
let R = parseInt(input[0][2]);

let edges = [];

for (let i = 0; i <= N; i++) {
    edges.push([]);
}

for (let i = 1; i <= M; i++) {
    input[i] = input[i].trim().split(' ');
    let u = parseInt(input[i][0]);
    let v = parseInt(input[i][1]);
    
    edges[u].push(v);
    edges[v].push(u);
}

let queue = [];
let visited = Array.from({length: N + 1}, () => false);
let sequences = Array.from({length: N + 1}, () => 0);
let count = 1;

queue.push(R);
visited[R] = true;
sequences[R] = count++;

while (queue.length != 0) {
    let v = queue.shift();
    
    edges[v].sort((a, b) => a - b);
    
    for (let nv of edges[v]) {
        if (!visited[nv]) {
            queue.push(nv);
            visited[nv] = true;
            sequences[nv] = count++;
        }
    }
}

for (let i = 1; i <= N; i++) {
    console.log(sequences[i]);
}