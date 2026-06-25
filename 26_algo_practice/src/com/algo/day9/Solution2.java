package com.algo.day9;

/*
2098번 — 외판원 순회
문제

1번부터 N번까지 도시가 있으며, 도시 사이를 이동할 때 비용이 발생한다. 일부 도시 사이에는 이동 가능한 길이 없을 수도 있다.

한 도시에서 출발하여 다음 조건을 만족하는 순회 경로를 구한다.

모든 도시를 정확히 한 번씩 방문한다.
모든 도시를 방문한 후 출발한 도시로 돌아온다.
방문했던 도시는 다시 방문할 수 없다. 단, 마지막에 출발 도시로 돌아가는 것은 허용된다.

가능한 순회 경로 중 이동 비용의 합이 최소가 되는 값을 구해야 한다.

입력

첫째 줄에 도시의 수 N이 주어진다.

2 ≤ N ≤ 16

다음 N개의 줄에 비용 행렬 W가 주어진다.

W[i][j]: 도시 i에서 도시 j로 이동하는 비용
이동할 수 없는 경우 W[i][j] = 0
그 외의 비용은 1,000,000 이하의 양의 정수
항상 모든 도시를 순회할 수 있는 입력만 주어진다.
출력

외판원이 모든 도시를 순회하고 출발 도시로 돌아오는 데 필요한 최소 비용을 출력한다.

예제 입력
4
0 10 15 20
5 0 9 10
6 13 0 12
8 8 9 0
예제 출력
35
 */

import java.util.*;
import java.io.*;

public class Solution2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][];

        for (int i = 0; i < n; i++) {
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int FILL = (1 << n);
        int INF = Integer.MAX_VALUE / 2;
        int[][] costs = new int[FILL][n];
        for (int[] cost : costs) {
            Arrays.fill(cost, INF);
        }
        Arrays.fill(costs[0], 0);

        for (int i = 0; i < n; i++) {
            costs[1 << (i)][i] = 0;
        }

        for (int last = 0; last < n; last++) {
            for (int mask = 0; mask < FILL; mask++) {
                if (((mask >> last) & 1) != 1) continue; // 이전 방문 위치 방문 못 한 경우 건너 뜀
                for (int next = 0; next < n; next++) {
                    if (((mask >> next) & 1) == 1) continue; // 다음 방문 예정 방문한 경우 건너 뜀
                    int nm = mask | (1 << next);
                    int prevCost = costs[mask][last];
                    if (prevCost >= INF) continue;
                    int nCost = prevCost + map[last][next];
                    costs[nm][next] = Math.min(costs[nm][next], nCost);
                }
            }
        }

        int answer = INF;

        for (int last = 0; last < n; last++) {
            for (int start = 0; start < n; start++) {
                if (last == start) continue;
                answer = Math.min(answer, costs[FILL - 1][last] + map[last][start]);
            }
        }

        System.out.println(answer);

    }
}

/*
 * 핵심 아이디어: 비트마스크 TSP DP. 출발 도시를 0번으로 고정한다.
 *   dp[mask][last] = 0번에서 출발해 mask 집합을 모두 방문하고 last에 있는 최소 비용.
 *   순회(cycle)는 어느 도시에서 시작해도 같으므로 시작점을 0으로 고정해도 일반성을 잃지 않으며,
 *   "돌아오는 간선"은 반드시 출발지 0으로 가야 한다 → 마지막에 + map[last][0].
 *
 * 사용자 풀이 대비 개선점:
 *   1) (치명적) 사용자는 시작점을 고정하지 않고 모든 i에 대해 costs[1<<i][i]=0으로 두었다.
 *      그 결과 마지막에 map[last][start]의 start를 "임의 도시"로 골라버려, 실제 출발지로
 *      돌아오지 않는 비-순환 경로까지 후보가 되어 답을 과소평가한다.
 *      (예제는 우연히 35로 맞지만, 0번으로 들어오는 간선만 비싼 입력에서 정답 1003 대신 4 출력)
 *      → 시작점을 0으로 고정하고 닫는 간선은 항상 map[last][0]으로 보내야 한다.
 *   2) W[i][j]==0(길 없음)을 비용 0으로 더해버리는 버그. 없는 길은 INF로 건너뛴다.
 *
 * 시간복잡도: O(2^N · N^2)   (N=16 → 약 1.7×10^7)
 * 공간복잡도: O(2^N · N)
 */
class Solution2Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());
        int[][] map = new int[n][];
        for (int i = 0; i < n; i++) {
            map[i] = Arrays.stream(br.readLine().trim().split("\\s+"))
                    .mapToInt(Integer::parseInt).toArray();
        }

        int FULL = 1 << n;
        int INF = Integer.MAX_VALUE / 2;
        int[][] dp = new int[FULL][n];
        for (int[] row : dp) Arrays.fill(row, INF);

        dp[1][0] = 0; // 출발 도시 0만 방문한 상태, 비용 0

        for (int mask = 1; mask < FULL; mask++) {
            if ((mask & 1) == 0) continue; // 출발지 0을 포함하지 않는 상태는 무효
            for (int last = 0; last < n; last++) {
                if (((mask >> last) & 1) == 0) continue;
                int cur = dp[mask][last];
                if (cur >= INF) continue;
                for (int next = 0; next < n; next++) {
                    if (((mask >> next) & 1) == 1) continue;
                    if (map[last][next] == 0) continue; // 길 없음 → 이동 불가
                    int nm = mask | (1 << next);
                    int nCost = cur + map[last][next];
                    if (nCost < dp[nm][next]) dp[nm][next] = nCost;
                }
            }
        }

        int answer = INF;
        for (int last = 0; last < n; last++) {
            if (map[last][0] == 0) continue;        // 출발지 0으로 돌아오는 길이 없으면 불가
            if (dp[FULL - 1][last] >= INF) continue;
            answer = Math.min(answer, dp[FULL - 1][last] + map[last][0]);
        }

        System.out.println(answer);
    }
}
