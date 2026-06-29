package com.algo.day13;


/*
문제 동전 뒤집기

N²개의 동전이 N행 N열을 이루어 탁자 위에 놓여 있다. 그 중 일부는 앞면(H)이 위를 향하도록 놓여 있고, 나머지는 뒷면(T)이 위를 향하도록 놓여 있다. <그림 1>은 N이 3일 때의 예이다.

H H T  T T H
T H H  T H H
T H T  T H T

<그림 1>

이들 N²개의 동전에 대하여 임의의 한 행 또는 한 열에 놓인 N개의 동전을 모두 뒤집는 작업을 수행할 수 있다. 예를 들어 <그림 1>의 상태에서 첫 번째 열에 놓인 동전을 모두 뒤집으면 <그림 2>와 같이 되고, <그림 2>의 상태에서 첫 번째 행에 놓인 동전을 모두 뒤집으면 <그림 3>과 같이 된다.

T H T          H T H
H H H          H H H
H H T          H H T

<그림 2>       <그림 3>

<그림 3>의 상태에서 뒷면이 위를 향하여 놓인 동전의 개수는 두 개이다. <그림 1>의 상태에서 이와 같이 한 행 또는 한 열에 놓인 N개의 동전을 모두 뒤집는 작업을 계속 수행할 때 뒷면이 위를 향하도록 놓인 동전의 개수를 2개보다 적게 만들 수는 없다.

N²개의 동전들의 초기 상태가 주어질 때, 한 행 또는 한 열에 놓인 N개의 동전을 모두 뒤집는 작업들을 수행하여 뒷면이 위를 향하는 동전 개수를 최소로 하려 한다. 이때의 최소 개수를 구하는 프로그램을 작성하시오.

입력

첫째 줄에 20이하의 자연수 N이 주어진다. 둘째 줄부터 N줄에 걸쳐 N²개 동전들의 초기 상태가 주어진다. 각 줄에는 한 행에 놓인 N개의 동전의 상태가 왼쪽부터 차례대로 주어지는데, 앞면이 위를 향하도록 놓인 경우 H, 뒷면이 위를 향하도록 놓인 경우 T로 표시되며 이들 사이에 공백은 없다.
 */

import java.util.*;
import java.io.*;

public class Solution1 {
    public static void main(String[] args) throws IOException {
        /*
        * N <= 20
        *
        * 이게 뭐임?
        *
        * N 개수가 20으로 매우 작음 -> 이진탐색일 확률은 적음
        *
        * 백트래킹인 듯?
        *
        * ** 핵심 원리 **
        * 1. 한 행 혹은 한 열을 2번 뒤집으면 똑같은 상태로 돌아옴
        * 2. 특정 행을 뒤집는 순서는 중요치 않음. 특정 행을 뒤집느냐 마느냐가 중요함
        *
        * 근데 문제
        * 2^20 * 2^20 => 10 ^ 6 * 10 ^ 6 => 약 10 ^ 12
        *
        * 시간초과네?
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        boolean[][] map = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                boolean target = true;
                if (!st.nextToken().equals("H"))
                    target = false;
                map[i][j] = target;
            }
        }

    }
}

/*
 * 모범답안: 행 전수조사(비트마스크) + 열 그리디
 *
 * 핵심 아이디어
 *  - 한 행/열을 두 번 뒤집으면 원상복귀이므로, 각 행·열은 "뒤집는다/안 뒤집는다" 2가지뿐.
 *  - 행을 뒤집는 모든 경우(2^N)를 고정하면, 각 열은 서로 독립이 된다.
 *    -> 그 열의 뒷면(T) 개수 cntT 와 (N - cntT) 중 작은 값이 그 열의 최소 기여분.
 *      (열을 뒤집을지 말지를 그 자리에서 그리디로 결정)
 *
 * 사용자 풀이 대비 개선점
 *  - 사용자가 우려한 2^N * 2^N(≈10^12) 전수조사를, 열을 그리디로 처리해 2^N * N^2 로 축소.
 *  - 입력에 공백이 없으므로 StringTokenizer가 아니라 line.charAt(j)로 한 글자씩 읽어야 한다.
 *
 * 시간복잡도: O(2^N * N^2)  (N=20 -> 약 4*10^8, 비트 연산으로 통과)
 * 공간복잡도: O(N)          (행당 비트마스크 N개)
 */
class Solution1Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        // row[i]의 j번째 비트 = (i,j)가 뒷면(T)이면 1
        int[] row = new int[n];
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < n; j++) {
                if (line.charAt(j) == 'T') row[i] |= (1 << j);
            }
        }

        int full = (1 << n) - 1;
        int answer = Integer.MAX_VALUE;

        // mask의 i번째 비트 = i행을 뒤집는다
        for (int mask = 0; mask <= full; mask++) {
            // 행 뒤집기를 반영한 각 행의 비트마스크를 만들고, 열별 T 개수를 센다
            int[] cntT = new int[n];
            for (int i = 0; i < n; i++) {
                int r = ((mask >> i) & 1) == 1 ? (~row[i] & full) : row[i];
                for (int j = 0; j < n; j++) {
                    if (((r >> j) & 1) == 1) cntT[j]++;
                }
            }
            int sum = 0;
            for (int j = 0; j < n; j++) sum += Math.min(cntT[j], n - cntT[j]);
            answer = Math.min(answer, sum);
        }

        System.out.println(answer);
    }
}
