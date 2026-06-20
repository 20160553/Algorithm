package com.algo.day7;

import java.util.*;
import java.io.*;

/*
문제: 연속 선택 제한 최대 점수

길이가 N인 수열이 있다. 각 위치 i에는 점수 A[i]가 있다.

왼쪽부터 오른쪽으로 진행하면서 각 위치를 선택하거나 건너뛸 수 있다.

단, 다음 조건이 있다:

연속으로 3개 이상 선택할 수 없다.

얻을 수 있는 점수의 최댓값을 구하라.

입력
N
A1 A2 A3 ... AN
제한
1 ≤ N ≤ 100,000
1 ≤ Ai ≤ 10,000
출력

최대 점수 출력

예제 입력 1
6
6 10 13 9 8 1
예제 출력 1
33
설명

가능한 선택:

6 + 10 + (skip 13) + 9 + 8 + (skip 1) = 33
 */

public class Solution1 {
    public static void main(String[] args) throws IOException {
        /*
         * N <= 10만 (10^5)
         * Ai < 1만 (10^4)
         *
         * 3중에 1개는 무조건 선택이 유리함.
         *
         * 비트마스크 최대 : 10^6 - 1
         *
         * 1. DP[n][000 ~ 110]
         * DP[2][000] DP[001], [010] [011] [100] [101] [110]
         *
         * DP[n + 1][1AB] => Math.max(arr[n] + Dp[n][AB0], arr[n] + Dp[n][AB1]); !(A == B && A==1)
         *
         * */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[] arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int m = 7;
        int[][] dp = new int[n][m];
        dp[2][0] = 0;
        dp[2][1] = arr[0];
        dp[2][2] = arr[1];
        dp[2][3] = arr[0] + arr[1];
        dp[2][4] = arr[2];
        dp[2][5] = arr[2] + arr[0];
        dp[2][6] = arr[2] + arr[1];

        for (int i = 3; i < n; i++) {
            int score = arr[i];
            for (int j = 0; j < 2; j++) {
                // 이번에 선택 or X
                for (int mask = 0; mask < m; mask++) {
                    int nm = j << 2 | mask >> 1;
                    int prev = dp[i - 1][mask];
                    if ((mask >> 1) == 3) continue;
                    dp[i][nm] = Math.max(dp[i][nm], score + prev);
                }
            }
        }

        int answer = 0;
        for (int i = 0; i < m; i++)
            answer = Math.max(answer, dp[n - 1][i]);
        System.out.println(answer);
    }
}

/*
 * 핵심 아이디어: 상태를 "현재 위치까지의 연속 선택 개수(0/1/2)"로만 정의하면 충분하다.
 *   dp[i][k] = i번째까지 봤을 때, i에서 끝나는 연속 선택 길이가 k일 때의 최대 점수
 *   - dp[i][0] : i 미선택 → 이전 상태 무엇이든 가능 = max(dp[i-1][0..2])
 *   - dp[i][1] : i 선택, i-1 미선택      = dp[i-1][0] + A[i]
 *   - dp[i][2] : i 선택, i-1도 선택(연속2) = dp[i-1][1] + A[i]   (연속3은 금지되므로 dp[i-1][2]는 못 씀)
 *
 * 사용자 풀이 대비 개선점:
 *   - 사용자의 비트마스크(7상태) DP는 j=0(미선택)에도 score를 더해 과대계산(예제 33 대신 37 출력),
 *     또 (mask>>1)==3 skip이 미선택 경로까지 버리고, dp[2]부터 시작해 N<3에서 인덱스 예외가 난다.
 *   - 상태를 3개로 줄여 버그 여지를 없애고 N=1,2 같은 경계도 자연스럽게 처리한다.
 *
 * 시간복잡도: O(N), 공간복잡도: O(1) (직전 행만 유지)
 */
class Solution1Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());

        // p0/p1/p2 = 직전 위치에서 끝나는 연속 선택 길이가 0/1/2일 때의 최대 점수
        long p0 = 0, p1 = Long.MIN_VALUE / 4, p2 = Long.MIN_VALUE / 4;
        for (int i = 0; i < n; i++) {
            int a = Integer.parseInt(st.nextToken());
            long c0 = Math.max(p0, Math.max(p1, p2)); // i 미선택
            long c1 = p0 + a;                          // i 선택, 직전 미선택
            long c2 = p1 + a;                          // i 선택, 직전도 선택(연속2)
            p0 = c0; p1 = c1; p2 = c2;
        }
        System.out.println(Math.max(p0, Math.max(p1, p2)));
    }
}
