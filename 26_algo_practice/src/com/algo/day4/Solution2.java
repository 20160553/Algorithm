package com.algo.day4;

import java.util.*;
import java.io.*;

/*
문제 설명

    어느 도시에 n개의 관광지가 있고, 0번부터 n-1번까지 번호가 매겨져 있습니다.
    한 명의 가이드가 모든 관광지를 "정확히 한 번씩" 걸어서 둘러보는 도보 투어를 계획합니다.

    이번 투어는 day3의 배달 로봇과 달리 출발지와 도착지가 자유롭습니다.
    즉, 어느 관광지에서 시작해도 되고, 마지막 관광지에서 멈추면 끝입니다. (출발지로 되돌아오지 않습니다.)

    지점 i에서 지점 j로 직접 걸어가는 데 드는 시간이 2차원 정수 배열 dist로 주어집니다.
    dist[i][j]는 i번에서 j번으로 가는 이동 시간이며, 두 지점 사이에 직접 가는 길이 없으면 -1로 주어집니다.
    (dist[i][i] = 0 이고, dist[i][j]와 dist[j][i]가 서로 다를 수 있습니다 — 일방통행/오르막 등.)

    모든 관광지를 한 번씩 방문하는 경로 중, 이동 시간의 총합이 최소가 되는 값을 return 하도록
    solution 함수를 완성해 주세요.
    모든 지점을 한 번씩 방문하는 경로가 하나도 존재하지 않으면 -1을 return 합니다.

제한사항

    2 ≤ n = dist의 길이 ≤ 16
        dist는 n × n 크기의 정사각 배열입니다.
        모든 i에 대해 dist[i][i] = 0 입니다.
        i ≠ j 인 경우, dist[i][j]는 1 이상 1,000,000 이하의 정수이거나, 길이 없음을 뜻하는 -1 입니다.
    출발지로 되돌아오는 간선은 더하지 않습니다(열린 경로).

입출력 예

    dist                                                        result
    [[0,10,15,20],[10,0,35,25],[15,35,0,30],[20,25,30,0]]       50
    [[0,5,-1],[5,0,-1],[-1,-1,0]]                               -1
    [[0,1],[1,0]]                                               1

입출력 예 설명

    예 1) 3 → 1 → 0 → 2 경로의 시간은 25 + 10 + 15 = 50 으로 최소입니다.
          (day3의 복귀형 답 80과 달리, 마지막에 출발지로 돌아오지 않으므로 더 짧습니다.)
    예 2) 2번 지점으로 가거나 2번 지점에서 나오는 길이 전혀 없어 모든 지점을 방문할 수 없으므로 -1 입니다.
    예 3) 0 → 1, 시간은 1 입니다. (복귀하지 않으므로 1)

힌트
    - n ≤ 16. "어떤 지점들을 이미 방문했는가"를 비트마스크로 표현하는 Held-Karp DP를 떠올려 보세요.
    - day3(복귀형)과 비교해 (1) 시작 상태를 어떻게 잡을지, (2) 마지막에 복귀 간선을 더할지를 다시 생각해 보세요.
 */

public class Solution2 {

    public static int solution(int[][] dist) {
        /*
         * 1. 출발지 자유 → 시작 상태를 어떻게?
         * 2. 모든 노드 1번씩만 방문
         * 3. 복귀 없음 → 마지막 처리가 day3과 어떻게 다른가?
         * 4. 최소 비용?
         *
         * 완탐 => n! (시간초과)
         *
         * MST => 양방향이 아닌 단방향이라 사용 불가
         *
         *
         *
         * */

        int n = dist.length;
        int FULL = 1 << n;
        int INF = Integer.MAX_VALUE / 2;
        int[][] dp = new int[1 << n][n];
        int ans = INF;

        for (int start = 0; start < n; start++) {
            for (int i = 0; i < FULL; i++)
                Arrays.fill(dp[i], INF);
            dp[1 << start][start] = 0;
            for (int mask = 1; mask < FULL; mask++) {
                for (int last = 0; last < n; last++) {
                    if ((mask >> last & 1) != 1 || dp[mask][last] == INF) continue;
                    for (int nx = 0; nx < n; nx++) {
                        if ((mask >> nx & 1) == 1) continue;
                        int nm = mask | 1 << nx;
                        if (dist[last][nx] < 0) continue;
                        dp[nm][nx] = Math.min(dp[nm][nx], dp[mask][last] + dist[last][nx]);
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                ans = Math.min(ans, dp[FULL - 1][i]);
            }
        }
        if (ans == INF) return -1;
        return ans;
    }

    public static void main(String[] args) {
        int[][][] cases = {
                {{0, 10, 15, 20}, {10, 0, 35, 25}, {15, 35, 0, 30}, {20, 25, 30, 0}},
                {{0, 5, -1}, {5, 0, -1}, {-1, -1, 0}},
                {{0, 1}, {1, 0}},
        };
        int[] expected = {50, -1, 1};

        for (int t = 0; t < cases.length; t++) {
            int got = solution(cases[t]);
            System.out.printf("case %d: got=%d expected=%d -> %s%n",
                    t + 1, got, expected[t], got == expected[t] ? "PASS" : "FAIL");
        }
    }
}

/*
 * 핵심 아이디어: 열린 경로형 TSP — Held-Karp 비트마스크 DP.
 *   dp[mask][last] = 집합 mask를 모두 방문하고 현재 last에 있을 때의 최소 비용
 *                    (시작점은 mask 안의 "어떤 지점이든" 상관없음).
 *   복귀형(day3)과의 차이는 단 두 곳: (1) 시작 상태를 모든 i에 대해 dp[1<<i][i]=0 으로 깔고,
 *   (2) 마지막에 복귀 간선을 더하지 않고 dp[FULL][last] 중 최솟값을 답으로 쓴다.
 *
 * 사용자 풀이 대비 개선점:
 *   - 사용자는 시작점마다 DP 전체를 다시 돌리는 `for(start)` 외부 루프를 둬서 O(2^n·n^3)이 됐다.
 *     여러 시작 상태를 "한 번에" 깔면(dp[1<<i][i]=0 동시 시드) 같은 답을 O(2^n·n^2)로 얻는다.
 *     dp[mask][last]는 이미 "mask 안의 어딘가에서 출발"을 함의하므로 시작점을 분리할 필요가 없다.
 *   - 결과는 동일하게 정답. 정확성 문제는 없고 순전히 중복 계산(시간/공간 재초기화) 제거.
 *
 * 시간복잡도: O(2^n · n^2)   (n=16 → 약 1.6×10^7)
 * 공간복잡도: O(2^n · n)
 */
class Solution2Answer {
    static final int INF = Integer.MAX_VALUE / 2; // 더하기 오버플로 방지용

    public static int solution(int[][] dist) {
        int n = dist.length, FULL = (1 << n) - 1;
        int[][] dp = new int[1 << n][n];
        for (int[] row : dp) Arrays.fill(row, INF);

        // 출발점이 자유 → 모든 단일 지점 상태를 한 번에 시드
        for (int i = 0; i < n; i++) dp[1 << i][i] = 0;

        for (int mask = 1; mask <= FULL; mask++) {
            for (int last = 0; last < n; last++) {
                if ((mask >> last & 1) == 0 || dp[mask][last] == INF) continue;
                for (int nx = 0; nx < n; nx++) {
                    if ((mask >> nx & 1) == 1) continue;  // 이미 방문
                    if (dist[last][nx] < 0) continue;      // 길 없음
                    int nm = mask | (1 << nx);
                    dp[nm][nx] = Math.min(dp[nm][nx], dp[mask][last] + dist[last][nx]);
                }
            }
        }

        // 복귀하지 않으므로 마지막 지점이 무엇이든 전체 방문 상태의 최솟값이 곧 답
        int ans = INF;
        for (int last = 0; last < n; last++) ans = Math.min(ans, dp[FULL][last]);
        return ans == INF ? -1 : ans;
    }

    public static void main(String[] args) {
        int[][][] cases = {
                {{0, 10, 15, 20}, {10, 0, 35, 25}, {15, 35, 0, 30}, {20, 25, 30, 0}},
                {{0, 5, -1}, {5, 0, -1}, {-1, -1, 0}},
                {{0, 1}, {1, 0}},
        };
        int[] expected = {50, -1, 1};
        for (int t = 0; t < cases.length; t++) {
            int got = solution(cases[t]);
            System.out.printf("case %d: got=%d expected=%d -> %s%n",
                    t + 1, got, expected[t], got == expected[t] ? "PASS" : "FAIL");
        }
    }
}
