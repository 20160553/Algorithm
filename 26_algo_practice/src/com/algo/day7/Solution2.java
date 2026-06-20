package com.algo.day7;

import java.util.*;
import java.io.*;

/*
문제: 컨베이어 배송 계획

물류창고의 컨베이어 벨트 위에 N개의 상자가 일렬로 놓여 있다.
i번째 상자의 무게는 W[i]이다.

하나의 운반 트럭이 매일 한 번 출발하며, 다음 규칙을 따른다.

- 트럭은 항상 벨트의 "맨 앞"에 남아 있는 상자부터 순서대로 싣는다.
- 트럭의 1회 최대 적재 용량을 X라고 할 때, 이미 실은 상자들의 무게 합에
  다음 상자를 더해도 X를 넘지 않는 동안 계속 싣는다.
- 더 실으면 X를 초과하는 순간 그 날의 운반을 끝내고 출발한다.
  (즉, 상자의 순서를 바꾸거나 건너뛸 수 없다.)

이렇게 하루에 트럭 한 대만 운행할 때, 모든 상자를 정확히 D일 안에
전부 운반해야 한다.

조건을 만족하는 트럭의 최소 적재 용량 X를 구하라.

입력
N D
W1 W2 W3 ... WN
제한
1 ≤ D ≤ N ≤ 100,000
1 ≤ Wi ≤ 10,000

출력

모든 상자를 D일 안에 운반할 수 있는 최소 적재 용량 X를 출력한다.

예제 입력 1
10 5
1 2 3 4 5 6 7 8 9 10
예제 출력 1
15
설명

적재 용량이 15이면 다음과 같이 5일 만에 모든 상자를 운반할 수 있다.

1일차: 1 2 3 4 5   (합 15)
2일차: 6 7         (합 13)
3일차: 8           (합 8)
4일차: 9           (합 9)
5일차: 10          (합 10)

적재 용량이 14이면 5일 안에 끝낼 수 없으므로, 최소 X는 15이다.

예제 입력 2
3 3
3 2 2
예제 출력 2
3

매일 한 상자씩 운반해야 하므로, 최소 X는 가장 무거운 상자의 무게인 3이다.
 */

public class Solution2 {
    static int n, d;
    static int[] sum;

    static boolean solve(int x) {
        int l = 0, r = 1;

        int day = 0;
        while (r <= n && l <= r) {
            int w = sum[r] - sum[l];
            if (w <= x) {
                r++;
            } else {
                if (++day > d) return false;
                l = r-1;
            }
        }
        if (++day > d) return false;
        return true;
    }

    public static void main(String[] args) throws IOException {
        /*
        * n, d <= 10^5
        * 1. 상자는 항상 선입 선출
        * 2. X 이하까지 트럭에 실을 수 있음
        *
        * 무게 총합 최대 : 10^9(int)
        *
        * 1. 이진탐색
        * 2. 슬라이딩 윈도우
        *
        * X 구하기 <= 이진 탐색 (1, SUM(WEIGHT)
        *
        *
        * */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st =new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        sum = new int[n+1];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + Integer.parseInt(st.nextToken());
        }

        int l = 1, r = sum[n];
        int answer = Integer.MAX_VALUE;
        while(l <= r) {
            int mid = (l+r) / 2;
            boolean result = solve(mid);
            if (result) {
                answer = Math.min(answer, mid);
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        System.out.println(answer);
    }
}

/*
 * 핵심 아이디어: 답(최소 적재 용량 X)에 대한 매개변수 탐색(parametric binary search).
 *   X가 클수록 필요한 날수는 단조 감소하므로, "용량 X로 D일 이내 가능?"을 판정하는
 *   탐욕 함수 feasible(X)를 이분탐색한다.
 *   - 탐색 하한 = 가장 무거운 상자(maxW): 그보다 작으면 그 상자를 절대 못 실음
 *   - 탐색 상한 = 전체 무게합(total): 하루에 다 실으면 1일
 *
 * 사용자 풀이 대비 개선점:
 *   - 사용자 풀이도 정답이지만 하한을 1로 두어 불필요한 구간을 탐색하고,
 *     mid=(l+r)/2 는 l+r이 약 2*10^9까지 커져 int 상한에 아슬아슬하다.
 *     하한을 maxW로 좁히고 mid=l+(r-l)/2 로 오버플로 여지를 없앤다.
 *   - prefix-sum 투포인터 대신 단순 누적 그리디로 feasible을 작성해 경계 추론을 단순화.
 *
 * 시간복잡도: O(N log(total)), 공간복잡도: O(N)
 */
class Solution2Answer {
    static int n, d;
    static int[] w;

    // 용량 cap으로 앞에서부터 순서대로 실을 때 필요한 날수가 d 이하인가
    static boolean feasible(int cap) {
        int days = 1, load = 0;
        for (int i = 0; i < n; i++) {
            if (load + w[i] > cap) { // 더 실으면 초과 → 오늘 마감하고 새 날 시작
                days++;
                load = 0;
                if (days > d) return false;
            }
            load += w[i];
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        w = new int[n];

        int lo = 0, hi = 0; // lo=maxW, hi=total
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            w[i] = Integer.parseInt(st.nextToken());
            lo = Math.max(lo, w[i]);
            hi += w[i];
        }

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (feasible(mid)) hi = mid; // 가능 → 더 줄여본다
            else lo = mid + 1;
        }
        System.out.println(lo);
    }
}
