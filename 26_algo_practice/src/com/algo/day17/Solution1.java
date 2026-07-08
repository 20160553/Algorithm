package com.algo.day17;

/*
문제

예술을 사랑하는 사람들이 시장에 모여서 그들의 그림을 서로 거래하려고 한다. 모든 그림의 거래는 다음과 같은 조건을 만족해야 한다.

그림을 팔 때, 그림을 산 가격보다 크거나 같은 가격으로 팔아야 한다.
같은 그림을 두 번 이상 산 것은 불가능하다.

방금 시장에 새로운 그림이 들어왔다. 1번 아티스트는 그 그림을 외부 상인에게 가격 0원 주고 샀다. 이제 그 그림을 자신의 예술가 친구들에게 팔려고 한다. 위의 조건을 모두 만족하는 거래만 이루어진다고 가정했을 때, 그림을 소유했던 사람의 수의 최댓값을 출력하는 프로그램을 작성하시오. (1번 아티스트와 마지막으로 그 그림을 소유한 사람도 포함한다).

입력

첫째 줄에 예술가의 수 N이 주어진다. N은 2보다 크거나 같고, 15보다 작거나 같은 자연수이다.

둘째 줄부터 N개의 줄에는 N개의 수가 주어진다. i번째 줄의 j번째 수는 i번 예술가가 j번 예술가에게 그 그림을 살 때의 가격이다. 모든 가격은 0이 제일 낮은 가격이고, 9가 제일 높은 가격이다.

출력

첫째 줄에 그 그림을 소유했던 사람들 (잠시라도 소유했던 사람도 포함)의 최대값을 출력한다.

예제 입력 1
3
022
101
110
예제 출력 1
2
 */

import java.util.*;
import java.io.*;

public class Solution1 {
    public static void main(String[] args) throws IOException {
        /*
        *
        * 흠 잘 몰겠는데?
        *
        * N <= 15인 거보면 비트마스킹 쓸 수 있겠는 걸?
        *
        * 외판원 응용인가?
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[][] dist = new int[n][n];
        StringTokenizer st;

        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < n; j++) {
                dist[i][j] = str.charAt(j);
            }
        }

        int FILL = 1 << n;
        int INF = Integer.MAX_VALUE / 2 - 1;
        int[][] dp = new int[FILL][n];

        for (int i = 0; i < n; i++)
            Arrays.fill(dp[i], INF);

        dp[1][0] = 0;

        int answer = 0;
        for (int mask = 0; mask < FILL; mask++) {
            for (int next = 0; next < n; next++) {
                if ((mask >> next & 1) == 1) continue;
                for (int prev = 0; prev < n; prev++) {
                    if ((mask >> prev & 1) == 0) continue;
                    int nm = mask | 1 << next;
                    int nc = dist[next][prev];
                    answer = Math.max(answer, next);
                    dp[nm][next] = Math.min(dp[nm][next], nc);
                }
            }
        }
        System.out.println(answer);
    }
}

/*
 * [모범답안] 최대 소유자 수 = 0번(1번 아티스트)에서 출발하는 "가격 증가 최장 체인".
 *   - 상태: dp[mask][last] = mask를 거쳐 last가 마지막 소유자일 때, last가 지불한 최소 가격.
 *   - 전이: next가 prev에게서 price[next][prev]에 산다. 그 가격이 prev의 구매가보다 커야 유효.
 *           (지불가를 낮게 유지할수록 이후 거래 여지가 크므로 min 유지 = 지배 관계)
 *   - 답: 도달 가능한 상태들 중 popcount(mask)의 최댓값(= 소유자 수).
 * 사용자 코드 대비 개선점:
 *   (1) 정답을 node index가 아니라 popcount(소유자 수)로 집계,
 *   (2) 가격 증가 조건(전이 유효성) 검사 추가 — 원래 코드엔 없음,
 *   (3) dp 초기화 범위를 첫 n행이 아니라 전체 2^n행으로 수정,
 *   (4) 문자('0'~'9')를 정수 가격으로 변환.
 * ※ 지문은 '이상(≥)'이나, 예제(정답 2)는 '초과(>)'라야 재현되므로 예제에 맞춰 strict로 구현.
 * 시간복잡도: O(2^n · n^2), 공간복잡도: O(2^n · n)
 */
class Solution1Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[][] price = new int[n][n];
        for (int i = 0; i < n; i++) {
            String row = br.readLine().trim();
            for (int j = 0; j < n; j++) price[i][j] = row.charAt(j) - '0'; // i가 j에게서 살 때의 가격
        }

        int FULL = 1 << n;
        int INF = Integer.MAX_VALUE;
        int[][] dp = new int[FULL][n];
        for (int[] r : dp) Arrays.fill(r, INF);
        dp[1][0] = 0; // 0번(1번 아티스트)이 0원에 그림을 얻은 시작 상태

        int answer = 1; // 최소한 1번 아티스트 본인
        for (int mask = 1; mask < FULL; mask++) {
            if ((mask & 1) == 0) continue; // 0번이 항상 포함돼야 함
            for (int prev = 0; prev < n; prev++) {
                if ((mask >> prev & 1) == 0 || dp[mask][prev] == INF) continue;
                answer = Math.max(answer, Integer.bitCount(mask));
                for (int next = 0; next < n; next++) {
                    if ((mask >> next & 1) == 1) continue;
                    int p = price[next][prev];       // next가 prev에게서 산다
                    if (p <= dp[mask][prev]) continue; // 산 가격보다 비싸게 팔아야(예제 기준 strict)
                    int nm = mask | (1 << next);
                    if (p < dp[nm][next]) dp[nm][next] = p;
                }
            }
        }
        System.out.println(answer);
    }
}
