package com.algo.day10;


/*
1m 29s 동안 생각함
팰린드롬 분할
시간 제한: 2초
메모리 제한: 128 MB
문제

세준이는 어떤 문자열을 팰린드롬으로 분할하려고 한다. 예를 들어, ABACABA를 팰린드롬으로 분할하면, {A, B, A, C, A, B, A}, {A, BACAB, A}, {ABA, C, ABA}, {ABACABA} 등이 있다.

분할의 개수의 최솟값을 출력하는 프로그램을 작성하시오.

입력

첫째 줄에 문자열이 주어진다. 이 문자열은 알파벳 대문자로만 이루어져 있고, 최대 길이는 2,500이다.

출력

첫째 줄에 팰린드롬 분할의 개수의 최솟값을 출력한다.

예제 입력 1
BBCDDECAECBDABADDCEBACCCBDCAABDBADD
예제 출력 1
22
예제 입력 2
AAAA
예제 출력 2
1
예제 입력 3
ABCDEFGH
예제 출력 3
8
예제 입력 4
QWERTYTREWQWERT
예제 출력 4
5
 */

import java.util.*;
import java.io.*;

public class Solution2 {
    public static void main(String[] args) {
        /*
        * 펠림드롬 분할 개수 최대값 -> 문자열 길이
        *
        * 문자열 길이 최대 2500 => 2500 중 몇 번 자르냐도 모름 => DP일 확률이 높은데?
        *
        * 1차원 DP로 될 수도?
        *
        * DP[i] -> 앞에서 i개 자른 겱과 팰림드론 분할 개수 최소값.
        *
        * 흠? 근데 결국 펠린드롬인지 판단하는게 문제 아님?
        * 흠... 디피 아닌가?
        *
        * 아닌가? 결국 연속된 부분 구간으로 분할하는 거라 가능한가?
        * 음..? 앞부분 펠린드롬 판단을 못하는 거 아닌가?
        * 흠~
        * */
    }
}

/*
 * 핵심 아이디어: 2차원 팰린드롬 판정표 + 1차원 DP.
 *   pal[i][j] = 구간 [i..j]가 팰린드롬인지 (양끝 같고 안쪽이 팰린드롬이면 참 → 길이순/구간DP로 채움).
 *   dp[i] = 앞에서 i글자를 분할했을 때 최소 팰린드롬 조각 수.
 *   dp[i] = min_{j<=i, pal[j..i]} ( dp[j-1] + 1 ).
 *
 * 사용자 풀이 대비 개선점: DP[i]=앞 i개 최소 분할 이라는 방향은 정확했음.
 *   막혔던 "팰린드롬 판단"은 별도의 O(N^2) 판정표를 미리 채워두면 dp 전이에서 O(1)에 조회 가능.
 *
 * 시간복잡도: O(N^2)  (N=2500 → 약 6.25M, 충분)
 * 공간복잡도: O(N^2)  (팰린드롬 판정표)
 */
class Solution2Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] s = br.readLine().trim().toCharArray();
        int n = s.length;

        // pal[i][j]: 1-indexed 구간 [i..j]가 팰린드롬인가
        boolean[][] pal = new boolean[n + 1][n + 1];
        for (int len = 1; len <= n; len++) {
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                if (s[i - 1] != s[j - 1]) continue;
                // 양 끝이 같고, 길이 2 이하이거나 안쪽이 팰린드롬이면 팰린드롬
                pal[i][j] = (len <= 2) || pal[i + 1][j - 1];
            }
        }

        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 1; j <= i; j++) {
                if (pal[j][i]) dp[i] = Math.min(dp[i], dp[j - 1] + 1);
            }
        }
        System.out.println(dp[n]);
    }
}
