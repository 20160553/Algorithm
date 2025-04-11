#include <iostream>
#include <queue>
#include <algorithm>

using namespace std;

int main() {
    int N, M, R;
    
    cin >> N >> M >> R;
    
    vector<int> edges[N + 1];
    
    for (int i = 0; i < M; i++) {
        int u, v;
        
        cin >> u >> v;
        
        edges[u].push_back(v);
        edges[v].push_back(u);
    }
    
    queue<int> queue;
    bool visited[N + 1]{false};
    int sequence[N + 1]{0};
    int count = 1;
    
    queue.push(R);
    visited[R] = true;
    sequence[R] = count++;
    
    while (!queue.empty()) {
        int vertex = queue.front();
        queue.pop();
        
        sort(edges[vertex].begin(), edges[vertex].end(), less<int>());
        for (int nextVertex : edges[vertex]) {
            if (!visited[nextVertex]) {
                queue.push(nextVertex);
                visited[nextVertex] = true;
                sequence[nextVertex] = count++;
            }
        }
    }
    
    for (int i = 1; i <= N; i++) {
        cout << sequence[i] << '\n';
    }
}