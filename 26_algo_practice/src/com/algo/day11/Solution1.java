package com.algo.day11;

/*
부분수열의 합 2
문제

N개의 정수로 이루어진 수열이 있을 때, 크기가 양수인 부분수열 중에서 그 수열의 원소를 다 더한 값이 S가 되는 경우의 수를 구하는 프로그램을 작성하시오.

입력

첫째 줄에 정수의 개수를 나타내는 N과 정수 S가 주어진다.

1≤N≤40
∣S∣≤1,000,000

둘째 줄에 N개의 정수가 빈칸을 사이에 두고 주어진다. 주어지는 정수의 절댓값은 100,000을 넘지 않는다.

출력

첫째 줄에 합이 S가 되는 부분수열의 개수를 출력한다.

예제 입력 1
5 0
-7 -3 -2 5 8
예제 출력 1
1
 */

import java.util.*;
import java.io.*;

public class Solution1 {
    public static void main(String[] args) throws IOException {
        /*
        * input
        * n, s
        * 수열
        *
        * 합이 S가 되는 부분 수열의 개수
        *
        * 누적합 : sum[i] - sum[j] = j + 1 ~ i까지 수열 합
        *
        * 합이 S로 고정.
        *
        * 이건 DP 응용 + 누적합 응용 형태
        *
        * SUM[i] = S + sum[j] 인 개수를 구하면 됨
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        int[] sum = new int[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + Integer.parseInt(st.nextToken());
        }

        int answer = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (sum[i] == s + sum[j]) answer++;
            }
        }
        System.out.println(answer);
    }
}

/*
 * 모범답안: 중간에서 만나기(Meet in the Middle)
 *
 * 핵심 아이디어:
 *   - 이 문제는 "연속 부분배열"이 아니라 "부분수열(임의의 부분집합)"의 합을 세는 문제다.
 *     따라서 누적합으로 연속 구간만 세면 틀린다(예제는 우연히 1로 일치할 뿐).
 *   - N≤40이라 전체 2^40은 불가능 → 수열을 절반으로 나눠 각 절반의 모든 부분집합 합을
 *     2^(N/2)개씩 만든 뒤, 한쪽을 정렬하고 다른 쪽에서 (S - x)의 개수를 이분탐색으로 센다.
 *
 * 사용자 풀이 대비 개선점:
 *   - 부분수열 = 부분집합이므로 연속 누적합 접근(sum[i]==S+sum[j])은 문제 정의 자체가 다르다.
 *     이를 분할정복+이분탐색으로 정정.
 *
 * 시간복잡도: O(2^(N/2) * (N/2)) — 생성 + 정렬/이분탐색
 * 공간복잡도: O(2^(N/2))
 */
class Solution1Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long s = Long.parseLong(st.nextToken());

        int[] a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

        int half = n / 2;
        long[] left = subsetSums(a, 0, half);          // 2^half 개
        long[] right = subsetSums(a, half, n);         // 2^(n-half) 개
        Arrays.sort(right);

        long answer = 0;
        for (long x : left) {
            // right 에서 값이 정확히 (s - x)인 원소 개수 = upper - lower
            answer += upperBound(right, s - x) - lowerBound(right, s - x);
        }

        // 빈 부분수열(왼쪽 공집합 + 오른쪽 공집합, 합 0)은 크기가 양수가 아니므로 제외
        if (s == 0) answer--;

        System.out.println(answer);
    }

    // [from, to) 구간 원소들로 만들 수 있는 모든 부분집합의 합(공집합 포함)
    private static long[] subsetSums(int[] a, int from, int to) {
        int len = to - from;
        long[] res = new long[1 << len];
        for (int mask = 0; mask < (1 << len); mask++) {
            long sum = 0;
            for (int b = 0; b < len; b++) {
                if ((mask & (1 << b)) != 0) sum += a[from + b];
            }
            res[mask] = sum;
        }
        return res;
    }

    private static int lowerBound(long[] arr, long key) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < key) lo = mid + 1; else hi = mid;
        }
        return lo;
    }

    private static int upperBound(long[] arr, long key) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] <= key) lo = mid + 1; else hi = mid;
        }
        return lo;
    }
}
