package com.algo.day14;
/*
네트워크 복구
문제

N(1 ≤ N ≤ 1,000)개의 컴퓨터로 구성된 네트워크가 있다. 이들 중 몇 개의 컴퓨터들은 서로 네트워크 연결이 되어 있어 서로 다른 두 컴퓨터 간 통신이 가능하도록 되어 있다.
통신을 할 때에는 서로 직접 연결되어 있는 회선을 이용할 수도 있으며, 회선과 다른 컴퓨터를 거쳐서 통신을 할 수도 있다.

각 컴퓨터들과 회선은 그 성능이 차이가 날 수 있다. 따라서 각각의 직접 연결되어 있는 회선을 이용해서 통신을 하는데 걸리는 시간이 서로 다를 수 있다.
심지어는 직접 연결되어 있는 회선이 오히려 더 느려서, 다른 컴퓨터를 통해서 통신을 하는 것이 더 유리할 수도 있다. 직접 연결되어 있는 회선을 사용할 경우에는 그 회선을 이용해서 통신을 하는 데 드는 시간만큼이 들게 된다.
여러 개의 회선을 거치는 경우에는 각 회선을 이용해서 통신을 하는 데 드는 시간의 합만큼의 시간이 걸리게 된다.

어느 날, 해커가 네트워크에 침입하였다. 네트워크의 관리자는 우선 모든 회선과 컴퓨터를 차단한 후, 해커의 공격을 막을 수 있었다.
관리자는 컴퓨터에 보안 시스템을 설치하려 하였는데, 버전 문제로 보안 시스템을 한 대의 슈퍼컴퓨터에만 설치할 수 있었다. 한 컴퓨터가 공격을 받게 되면, 네트워크를 통해 슈퍼컴퓨터에 이 사실이 전달이 되고,
그러면 슈퍼컴퓨터에서는 네트워크를 이용해서 보안 패킷을 전송하는 방식을 사용하기로 하였다. 준비를 마친 뒤, 관리자는 다시 네트워크를 복구하기로 하였다. 이때, 다음의 조건들이 만족되어야 한다.

해커가 다시 공격을 할 우려가 있기 때문에, 최소 개수의 회선만을 복구해야 한다. 물론, 그렇다면 아무 회선도 복구하지 않으면 되겠지만, 이럴 경우 네트워크의 사용에 지장이 생기게 된다.
따라서 네트워크를 복구한 후에 서로 다른 두 컴퓨터 간에 통신이 가능하도록 복구해야 한다.
네트워크를 복구해서 통신이 가능하도록 만드는 것도 중요하지만, 해커에게 공격을 받았을 때 보안 패킷을 전송하는 데 걸리는 시간도 중요한 문제가 된다.
따라서 슈퍼컴퓨터가 다른 컴퓨터들과 통신하는데 걸리는 최소 시간이, 원래의 네트워크에서 통신하는데 걸리는 최소 시간보다 커져서는 안 된다.

원래의 네트워크에 대한 정보가 주어졌을 때, 위의 조건을 만족하면서 네트워크를 복구하는 방법을 알아내는 프로그램을 작성하시오.

입력

첫째 줄에 두 정수 N, M이 주어진다. 다음 M개의 줄에는 회선의 정보를 나타내는 세 정수 A, B, C가 주어진다. 이는 A번 컴퓨터와 B번 컴퓨터가 통신 시간이 C (1 ≤ C ≤ 10)인 회선으로 연결되어 있다는 의미이다.
컴퓨터들의 번호는 1부터 N까지의 정수이며, 1번 컴퓨터는 보안 시스템을 설치할 슈퍼컴퓨터이다. 모든 통신은 완전쌍방향 방식으로 이루어지기 때문에, 한 회선으로 연결된 두 컴퓨터는 어느 방향으로도 통신할 수 있다.

출력

첫째 줄에 복구할 회선의 개수 K를 출력한다. 다음 K개의 줄에는 복구할 회선을 나타내는 두 정수 A, B를 출력한다. 이는 A번 컴퓨터와 B번 컴퓨터를 연결하던 회선을 복구한다는 의미이다.
출력은 임의의 순서대로 하며, 답이 여러 개 존재하는 경우에는 아무 것이나 하나만 출력하면 된다.
 */

import java.util.*;
import java.io.*;

public class Solution1 {

    static int find(int a, int[] p) {
        if (p[a] == a) return a;
        return p[a] = find(p[a], p);
    }

    /// union 시 true 안했을 시 false
    static boolean union(int a, int b, int[] p) {
        a = find(a, p);
        b = find(b, p);
        if (a == b) return false;
        p[b] = a;
        return true;
    }

    public static void main(String[] args) throws IOException {
        /*
         * 모든 정점을 최소 비용으로 연결하는 MST.
         *
         * Union-find 사용. 사이클 생기면 스킵
         *
         * 1. 간선 비용순으로 정렬
         * 2. 사이클 안 생길 경우 연결
         *
         * */

        int n, m;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[] p = new int[n + 1];
        ArrayList<int[]> edges = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (int i = 0; i < m; i++) {
            int[] edge = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            edges.add(new int[] {edge[1], edge[2]});
        }

        Collections.sort(edges, Comparator.comparingInt(o -> o[2]));

        int cnt = 0;
        StringBuilder sb = new StringBuilder();

        for (int[] e: edges) {
            if (union(e[0], e[1], p)) {
                cnt++;
                sb.append(e[0] + " " + e[1] + "\n");
            }
        }
        System.out.println(cnt);
        System.out.println(sb);
    }
}

/*
 * 핵심 아이디어: 이 문제는 MST가 아니라 "최단경로 트리(Shortest Path Tree)"다.
 *   복구 후 1번(슈퍼컴퓨터)에서 각 정점까지의 최단시간이 원래 그래프와 같아야 하고,
 *   회선 수는 최소(=N-1, 트리)여야 한다. 1번에서 Dijkstra로 dist[]를 구한 뒤,
 *   각 정점 v마다 dist[u] + w(u,v) == dist[v]를 만족하는 간선 하나만 채택하면
 *   최단거리를 보존하는 스패닝 트리가 된다.
 *
 * 사용자 풀이 대비: 간선 비용 총합을 최소화하는 MST(Kruskal)는 1번 기준 최단거리를
 *   보존하지 못해 오답이다. (예: 우회로가 더 싸도 1번까지 거리는 늘 수 있음)
 *
 * 시간복잡도: O((N + M) log N)   /   공간복잡도: O(N + M)
 */
class Solution1Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<int[]>[] adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            adj[a].add(new int[] {b, c});
            adj[b].add(new int[] {a, c});
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        pq.add(new int[] {1, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0], d = cur[1];
            if (d > dist[u]) continue; // 이미 더 짧은 경로로 확정된 정점은 스킵
            for (int[] e : adj[u]) {
                int nd = d + e[1];
                if (nd < dist[e[0]]) {
                    dist[e[0]] = nd;
                    pq.add(new int[] {e[0], nd});
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        // 각 정점마다 "이 정점의 최단거리를 만든 간선" 하나씩만 복구한다.
        for (int v = 2; v <= n; v++) {
            for (int[] e : adj[v]) {
                int u = e[0];
                if (dist[u] != Integer.MAX_VALUE && dist[u] + e[1] == dist[v]) {
                    sb.append(u).append(' ').append(v).append('\n');
                    cnt++;
                    break;
                }
            }
        }
        System.out.println(cnt);
        System.out.print(sb);
    }
}
