package com.algo.day16;

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

import java.util.*;
import java.io.*;

public class Solution2 {

    static int solution(int[][] dist) {
        int n = dist.length;

        int FILL = 1 << n;
        int INF = Integer.MAX_VALUE / 2;
        int[][] dp = new int[FILL][n];
        int answer = INF;

        for (int i = 0; i < FILL; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[1][0] = 0;

        for (int mask = 0; mask < FILL; mask++) {
            for (int prev = 0; prev < n; prev++) {
                if (((mask >> prev) & 1) == 0 || dp[mask][prev] >= INF) continue;
                for (int next = 0; next < n; next++) {
                    if (((mask >> next) & 1) == 1 || dist[prev][next] < 0) continue;
                    int nm = mask | (1 << next);
                    int nc = dp[mask][prev] + dist[prev][next];
                    dp[nm][next] = Math.min(nc, dp[nm][next]);
                }
            }
        }

        for (int i = 1; i < n; i++) {
            if (dist[i][0] < 0) continue;;
            answer = Math.min(dp[FILL - 1][i] + dist[i][0], answer);
        }

        return answer >= INF ? -1 : answer;
    }

    public static void main(String[] args) {
        /*
        * 처음 -> 모든 장소 순회 후 원래 자리로 돌아옴
        *   => 외판원 순회
        *
        * 비트 마스킹 이용
        * */

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
