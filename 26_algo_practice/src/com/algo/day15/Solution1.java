package com.algo.day15;

/*
Day1 S1 복습

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

import java.util.*;
import java.io.*;

public class Solution1 {
    public static void main(String[] args) throws IOException {
        /*
         * 같은 장소 여러번 지나가는 거 -> 허용됨
         *
         * 총 이동시간이 가장 짧은 경로 선택해야함
         *
         * 양 방향 그래프
         *
         * 마스킹 응용 하는 듯
         * 왜냐 ? 각 방을 방문했을 때 배송 상태에 따라 값을 저장하고 있어야하기 떄문,
         *
         * 그럼 이를 어떻게 해야 표시할 수 있는가?
         * dp[방문한 방 수][마지막 방문한 도시락 필요 사무실 번호]
         *
         * 특정 방 -> 방 까지 최소거리를 모두 산정해놓는 것이 좋을 것 같음 (O(N^3))
         *
         * 그 이후 마스킹 이용 DP
         *
         * */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, m, k;

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[] lunchRoomsIdx = new int[k+1];
        lunchRoomsIdx[0] = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= k; i++) {
            lunchRoomsIdx[i] = Integer.parseInt(st.nextToken());
        }

        int[][] dist = new int[n][n];
        int INF = Integer.MAX_VALUE / 2 - 1;

        // 최소 거리 초기화
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) dist[i][j] = 0;
                else dist[i][j] = INF;
            }
        }

        for (int i = 0; i < m; i++) {
            int[] edge = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            dist[edge[0]][edge[1]] = Math.min(dist[edge[0]][edge[1]], edge[2]);
            dist[edge[1]][edge[0]] = Math.min(dist[edge[1]][edge[0]], edge[2]);
        }

        for (int mid = 0; mid < n; mid++) {
            for (int start = 0; start < n; start++) {
                if (start == mid || dist[start][mid] >= INF) continue;
                for (int end = 0; end < n; end++) {
                    if (mid == end || start == end) continue;
                    dist[start][end] = Math.min(dist[start][end], dist[start][mid] + dist[mid][end]);
                }
            }
        }

        // 마스킹 이용 풀이
        int FILL = 1 << k + 1;
        int[][] dp = new int[FILL][k + 1];

        for (int i = 0; i < FILL; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[1][0] = 0;
        for (int i = 1; i <= k; i++) {
            dp[1 << i][0] = dist[0][lunchRoomsIdx[i]];
        }

        for (int mask = 0; mask < FILL; mask++) {
            for (int next = 1; next <= k; next++) {
                if (((mask >> next) & 1) == 1) continue; // 이미 다음 갈 곳을 갔으면 스킵
                for (int prev = 0; prev <= k; prev++) {
                    int prevIdx = lunchRoomsIdx[prev];
                    int nextIdx = lunchRoomsIdx[next];
                    if (((mask >> prev) & 1) == 0) continue; // 이전 장소 안가본 경우 스킵
                    int nm = mask | (1 << next);
                    int nCost = dp[mask][prev] + dist[prevIdx][nextIdx];
                    dp[nm][next] = Math.min(dp[nm][next], nCost);
                }
            }
        }

        int answer = INF;
        for (int i = 0; i <= k; i++) {
            answer = Math.min(answer, dp[FILL - 1][i]);
        }

        System.out.println(answer);
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
 * 사용자 풀이 대비: 사용자 풀이도 이미 동일한 Floyd-Warshall + Bitmask DP로 정답이다.
 * 다만 mask에 출발지(0번)까지 index 0으로 포함해 K+1 비트를 쓰고,
 * dp[1<<i][0] 초기화(136~138행)는 최종 마스크(FILL-1, 0번 비트 필수)에
 * 도달할 수 없는 사(死)코드다. 모범답안은 요청 사무실 K개만 마스킹하고
 * dp[1<<i][i] = dist[0][targets[i]]로 시작해 더 간결하다.
 *
 * 시간복잡도: O(N^3 + K^2 * 2^K)
 * 공간복잡도: O(N^2 + K * 2^K)
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
