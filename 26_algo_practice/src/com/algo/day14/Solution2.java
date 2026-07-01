package com.algo.day14;

/*
놀이 공원
시간 제한: 2초
메모리 제한: 128 MB
문제

N명의 아이들이 한 줄로 줄을 서서 놀이공원에서 1인승 놀이기구를 기다리고 있다. 이 놀이공원에는 총 M종류의 1인승 놀이기구가 있으며, 1번부터 M번까지 번호가 매겨져 있다.

모든 놀이기구는 각각 정해진 운행 시간이 정해져 있어서, 운행 시간이 지나면 탑승하고 있던 아이는 내리게 된다. 놀이 기구가 비어 있으면 현재 줄에서 가장 앞에 서 있는 아이가 빈 놀이 기구에 탑승한다. 만일 여러 개의 놀이기구가 동시에 비어 있으면, 더 작은 번호가 적혀 있는 놀이기구를 먼저 탑승한다고 한다.

놀이기구가 모두 비어 있는 상태에서 첫 번째 아이가 놀이기구에 탑승한다고 할 때, 줄의 마지막 아이가 타게 되는 놀이기구의 번호를 구하는 프로그램을 작성하시오.

입력

첫째 줄에 N(1 ≤ N ≤ 2,000,000,000)과 M(1 ≤ M ≤ 10,000)이 빈칸을 사이에 두고 주어진다. 둘째 줄에는 각 놀이기구의 운행 시간을 나타내는 M개의 자연수가 순서대로 주어진다. 운행 시간은 1 이상 30 이하의 자연수이며, 단위는 분이다.

출력

첫째 줄에 마지막 아이가 타게 되는 놀이기구의 번호를 출력한다.

예제 입력 1
22 5
1 2 3 4 5
예제 출력 1
4
 */

import java.io.*;
import java.util.*;

public class Solution2 {
    public static void main(String[] args) throws IOException {
        /*
        * N번 째 학생은 몇 번째 놀이기구를 타는가?
        *
        * 최소공배수 응용인가?
        *
        * 최소 공배수 X일 경우, X까지 놀이기구 별 인원 수
        *
        * N % X 만 직접 계산하면 되는 건가 그럼
        *
        * 알고리즘
        * 1. 최소 공배수 구하기
        * 2. N % 최소 공배수 만 한 명씩 배치하도록 수행
        * 3. 결과 출력?
        *
        * 흠 유클리드 호제법 이용하는건가?
        * 최대 공약수 구해서 최소 공배수 구하기?
        *
        * */
    }
}

/*
 * 핵심 아이디어: "시간(분)"을 이분 탐색한다. 각 놀이기구 i는 시각 0, d_i, 2d_i, ...
 *   마다 한 명씩 태우므로, 시각 T까지 탑승한 아이 수 = Σ(T/d_i + 1)이다(단조 증가).
 *   Σ(T/d_i + 1) >= N을 만족하는 최소 시각 T를 찾으면, 그 시각 직전까지 탑승한 수
 *   cntBefore를 빼서 "T 시각에 몇 번째로 타는 아이인지" remaining을 구하고,
 *   T % d_i == 0 인 기구를 번호 오름차순으로 세어 답을 찾는다.
 *
 * 사용자 풀이 대비: 최소공배수(LCM) 접근은 기구가 10,000개라 LCM이 폭발하고
 *   N도 20억까지라 직접 시뮬레이션이 불가능하다. 값이 아니라 "시간"을 이분 탐색해야 한다.
 *   또한 N <= M이면 첫 M명이 그대로 1..M번을 타므로 답은 N.
 *
 * 시간복잡도: O(M log(maxT))   /   공간복잡도: O(M)
 */
class Solution2Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] d = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) d[i] = Integer.parseInt(st.nextToken());

        // 첫 M명은 시각 0에 번호순으로 그대로 탑승
        if (n <= m) {
            System.out.println(n);
            return;
        }

        // Σ(T/d_i + 1) >= N 을 만족하는 최소 시각 T 이분 탐색
        long lo = 0, hi = 2_000_000_000L * 30; // 최악(M=1, d=30, N=2e9)까지 커버
        while (lo < hi) {
            long mid = (lo + hi) / 2;
            if (boarded(d, mid) >= n) hi = mid;
            else lo = mid + 1;
        }
        long t = lo;

        long cntBefore = boarded(d, t - 1);        // t 시각 직전까지 탑승한 아이 수
        long remaining = n - cntBefore;            // t 시각에 remaining 번째로 타는 아이가 정답
        for (int i = 0; i < m; i++) {
            if (t % d[i] == 0) {
                remaining--;
                if (remaining == 0) {
                    System.out.println(i + 1);
                    return;
                }
            }
        }
    }

    // 시각 T까지(포함) 탑승한 아이의 총 수
    static long boarded(int[] d, long t) {
        long sum = 0;
        for (int x : d) sum += t / x + 1;
        return sum;
    }
}
