package com.algo.day3;

import java.util.*;
import java.io.*;

/*
문제 설명

    물류 창고에 배달 로봇 한 대가 있습니다. 창고를 포함해 총 n개의 지점이 있으며, 0번 지점이 창고입니다.
    로봇은 창고(0번)에서 출발해 나머지 모든 지점을 "정확히 한 번씩" 방문한 뒤, 다시 창고로 돌아와야 합니다.

    지점 i에서 지점 j로 직접 이동하는 데 드는 비용이 2차원 정수 배열 dist로 주어집니다.
    dist[i][j]는 i번 지점에서 j번 지점으로 가는 이동 비용이며, 두 지점 사이에 직접 가는 길이 없는 경우 -1로 주어집니다.
    (dist[i][i] = 0 이고, dist[i][j]와 dist[j][i]가 서로 다를 수 있습니다.)

    로봇이 창고에서 출발해 모든 지점을 한 번씩 방문하고 창고로 돌아오는 경로 중,
    이동 비용의 총합이 최소가 되는 값을 return 하도록 solution 함수를 완성해 주세요.
    모든 지점을 방문하고 돌아오는 경로가 하나도 존재하지 않으면 -1을 return 합니다.

제한사항

    2 ≤ n = dist의 길이 ≤ 16
        dist는 n × n 크기의 정사각 배열입니다.
        모든 i에 대해 dist[i][i] = 0 입니다.
        i ≠ j 인 경우, dist[i][j]는 1 이상 1,000,000 이하의 정수이거나, 길이 없음을 뜻하는 -1 입니다.
    모든 지점은 0번 지점에서 출발해 도달 가능한지 여부를 보장하지 않습니다(불가능한 경우 -1 반환).

입출력 예

    dist                                                        result
    [[0,10,15,20],[10,0,35,25],[15,35,0,30],[20,25,30,0]]       80
    [[0,5,-1],[5,0,-1],[-1,-1,0]]                               -1
    [[0,1],[1,0]]                                               2

입출력 예 설명

    예 1) 0 → 1 → 3 → 2 → 0 경로의 비용은 10 + 25 + 30 + 15 = 80 으로 최소입니다.
    예 2) 2번 지점으로 가거나 2번 지점에서 나오는 길이 전혀 없어 모든 지점을 방문할 수 없으므로 -1 입니다.
    예 3) 0 → 1 → 0, 비용은 1 + 1 = 2 입니다.

힌트
    - n이 최대 16으로 작습니다. "어떤 지점들을 이미 방문했는가"를 비트마스크로 표현해 보세요.
 */

public class Solution2 {

    public static int solution(int[][] dist) {
        /*
        * 1. 0번 출발
        * 2. 모든 노드 1번씩만 방문
        * 3. 최소 비용?
        *
        * 최대 비용 : 1600만 (16 * 100만)
        *
        * 완탐 => 15! (시간초과)
        *
        *
        * */

        int n = dist.length;

        // 플로이드 워샬
        for (int mid = 0; mid < n; mid++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (dist[i][mid] >= 0 && dist[mid][j] >= 0)
                        dist[i][j] = Math.min(dist[i][j], dist[i][mid] + dist[j][i]);

        int dpCSize = (1 << n);
        int[][] dp = new int[dpCSize][n];
        // dp[방문 플래그][마지막으로 방문 위치] 최솟값

        for (int i = 0; i < dpCSize; i++)
            Arrays.fill(dp[i], -1);

        for (int i = 0; i < n; i++) {
            dp[1 << i][0] = dist[i][0];
        }

        for (int i = 0; i < dpCSize; i++) {
            // 이번에 방문
            for (int j = 0; j < n; j++) {
                //이미 해당 노드 방문했으면 생략
                if ((i >> j & 1) == 1) continue;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        int[][][] cases = {
                {{0, 10, 15, 20}, {10, 0, 35, 25}, {15, 35, 0, 30}, {20, 25, 30, 0}},
                {{0, 5, -1}, {5, 0, -1}, {-1, -1, 0}},
                {{0, 1}, {1, 0}},
        };
        int[] expected = {80, -1, 2};

        for (int t = 0; t < cases.length; t++) {
            int got = solution(cases[t]);
            System.out.printf("case %d: got=%d expected=%d -> %s%n",
                    t + 1, got, expected[t], got == expected[t] ? "PASS" : "FAIL");
        }
    }
}

/*
 * 핵심 아이디어: 외판원 순회(TSP) — n≤16이므로 "방문 집합"을 비트마스크로 잡는 Held-Karp DP.
 *   dp[mask][last] = 0에서 출발해 집합 mask를 모두 방문하고 현재 last에 있을 때의 최소 비용.
 * 사용자 풀이 대비 개선점:
 *   1) Floyd-Warshall 불필요/유해: 각 지점을 "정확히 한 번"만 방문해야 하므로 다른 지점을 경유하는
 *      최단경로로 간선을 줄이면 그 경유 지점을 두 번 방문하는 셈 → 직접 간선(dist)만 사용해야 한다.
 *   2) -1(길 없음)은 INF로 취급해야 하며, Math.min에 -1을 그냥 넣으면 안 된다.
 *   3) DP 전이 루프가 비어 있었음 → mask에 next를 추가하는 전이를 채워야 한다.
 * 시간복잡도: O(2^n · n^2)  (n=16 → 약 1.6×10^7). 공간복잡도: O(2^n · n).
 */
class Solution2Answer {
    static final int INF = Integer.MAX_VALUE / 2; // 더하기 오버플로 방지용

    public static int solution(int[][] dist) {
        int n = dist.length, FULL = (1 << n) - 1;
        int[][] dp = new int[1 << n][n];
        for (int[] row : dp) Arrays.fill(row, INF);
        dp[1][0] = 0; // 0번만 방문한 상태, 현재 위치 0

        for (int mask = 1; mask <= FULL; mask++) {
            if ((mask & 1) == 0) continue; // 출발점 0이 빠진 집합은 도달 불가
            for (int last = 0; last < n; last++) {
                if ((mask >> last & 1) == 0 || dp[mask][last] == INF) continue;
                for (int nx = 0; nx < n; nx++) {
                    if ((mask >> nx & 1) == 1) continue;   // 이미 방문
                    if (dist[last][nx] < 0) continue;       // 길 없음
                    int nm = mask | (1 << nx);
                    dp[nm][nx] = Math.min(dp[nm][nx], dp[mask][last] + dist[last][nx]);
                }
            }
        }

        // 모든 지점 방문 후 마지막 지점에서 0번으로 복귀
        int ans = INF;
        for (int last = 0; last < n; last++) {
            if (dp[FULL][last] == INF || dist[last][0] < 0) continue;
            ans = Math.min(ans, dp[FULL][last] + dist[last][0]);
        }
        return ans == INF ? -1 : ans;
    }

    public static void main(String[] args) {
        int[][][] cases = {
                {{0, 10, 15, 20}, {10, 0, 35, 25}, {15, 35, 0, 30}, {20, 25, 30, 0}},
                {{0, 5, -1}, {5, 0, -1}, {-1, -1, 0}},
                {{0, 1}, {1, 0}},
        };
        int[] expected = {80, -1, 2};
        for (int t = 0; t < cases.length; t++) {
            int got = solution(cases[t]);
            System.out.printf("case %d: got=%d expected=%d -> %s%n",
                    t + 1, got, expected[t], got == expected[t] ? "PASS" : "FAIL");
        }
    }
}
