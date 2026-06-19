package com.algo.day6;

import java.util.*;
import java.io.*;

/*
문제: 장을 다 보고 집으로

민수는 동네 사람들의 장보기 심부름을 맡았다.

동네에는 N개의 장소가 있고, 장소 사이에는 M개의 양방향 도로가 있다. 장소는 1번부터 N번까지 번호가 붙어 있으며, 민수의 집은 1번 장소에 있다.

민수가 맡은 심부름은 총 K개이다. 각 심부름은 다음과 같이 이루어진다.

지정된 장소에서 물건을 받은 뒤, 다른 지정된 장소에 가져다준다.

민수의 가방에는 물건을 최대 C개까지만 넣을 수 있다. 이미 전달을 끝낸 물건은 가방에서 사라지므로 자리를 차지하지 않는다.

민수는 집에서 출발해 모든 심부름을 끝낸 뒤 다시 집으로 돌아오려고 한다. 이동 시간을 최소로 만들어 보자.

이동 규칙

각 심부름 i에는 물건을 받는 장소 S_i와 물건을 전달할 장소 E_i가 주어진다.

민수는 다음 규칙에 따라 움직인다.

물건은 반드시 S_i에서 받은 뒤 E_i에 전달해야 한다.
가방에 들어 있는 물건의 수는 항상 C개 이하여야 한다.
물건을 받거나 전달하는 데에는 시간이 걸리지 않는다.
같은 장소에서 여러 물건을 연달아 받거나 전달할 수 있다.
한 장소와 도로를 여러 번 지나가도 된다.
같은 장소가 여러 심부름의 출발지나 도착지로 사용될 수 있다.

모든 심부름을 끝내고 1번 장소로 돌아오는 데 필요한 최소 시간을 구하여라.

불가능하다면 -1을 출력한다.

입력

첫 번째 줄에 장소의 수 N, 도로의 수 M, 심부름의 수 K, 가방에 넣을 수 있는 물건의 최대 개수 C가 주어진다.

N M K C

다음 M개의 줄에는 도로의 정보가 주어진다.

A B T

이는 A번 장소와 B번 장소를 연결하는 양방향 도로가 있으며, 이 도로를 지나는 데 T분이 걸린다는 뜻이다.

다음 K개의 줄에는 심부름의 정보가 주어진다.

S_i E_i

이는 i번 물건을 S_i번 장소에서 받아 E_i번 장소에 전달해야 한다는 뜻이다.

출력

모든 심부름을 끝내고 집으로 돌아오는 데 필요한 최소 시간을 출력한다.

심부름을 모두 끝낼 수 없다면 -1을 출력한다.

제한
2 ≤ N ≤ 100,000
1 ≤ M ≤ 200,000
1 ≤ K ≤ 9
1 ≤ C ≤ K
1 ≤ A, B, S_i, E_i ≤ N
A ≠ B
S_i ≠ E_i
1 ≤ T ≤ 10^9
같은 두 장소를 연결하는 도로가 여러 개 존재할 수 있다.
정답은 64비트 정수 범위 안에 있다.
예제 입력 1
6 7 2 2
1 2 2
2 3 2
3 4 2
4 5 2
5 6 2
1 6 10
2 5 3
2 6
3 5
예제 출력 1
17
예제 설명 1

민수는 다음과 같이 움직일 수 있다.

집에서 2번 장소로 이동한다. 2분
첫 번째 물건을 받는다.
3번 장소로 이동한다. 2분
두 번째 물건을 받는다.
5번 장소로 이동해 두 번째 물건을 전달한다. 4분
6번 장소로 이동해 첫 번째 물건을 전달한다. 2분
1번 장소로 돌아온다. 7분

총 이동 시간은 17분이다.

예제 입력 2
4 2 1 1
1 2 3
3 4 1
2 4
예제 출력 2
-1

2번 장소에서 물건을 받을 수는 있지만, 물건을 전달해야 하는 4번 장소로 이동할 수 없다.
 */

public class Soltuion1 {
    public static void main(String[] args) throws IOException {
        /*
        * 1. 양방향 도로
        *
        * 완탐 절대 아님
        * dp인것 같긴한데?
        *
        * 일단은 모든 지점간 최단거리 필요 - 플로이드 워샬?
        *
        *
        * */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, m, k, c;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        ArrayList<ArrayList<int[]>> roads = new ArrayList<>();
        for (int i = 0; i < n;i++) {
            roads.add(new ArrayList<>());
        }


        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            roads.get(a).add(new int[] {b, cost});
            roads.get(b).add(new int[] {a, cost});
        }

    }
}

/*
 * 핵심 아이디어:
 *  - K ≤ 9 이므로 각 심부름의 상태(0:미수령, 1:수령·미전달, 2:전달완료)를 3진수로 인코딩 → 3^K(≤19683) 상태.
 *  - 의미 있는 위치는 집(1) + 모든 S_i + 모든 E_i (최대 2K+1=19곳)뿐이다.
 *    이 특수 노드들 사이의 최단거리만 미리 구해두면 된다(특수 노드마다 다익스트라 1회).
 *  - dp[mask][pos] = "심부름 상태가 mask이고 현재 pos(특수 노드)에 있을 때의 최소 이동시간".
 *    가방에 든 물건 수 = 상태가 1인 심부름 개수 → 이 값이 항상 C 이하가 되도록 수령 전이를 제한.
 *
 * 사용자 풀이 대비 개선점:
 *  - 사용자는 입력/그래프 구성까지만 작성하고 멈췄다(완탐 불가는 인지). 본 답안은
 *    "특수 노드 간 거리 압축(다익스트라) + 비트(3진)마스크 DP"로 완성한다.
 *  - roads 리스트를 n+1 크기로 만들어 1-based 인덱스 OOB 버그도 제거.
 *
 * 시간복잡도: O((2K+1) · M log N) (다익스트라) + O(3^K · (2K+1) · K) (DP)
 * 공간복잡도: O(N + M + 3^K · (2K+1))
 */
class Soltuion1Answer {
    static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        List<int[]>[] graph = new List[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            graph[a].add(new int[] {b, t});
            graph[b].add(new int[] {a, t});
        }

        int[] start = new int[k], end = new int[k];
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            start[i] = Integer.parseInt(st.nextToken());
            end[i] = Integer.parseInt(st.nextToken());
        }

        // 1) 특수 노드(집 + 모든 S_i, E_i) 수집 후 인덱스 부여
        LinkedHashMap<Integer, Integer> idx = new LinkedHashMap<>();
        idx.put(1, idx.size());                  // 집은 항상 인덱스 0
        for (int i = 0; i < k; i++) {
            idx.putIfAbsent(start[i], idx.size());
            idx.putIfAbsent(end[i], idx.size());
        }
        int sp = idx.size();
        int[] nodes = new int[sp];
        for (Map.Entry<Integer, Integer> e : idx.entrySet()) nodes[e.getValue()] = e.getKey();

        // 2) 특수 노드 간 최단거리 압축 (각 특수 노드에서 다익스트라 1회)
        long[][] dist = new long[sp][sp];
        for (int s = 0; s < sp; s++) {
            long[] d = dijkstra(graph, n, nodes[s]);
            for (int t = 0; t < sp; t++) dist[s][t] = d[nodes[t]];
        }

        // 3) 3진 마스크 DP
        int[] pow3 = new int[k + 1];
        pow3[0] = 1;
        for (int i = 1; i <= k; i++) pow3[i] = pow3[i - 1] * 3;
        int full = pow3[k] - 1;                  // 모든 자리 = 2 (전부 전달완료)

        // sIdx[i]/eIdx[i] = i번 심부름의 수령/전달 장소의 특수 노드 인덱스
        int[] sIdx = new int[k], eIdx = new int[k];
        for (int i = 0; i < k; i++) { sIdx[i] = idx.get(start[i]); eIdx[i] = idx.get(end[i]); }

        long[][] dp = new long[full + 1][sp];
        for (long[] row : dp) Arrays.fill(row, INF);
        dp[0][0] = 0;                            // 상태 0(전부 미수령), 집에서 출발

        for (int mask = 0; mask <= full; mask++) {
            // 현재 가방에 든 물건 수 = 상태가 1인 심부름 개수
            int carrying = 0;
            for (int i = 0; i < k; i++) if ((mask / pow3[i]) % 3 == 1) carrying++;
            for (int pos = 0; pos < sp; pos++) {
                long cur = dp[mask][pos];
                if (cur >= INF) continue;
                for (int i = 0; i < k; i++) {
                    int state = (mask / pow3[i]) % 3;
                    if (state == 0 && carrying < c) {            // 수령: S_i 로 이동 (0→1)
                        long nd = dist[pos][sIdx[i]];
                        if (nd < INF) relax(dp, mask + pow3[i], sIdx[i], cur + nd);
                    } else if (state == 1) {                      // 전달: E_i 로 이동 (1→2)
                        long nd = dist[pos][eIdx[i]];
                        if (nd < INF) relax(dp, mask + pow3[i], eIdx[i], cur + nd);
                    }
                }
            }
        }

        // 4) 모든 심부름 완료 후 집(인덱스 0)으로 복귀
        long ans = INF;
        for (int pos = 0; pos < sp; pos++) {
            if (dp[full][pos] >= INF || dist[pos][0] >= INF) continue;
            ans = Math.min(ans, dp[full][pos] + dist[pos][0]);
        }
        System.out.println(ans >= INF ? -1 : ans);
    }

    static void relax(long[][] dp, int mask, int pos, long val) {
        if (val < dp[mask][pos]) dp[mask][pos] = val;
    }

    static long[] dijkstra(List<int[]>[] graph, int n, int src) {
        long[] d = new long[n + 1];
        Arrays.fill(d, INF);
        d[src] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.add(new long[] {src, 0});
        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            int u = (int) top[0];
            long du = top[1];
            if (du > d[u]) continue;
            for (int[] e : graph[u]) {
                long nd = du + e[1];
                if (nd < d[e[0]]) { d[e[0]] = nd; pq.add(new long[] {e[0], nd}); }
            }
        }
        return d;
    }
}
