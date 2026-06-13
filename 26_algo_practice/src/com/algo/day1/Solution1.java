package com.algo.day1;

import java.util.*;
import java.io.*;

/*
문제: 야근 도시락 배달

회사에는 N개의 사무실이 있고, 야근 중인 직원들에게 도시락을 배달해야 한다.

배달원은 0번 사무실에서 출발한다.
각 사무실에는 도시락을 원하는 직원이 있을 수도 있고, 없을 수도 있다.

도시락을 원하는 사무실들을 모두 정확히 한 번 이상 방문해야 한다.
단, 같은 사무실을 여러 번 지나가는 것은 가능하다.

사무실 사이에는 이동 통로가 있으며, 각 통로를 지나면 시간이 든다.

문제는 단순히 최단거리만 구하는 것이 아니다.

배달원은 피곤하기 때문에, 도시락을 원하는 사무실을 방문하는 순서를 직접 정해야 한다.
그리고 그 순서가 가능한 모든 경우 중에서 총 이동 시간이 가장 짧은 경우를 찾아야 한다.

입력

첫째 줄에 사무실 수 N, 통로 수 M, 도시락 요청 사무실 수 K가 주어진다.

2 ≤ N ≤ 15
1 ≤ M ≤ N(N-1)/2
1 ≤ K ≤ N-1

둘째 줄에 도시락을 요청한 사무실 번호 K개가 주어진다.
0번 사무실은 요청 사무실에 포함되지 않는다.

이후 M개의 줄에 다음 정보가 주어진다.

A B C

이는 A번 사무실과 B번 사무실 사이를 이동하는 데 C분이 걸린다는 뜻이다.

1 ≤ C ≤ 100

모든 사무실은 서로 이동 가능하다.

출력

도시락을 요청한 모든 사무실을 방문하는 데 필요한 최소 시간을 출력하라.

예시 입력
5 7 3
1 3 4
0 1 4
0 2 2
2 1 1
1 3 5
2 3 8
3 4 3
1 4 7
예시 출력
11
 */

class Edge {
    int start, end, dist;

    public Edge(int start, int end, int dist) {
        this.start = start;
        this.end = end;
        this.dist = dist;
    }
}

public class Solution1 {

    static int dfs(int cRoomIdx, int remains, int cost, List<List<Edge>> edges, int[] v, boolean[] rR) {
        int minCost = Integer.MAX_VALUE;
        if (remains <= 0) {
            return cost;
        }

        for (Edge edge: edges.get(cRoomIdx)) {
            int prev = v[edge.end];
            if (prev <= remains) {
                continue;
            }

            v[edge.end] = remains;
            int result;
            // 도시락 전달 한 경우
            if (rR[edge.end]) {
                rR[edge.end] = false;
                result = dfs(edge.end, remains - 1, cost + edge.dist, edges, v, rR);
                minCost = Math.min(minCost, result);
                rR[edge.end] = true;
            }
            // 도시락 전달 못 한 경우
            result = dfs(edge.end, remains, cost + edge.dist, edges, v, rR);
            minCost = Math.min(minCost, result);
            v[edge.end] = prev;
        }
        return minCost;
    }

    public static void main(String[] args) throws IOException {
        /*
        * 풀이 시간 : 1시간 10분
        * 풀이 결과 : 부분 성공 (시간초과 케이스 존재)
        *
        * 사무실 : N, 통로 : M, 도시락 요청 : K
        * 둘째줄 => 도시락 요청 사무실 번호
        * 셋째줄 시작 사무실, 도착 사무실, 사무실 번호
        * 2 ≤ N ≤ 15
        * 1 ≤ M ≤ N(N-1)/2
        * 1 ≤ K ≤ N-1
        *
        * 사용 알고리즘 : DFS
        *
        * 문제 핵심 : 같은 방 여러번 탐색 가능한데, 이를 어떻게 처리할 거냐
        * 방문 처리 -> 전달 완료한 도시락 수 기준
        *
        * DFS => 각 DFS 완료 전까지 기존 상태 갱신해 나가고, 뎁스 완료 후 그 이후 원상복구
        * DFS는 이론상 대부분의 경로를 탐색 끝내야함
        *
        * */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int start = 0;

        boolean[] requestsRooms = new boolean[n];
        int[] v = new int[n];
        List<List<Edge>> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        Arrays.fill(v, k+1);
        v[0] = k;

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            requestsRooms[Integer.parseInt(st.nextToken())] = true;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            edges.get(s).add(new Edge(s, e, d));
            edges.get(e).add(new Edge(e, s, d));
        }

        int result = dfs(0, k, 0, edges, v, requestsRooms);
        System.out.println(result);
    }
}

/*
 * 모범 답안: Floyd-Warshall + Bitmask DP (TSP)
 *
 * 핵심 아이디어:
 * 1. Floyd-Warshall로 모든 노드 간 최단거리 전처리 (O(N^3))
 * 2. Bitmask DP로 TSP 풀기 (O(K^2 * 2^K))
 *    dp[mask][i] = 요청 사무실 중 mask에 속한 곳을 방문 완료하고
 *                 마지막 위치가 targets[i]일 때의 최소 이동 시간
 *
 * 정답: 11 (0→2→1→3→4, 비용 2+1+5+3)
 * 문제의 예시 출력 9는 사무실 4를 누락한 오류
 */
class Solution1Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        final int INF = Integer.MAX_VALUE / 2;
        int[][] dist = new int[n][n];
        for (int[] row : dist) Arrays.fill(row, INF);
        for (int i = 0; i < n; i++) dist[i][i] = 0;

        int[] targets = new int[k];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) targets[i] = Integer.parseInt(st.nextToken());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            dist[a][b] = Math.min(dist[a][b], c);
            dist[b][a] = Math.min(dist[b][a], c);
        }

        // Floyd-Warshall: 모든 쌍 최단 경로
        for (int mid = 0; mid < n; mid++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (dist[i][mid] < INF && dist[mid][j] < INF)
                        dist[i][j] = Math.min(dist[i][j], dist[i][mid] + dist[mid][j]);

        // Bitmask TSP DP
        int[][] dp = new int[1 << k][k];
        for (int[] row : dp) Arrays.fill(row, INF);
        for (int i = 0; i < k; i++)
            dp[1 << i][i] = dist[0][targets[i]];

        for (int mask = 1; mask < (1 << k); mask++) {
            for (int last = 0; last < k; last++) {
                if (dp[mask][last] == INF || (mask >> last & 1) == 0) continue;
                for (int next = 0; next < k; next++) {
                    if ((mask >> next & 1) == 1) continue;
                    int newCost = dp[mask][last] + dist[targets[last]][targets[next]];
                    dp[mask | (1 << next)][next] = Math.min(dp[mask | (1 << next)][next], newCost);
                }
            }
        }

        int ans = INF;
        for (int i = 0; i < k; i++) ans = Math.min(ans, dp[(1 << k) - 1][i]);
        System.out.println(ans);
    }
}
