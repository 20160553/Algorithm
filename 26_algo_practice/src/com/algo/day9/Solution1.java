package com.algo.day9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
문제: 냉장 배송 상자 포장

온라인 마트에서는 주문받은 식품을 컨베이어 벨트에 놓인 순서대로 냉장 상자에 포장합니다.

각 식품에는 다음 두 가지 정보가 있습니다.

식품의 무게
식품을 안전하게 배송하기 위해 필요한 냉장 비용

하나의 상자에는 컨베이어 벨트에서 연속해서 놓여 있는 식품들만 담을 수 있습니다. 식품의 순서를 바꾸거나, 중간에 있는 식품을 건너뛸 수는 없습니다.

각 상자가 담을 수 있는 식품의 총무게는 W 이하입니다.

상자 하나의 배송 비용은 다음과 같이 계산합니다.

기본 배송비 B + 상자에 담긴 식품 중 가장 큰 냉장 비용

모든 식품을 빠짐없이 포장할 때 필요한 최소 배송 비용을 구하세요.

입력

첫 번째 줄에 식품의 개수 N, 상자의 최대 허용 무게 W, 기본 배송비 B가 주어집니다.

N W B

다음 N개의 줄에는 컨베이어 벨트 순서대로 각 식품의 무게 weight와 냉장 비용 cost가 주어집니다.

weight cost
제한 사항
1 ≤ N ≤ 2,000
1 ≤ W ≤ 10,000
1 ≤ B ≤ 100,000
1 ≤ weight ≤ W
1 ≤ cost ≤ 100,000

정답은 32비트 정수 범위를 넘을 수 있습니다.

출력

모든 식품을 포장하는 데 필요한 최소 배송 비용을 출력합니다.

예제 입력
5 10 100
4 30
2 80
5 20
3 50
2 40
예제 출력
250
예제 설명

다음과 같이 포장할 수 있습니다.

첫 번째 상자: 1번, 2번 식품
총무게: 4 + 2 = 6
배송비: 100 + max(30, 80) = 180
두 번째 상자: 3번, 4번, 5번 식품
총무게: 5 + 3 + 2 = 10
배송비: 100 + max(20, 50, 40) = 150

이 경우 총비용은 330입니다.

하지만 다음과 같이 포장하면 더 저렴합니다.

첫 번째 상자: 1번 식품
배송비: 100 + 30 = 130
두 번째 상자: 2번, 3번, 4번 식품
총무게: 2 + 5 + 3 = 10
배송비: 100 + 80 = 180
세 번째 상자: 5번 식품
배송비: 100 + 40 = 140

이 값은 450이므로 최소가 아닙니다.

따라서 예제 출력 250과 설명이 일치하지 않습니다. 정확한 최소 비용은 330입니다.
 */
public class Solution1 {

    static int n, w, b;
    public static void main(String[] args) throws IOException {
        /*
        *
            weight cost
            제한 사항
            1 ≤ N ≤ 2,000
            1 ≤ W ≤ 10,000
            1 ≤ B ≤ 100,000
            1 ≤ weight ≤ W
            1 ≤ cost ≤ 100,000
        *
        * 이건 또 뭐야
        *
        * 몇 번 잘라야할지도 모르네?
        *
        * dp인데 뒤에서 부터 가는 dp인가?
        *
        * dp[i][j] => i~j내 최소값.
        *
        * i == j => b + costs[i]
        * dp[i - 1][i] => (dp[i - 1][i - 1] + dp[i][i])
        *
        * 흠 뭔가 디핀 거 같은데 감이 안잡히네 디피에 내가 너무 약한가?
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());

        int[] weights = new int[n];
        int[] costs = new int[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            weights[i] = Integer.parseInt(st.nextToken());
            costs[i] = Integer.parseInt(st.nextToken());
        }

    }
}

/*
 * 핵심 아이디어: 1차원 구간 분할 DP.
 *   dp[i] = 앞에서 i개의 식품을 모두 포장하는 최소 비용.
 *   마지막 상자가 식품 (j+1 .. i)을 담는다고 하면
 *   dp[i] = min over j ( dp[j] + B + max(cost[j+1..i]) ),  단 weight 합 <= W.
 *   i를 고정하고 j를 i-1부터 거꾸로 내리면서 무게 합과 구간 최댓값을 누적하면
 *   안쪽 루프 한 번에 max를 O(1)로 갱신할 수 있다.
 *
 * 사용자 풀이 대비 개선점:
 *   - 사용자는 dp[i][j](구간 최소) 2차원 정의에서 점화식을 못 세우고 멈췄다.
 *     "어디서 자를지" 자체를 상태로 두는 1차원 dp[i]로 바꾸면 끝점 i만 상태가 되어
 *     점화식이 자연스럽게 나온다.
 *   - 정답이 32비트를 넘으므로 dp는 long.
 *
 * 시간복잡도: O(N^2)  (N=2,000 → 약 4백만, 충분히 통과)
 * 공간복잡도: O(N)
 */
class Solution1Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        int[] weights = new int[n + 1];
        int[] costs = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            weights[i] = Integer.parseInt(st.nextToken());
            costs[i] = Integer.parseInt(st.nextToken());
        }

        long[] dp = new long[n + 1];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            int weightSum = 0;
            int maxCost = 0;
            // j+1..i 를 한 상자에 담는 경우. j를 거꾸로 내리며 무게/최댓값 누적
            for (int j = i; j >= 1; j--) {
                weightSum += weights[j];
                if (weightSum > w) break;          // 더 내려가면 무게 초과 → 중단
                maxCost = Math.max(maxCost, costs[j]);
                if (dp[j - 1] != Long.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[j - 1] + b + maxCost);
                }
            }
        }

        System.out.println(dp[n]);
    }
}
