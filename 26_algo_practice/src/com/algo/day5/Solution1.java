package com.algo.day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
문제: 냉장고 정리 대작전

민수는 주말마다 냉장고를 정리한다.
냉장고 안에는 음식이 N개 들어 있고, 음식들은 왼쪽부터 오른쪽까지 일렬로 놓여 있다.

민수는 음식을 버리지 않고 모두 먹으려고 한다.
다만 한 번에 먹을 음식들은 연속된 구간이어야 한다.

예를 들어 음식이 1번부터 7번까지 있다면, 한 번에 2번~5번 음식을 먹는 것은 가능하지만, 2번, 4번, 5번처럼 중간을 건너뛰는 것은 불가능하다.

각 음식에는 맛 점수 A_i가 있다.
민수가 한 번에 어떤 연속 구간의 음식을 먹으면, 그때 얻는 만족도는 다음과 같다.

구간 안 음식들의 맛 점수 합 × 그 구간에서 가장 맛 점수가 낮은 음식의 점수

즉, 구간 [l, r]을 먹었을 때 만족도는

(A_l + A_{l+1} + ... + A_r) × min(A_l, A_{l+1}, ..., A_r)

이다.

민수는 냉장고의 모든 음식을 정확히 한 번씩 먹어야 한다.
즉, 1번~N번 음식을 여러 개의 연속 구간으로 나누어 먹어야 한다.

하지만 민수는 너무 자주 먹으면 귀찮기 때문에, 음식을 먹는 횟수는 최대 K번까지만 가능하다.

민수가 얻을 수 있는 만족도의 최댓값을 구하라.

입력

첫째 줄에 음식의 개수 N과 먹을 수 있는 최대 횟수 K가 주어진다.

둘째 줄에 각 음식의 맛 점수 A_1, A_2, ..., A_N이 주어진다.

1 ≤ K ≤ N ≤ 500
1 ≤ A_i ≤ 1,000,000
출력

민수가 얻을 수 있는 만족도의 최댓값을 출력한다.

정답은 64비트 정수 범위를 사용할 수 있다.

예제 입력 1
5 2
3 1 4 1 5
예제 출력 1
45
 */
public class Solution1 {
    public static void main(String[] args) throws IOException {
        /*
        * 음식은 전부 먹어야함
        *
        * 만족도 범위 : Long (500 * 10^6 * 10^6)
        *
        * 일단은 누적합 필요 (맛 점수 합산)
        *
        * 결국 구하는 건 N 개를 나눌 K개 구간 완탐인가?
        *
        * 근데 완탐은 500 C K라  500 * 499 * ... * K / (K * K-1 * ... 1)
        * 완탐하면 시간초과일텐데?
        *
        * 구간을 더 적은 시간복잡도로 어떻게 나누지?
        *
        * */
        int k, n;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        long[] tasteSum = new long[n+1]; // 맛 누적합

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            tasteSum[i] = tasteSum[i - 1] + Integer.parseInt(st.nextToken());
        }

    }
}

/*
 * 모범답안: 구간 분할 DP (Partition DP)
 *
 * 핵심 아이디어:
 *   dp[j][i] = 앞에서부터 i개의 음식을 "정확히 j개"의 연속 구간으로 나눴을 때 만족도 최댓값.
 *   dp[j][i] = max_p ( dp[j-1][p] + value(p+1, i) ),   value(l,r) = (구간합) * (구간 최소값)
 *   최대 K번이므로 정답 = max_{1<=j<=K} dp[j][n].
 *
 * 사용자 풀이 대비 개선점:
 *   - 사용자는 누적합까지만 작성하고 "500 C K 완탐"에서 막혔다.
 *     구간의 "끝점"을 DP 상태로 두면 완탐이 다항시간으로 줄어든다.
 *   - 전이 시 p를 i-1부터 거꾸로 훑으면 마지막 구간의 최소값을 O(1)로 누적 갱신할 수 있어
 *     별도의 min 전처리(희소 테이블 등)가 필요 없다.
 *
 * 시간복잡도: O(K * N^2)  (N,K <= 500 → 약 1.25e8, 통과 가능)
 * 공간복잡도: O(K * N)
 */
class Solution1Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] a = new int[n + 1];
        long[] prefix = new long[n + 1]; // 맛 점수 누적합
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
            prefix[i] = prefix[i - 1] + a[i];
        }

        final long NEG = Long.MIN_VALUE / 4; // 도달 불가 상태 표시(덧셈 오버플로 방지용 여유)
        long[][] dp = new long[k + 1][n + 1];
        for (long[] row : dp) Arrays.fill(row, NEG);
        dp[0][0] = 0;

        for (int j = 1; j <= k; j++) {
            for (int i = j; i <= n; i++) {
                long min = Long.MAX_VALUE;
                // p+1..i 가 마지막 구간. p를 내리면서 최소값을 누적 갱신.
                for (int p = i - 1; p >= j - 1; p--) {
                    min = Math.min(min, a[p + 1]);
                    if (dp[j - 1][p] == NEG) continue;
                    long val = (prefix[i] - prefix[p]) * min;
                    dp[j][i] = Math.max(dp[j][i], dp[j - 1][p] + val);
                }
            }
        }

        long ans = NEG;
        for (int j = 1; j <= k; j++) ans = Math.max(ans, dp[j][n]);
        System.out.println(ans);
    }
}
