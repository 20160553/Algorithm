package com.algo.day4;

import java.util.*;
import java.io.*;

/*
* 문제: 구독 서비스 정산하기
문제 설명

당신은 여러 구독 서비스를 한 번에 관리하는 앱을 만들고 있다.

사용자는 하루에 한 번씩 구독료 정산 내역을 기록한다.
각 날짜의 정산 금액은 정수 A[i]로 주어진다.

양수: 그날 추가로 결제한 금액
음수: 그날 환불받은 금액
0: 아무 일도 없음

앱은 사용자가 특정 기간을 조회했을 때, 그 기간의 총 정산 금액이 정확히 X원이 되는 경우를 찾으려고 한다.

단, 너무 짧은 기간은 의미가 없으므로 조회 기간의 길이는 최소 L일 이상이어야 한다.

전체 기록에서 다음 조건을 만족하는 연속 기간의 개수를 구하여라.

기간의 총 정산 금액이 정확히 X
기간의 길이가 L 이상
입력
N X L
A1 A2 A3 ... AN
출력

조건을 만족하는 연속 기간의 개수를 출력한다.

제한
1 ≤ N ≤ 500,000
-10^9 ≤ A[i] ≤ 10^9
-10^14 ≤ X ≤ 10^14
1 ≤ L ≤ N

정답은 64비트 정수 범위를 사용할 수 있다.

예제 입력
8 3 3
1 2 -1 2 1 -2 3 0
예제 출력
4
* */

public class Solution1 {
    public static void main(String[] args) throws IOException {
        /*
        * 특정 기간 조회 시, 정산 금액이 X원인 경우의 수
        *
        * 1 ≤ N ≤ 500,000
        * -10^9 ≤ A[i] ≤ 10^9
        * -10^14 ≤ X ≤ 10^14
        *
        * 완탐 => N^2 => 10 ^ 10 * 5 절대 아님
        *
        * 일단은 누적합 + DP인 것 같긴 한데...
        *
        * 흠.. 아닌가 분할 정복인 것 같기도 하고,,
        * 예전에 트리 구조로 누적합 이용하는 알고리즘이 있었던 것 같은데..
        *
        * 가장 긴 수열 구하는 거면 LCS인가라 DP 확실한데 그게아니라 경우의 수 구하는 거니..
        *
        * 근데 누적합이든 뭐든 해서 합산을 아무리 줄여도 left, right 연산만해도 10의 10승을 가뿐히 넘는데?
        * 시간안에 된다고?
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        long x = Long.parseLong(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        long[] arr = new long[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

    }
}

/*
 * 핵심 아이디어: 누적합 + 해시맵.
 *   부분합이 X인 연속 구간 (i+1 .. j) 는 P[j] - P[i] = X 를 만족하므로
 *   각 우측 끝 j 에 대해 "이미 등장한 P[i] = P[j] - X 의 개수"를 세면 된다.
 *   길이 제약 (j - i >= L) 은, j 를 진행하며 i = j - L 이 되는 순간에만
 *   P[i] 를 맵에 넣는 "지연 등록"으로 자연스럽게 보장된다.
 *
 * 사용자 풀이 대비 개선점:
 *   - 사용자는 입력만 읽고 멈췄다. O(N^2) 완탐이 불가능함은 정확히 짚었으나,
 *     누적합의 차(P[j]-P[i]=X) 를 "맵 조회 1회"로 환산하면 O(N) 이 된다는
 *     전환점에 도달하지 못했다.
 *   - 길이 제약을 별도 루프로 처리하려 하면 다시 O(N*L) 위험이 있는데,
 *     j-L 시점 지연 등록으로 O(1) 처리한다.
 *
 * 시간복잡도: O(N)   (해시맵 평균 O(1) * N)
 * 공간복잡도: O(N)   (누적합 분포 저장)
 */
class Solution1Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        long x = Long.parseLong(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        long[] prefix = new long[n + 1]; // prefix[k] = arr[0..k-1] 의 합
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + Long.parseLong(st.nextToken());
        }

        long answer = 0;
        HashMap<Long, Integer> count = new HashMap<>(); // 등록된 P[i] 값들의 빈도

        for (int j = l; j <= n; j++) {
            // j 를 우측 끝으로 볼 때 i <= j-L 인 i 가 비로소 유효해지므로 P[j-L] 등록
            long eligible = prefix[j - l];
            count.merge(eligible, 1, Integer::sum);

            // P[i] = P[j] - X 인 i 의 개수가 곧 길이 L 이상이며 합이 X 인 구간 수
            answer += count.getOrDefault(prefix[j] - x, 0);
        }

        System.out.println(answer);
    }
}
