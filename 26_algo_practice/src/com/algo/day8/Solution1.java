package com.algo.day8;

import java.util.*;
import java.io.*;

/*
문제: 구내식당 식권 구매하기

회사원 민수는 앞으로 N일 동안 회사에 출근할 예정이다.
각 날짜마다 출근 여부가 정해져 있으며, 출근하는 날에는 반드시 구내식당에서 점심을 먹어야 한다.

구내식당에서는 다음 세 종류의 식권을 판매한다.

식권 종류	가격	사용 가능한 기간
1일 식권	A원	구매한 날만 사용 가능
3일 식권	B원	구매한 날부터 3일 동안 사용 가능
7일 식권	C원	구매한 날부터 7일 동안 사용 가능

기간 식권은 구매한 날짜를 포함하여 연속된 날짜에 적용된다.
식권의 사용 기간에 주말이나 재택근무일이 포함되더라도 기간은 그대로 지나간다.

예를 들어 5일째에 3일 식권을 구매하면 5일째, 6일째, 7일째에 사용할 수 있다.

민수는 출근하는 모든 날에 식권이 적용되도록 구매하려고 한다. 필요한 최소 비용을 구하라.

입력

첫 번째 줄에 전체 기간 N이 주어진다.

두 번째 줄에 식권 가격 A, B, C가 공백으로 구분되어 주어진다.

세 번째 줄에 길이가 N인 문자열 S가 주어진다.

S[i]가 1이면 i + 1일째에 출근한다.
S[i]가 0이면 i + 1일째에 출근하지 않는다.
출력

출근하는 모든 날의 점심을 해결하기 위해 필요한 최소 비용을 출력한다.

제한
1 ≤ N ≤ 100,000
1 ≤ A, B, C ≤ 100,000
S의 길이는 N
S는 0과 1로만 이루어져 있다.
예제 입력 1
10
4000 9000 18000
1110011110
 */

public class Solution1 {
    static int n;

    public static void main(String[] args) throws IOException {
        /*
         * N <= 10만
         * A, B, C, <= 10만
         *
         * DP임 이거.. ㅇㅇ
         *
         * 근데 뭐 어떻게 DP지?
         *
         * DP[i][j] => i 번째, j번쨰 식권 먹을때 최소값
         *
         * */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        int[] periods = new int[] {1, 3, 7};
        int[] prices = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        boolean[] works = new boolean[n];
        String str = br.readLine();

        for (int i = 0; i < n; i++) {
            works[i] = true;
            if (str.charAt(i) == '0') works[i] = false;
        }

        long[][] dp = new long[n + 1][prices.length];
        long INF = 10_000_000_001L;


        for (int i = 0; i <= n; i++)
            Arrays.fill(dp[i], INF);

        dp[0][0] = 0;
        dp[0][1] = 0;
        dp[0][2] = 0;

        for (int i = 1; i <= n; i++) {
            boolean flag = works[i-1];

            for (int j = 0; j < prices.length; j++) {
                int period = periods[j];
                int prev = i - period;
                int cost = prices[j];
                if (!flag && j == 0) cost = 0;

                if (prev < 0) continue;
                for (int k = 0; k < prices.length; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[prev][k] + cost);
                }
            }
        }

        long answer = INF;
        for (int i = 0; i < periods.length; i++)
            answer = Math.min(answer, dp[n][i]);
        System.out.println(answer);
    }
}

/*
 * 핵심 아이디어: 뒤에서부터 채우는 1차원 DP.
 *   dp[i] = i일째부터 N일째까지의 모든 출근일을 해결하는 최소 비용.
 *   - i일이 출근일이 아니면 식권이 필요 없으므로 dp[i] = dp[i+1].
 *   - 출근일이면 i일을 덮는 식권을 i일에 사서 시작하는 것이 항상 최적
 *     (앞쪽은 이미 처리됐으므로 더 일찍 사는 것은 손해).
 *       dp[i] = min(A + dp[i+1], B + dp[i+3], C + dp[i+7])
 *
 * 사용자 풀이 대비 개선점:
 *   - 사용자 DP는 식권 구간이 반드시 N일 이내에서 끝나도록 강제한다(prev = i-period >= 0).
 *     그래서 막바지 출근일을 7일/3일 식권이 N 너머로 넘어가며 더 싸게 덮는 경우를
 *     표현하지 못한다. 예) N=1, A=5,B=3,C=1, S="1" → 정답 1(7일권)인데
 *     사용자 코드는 1일권만 살 수 있어 5를 출력한다.
 *   - dp[i+k]에서 i+k > N 이면 자동으로 0이 되어 "구간이 N을 넘어가는" 식권을
 *     자연스럽게 허용한다.
 *
 * 시간복잡도 O(N), 공간복잡도 O(N).
 */
class Solution1Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        String s = br.readLine();

        // dp[n+1..n+7]는 N을 넘어가는 인덱스로, 기본값 0이 그대로 base case 역할을 한다.
        long[] dp = new long[n + 8];
        for (int i = n; i >= 1; i--) {
            if (s.charAt(i - 1) == '0') {
                dp[i] = dp[i + 1];
            } else {
                long best = a + dp[i + 1];
                best = Math.min(best, b + dp[i + 3]);
                best = Math.min(best, c + dp[i + 7]);
                dp[i] = best;
            }
        }
        System.out.println(dp[1]);
    }
}