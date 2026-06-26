package com.algo.day10;

/*
K번째 수
시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
2 초	128 MB	9515	3344	2451	38.237%
문제

세준이는 크기가 N×N인 배열 A를 만들었다. 배열에 들어있는 수 A[i][j] = i×j 이다. 이 수를 일차원 배열 B에 넣으면 B의 크기는 N×N이 된다. B를 오름차순 정렬했을 때, B[k]를 구해보자.

배열 A와 B의 인덱스는 1부터 시작한다.

입력

첫째 줄에 배열의 크기 N이 주어진다. N은 10⁵보다 작거나 같은 자연수이다. 둘째 줄에 k가 주어진다. k는 min(10⁹, N²)보다 작거나 같은 자연수이다.

출력

B[k]를 출력한다.

예제 입력 1
3
7
예제 출력 1
6
 */

import java.util.*;
import java.io.*;

public class Solution1 {
    public static void main(String[] args) {
        /*
        * 일단은 N ^ 2 => 10 ^ 10 - 1 Long 범위
        *
        * Sorting은 시간초과 범위가 너무 큼.
        *
        * Heap 구조 이용하는 거 같은데
        *
        * 아잇 힙으로 정렬 트리 만드는 알고리즘 뭐 있었는데 뭐더라.
        *
        * 힙쓰면 시간복잡도 : O(2log2(N)인가)
        * 너무 자료구조 문제인듯. 몰루겠네..
        *
        * */

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        long k = sc.nextLong();
    }
}

/*
 * 핵심 아이디어: 정답(B[k]의 값) 자체를 이분 탐색한다.
 *   배열 B를 직접 만들거나 정렬(N^2)할 필요 없이, "값 x 이하인 원소의 개수"만 셀 수 있으면 된다.
 *   i번째 행은 i, 2i, 3i, ... (등차) 이므로 x 이하 원소 개수는 min(x/i, n) 개다.
 *   전체 개수 cnt(x) = Σ_{i=1..n} min(x/i, n). 이는 x에 대해 단조 증가하므로,
 *   cnt(x) >= k 를 만족하는 가장 작은 x 가 답이다. (그 x 는 반드시 어떤 i×j 값)
 *
 * 사용자 풀이 대비 개선점: 힙/정렬(O(N^2 log N))로는 N=10^5 에서 불가능.
 *   "답을 이분 탐색 + 개수 세기" 패턴으로 O(N log(N^2)) 에 해결.
 *
 * 시간복잡도: O(N log(N^2)) = O(N log N)
 * 공간복잡도: O(1)
 */
class Solution1Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine().trim());
        long k = Long.parseLong(br.readLine().trim());

        long lo = 1, hi = n * n; // 답의 범위 [1, N^2]
        while (lo < hi) {
            long mid = (lo + hi) / 2;
            if (count(n, mid) >= k) hi = mid; // mid 이하가 k개 이상 → 답은 mid 이하
            else lo = mid + 1;
        }
        System.out.println(lo);
    }

    // 곱셈표에서 값이 x 이하인 원소의 개수
    private static long count(long n, long x) {
        long cnt = 0;
        for (long i = 1; i <= n; i++) cnt += Math.min(x / i, n);
        return cnt;
    }
}
