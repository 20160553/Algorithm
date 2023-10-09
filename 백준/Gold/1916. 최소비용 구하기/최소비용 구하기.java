import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static class Node implements Comparable<Node> {
        int start, dest, cost;

        Node(int start, int dest, int cost){
            this.start = start;
            this.dest = dest;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }

    static int n = 0, m = 0;
    static int[][] costs;
    static boolean[][] v;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        costs = new int[n + 1][n + 1];
        v = new boolean[n + 1][n + 1];
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                costs[i][j] = Integer.MAX_VALUE;

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start, dest, cost;
            start = Integer.parseInt(st.nextToken());
            dest = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());
            costs[start][dest] = Math.min(cost, costs[start][dest]);
        }

        int start, dest;
        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        dest = Integer.parseInt(st.nextToken());

        PriorityQueue<Node> pq = new PriorityQueue<>();

        //다익스트라
        //맨 처음 갈 수 있는 곳 추가
        for (int i = 1; i <= n; i++) {
            if (i == start || costs[start][i] == Integer.MAX_VALUE) continue;
            pq.add(new Node(start, i, costs[start][i]));
        }

        while(!pq.isEmpty()) {
            Node current = pq.poll();
            
            if (v[start][current.dest]) continue;
            v[start][current.dest] = true;
            for (int i = 1; i <= n; i++) {
                if (v[start][i]) continue;
                if (costs[current.dest][i] != Integer.MAX_VALUE && costs[start][current.dest] + costs[current.dest][i] < costs[start][i]) {
                    costs[start][i] = Math.min(costs[start][i], costs[start][current.dest] + costs[current.dest][i]);
                    pq.add(new Node(start, i, costs[start][i]));
                }
            }
        }

        System.out.println(costs[start][dest]);
    }
}