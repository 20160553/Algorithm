import java.util.*;

class Solution {
    public int solution(int n, int[][] edge) {
        /*
        가장 머리 떨어진 노드 : 최단 경로로 이동했을 때 간 선 개수가 가장 많은 노드
        
        간선 : 양방향
        출발점 : 1번 노드
        "다익스트라" 사용
        2. 0 - 1 BFS 사용 가능할 듯
        
        메모리 크기 : 4 * 4 * 10^8
        메모리 초과 날 수도 : 해보고 안되면 맵이나 리스트
        */
        
        Map<Integer, Integer>[] costs = new HashMap[n + 1];
        for (int i = 1; i <= n; i++) {
            costs[i] = new HashMap<Integer, Integer>();
        }
        boolean[] v = new boolean[n + 1];
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((int[] x, int[] y) -> {
            return x[1] - y[1];
        });
        
        int answer = 0, max = 0;
        
        for (int[] e: edge) {
            if (!costs[e[0]].containsKey(e[1]))
                costs[e[0]].put(e[1], 1);
            if (!costs[e[1]].containsKey(e[0]))
                costs[e[1]].put(e[0], 1);
        }
        
        for (int i = 2; i <= n; i++) {
            if (costs[1].containsKey(i)) 
                pq.add(new int[] {i, costs[1].get(i)});
        }
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int dest = current[0], cost = current[1];
            if (v[dest]) continue;
            if (max < cost) {
                max = cost;
                answer = 1;
            } else answer++;
            
            v[dest] = true;
            
            for (int i: costs[dest].keySet()) {
                if (v[i] || i == 1) continue;
                if ((!costs[1].containsKey(i) || costs[1].get(i) > costs[dest].get(i) + cost)) {
                        costs[1].put(i, costs[dest].get(i) + cost);
                        pq.add(new int[] {i, costs[1].get(i)});
                } 
            }
        }
        return answer;
    }
}