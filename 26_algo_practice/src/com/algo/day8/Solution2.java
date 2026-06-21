package com.algo.day8;

import java.util.*;
import java.io.*;

/*
알고리즘 조합: Binary Search + Greedy (파라메트릭 서치)

문제: 택배 물류 분배

물류센터에 N개의 택배 보관함이 일렬로 놓여 있다.
i번째 보관함에는 box[i]개의 택배가 들어 있다. (보관함의 순서는 바꿀 수 없다.)

회사는 M명의 배송기사에게 보관함을 나누어 맡기려고 한다.
각 배송기사는 "연속된 구간"의 보관함들을 통째로 담당하며,
모든 보관함은 반드시 정확히 한 명의 기사에게 배정되어야 한다.
(즉, 일렬로 놓인 보관함을 M개의 연속 구간으로 나눈다. 빈 구간은 없다.)

한 기사가 담당하는 구간의 "작업량"은 그 구간에 속한 보관함들의 택배 개수 합이다.

배송이 가장 오래 걸리는 기사(= 작업량이 가장 큰 기사)의 작업량을 최소가 되도록
보관함을 나누고 싶다. 이때 가능한 "최대 작업량의 최솟값"을 구하라.

입력
첫 번째 줄에 보관함의 수 N과 배송기사의 수 M이 공백으로 구분되어 주어진다.
두 번째 줄에 N개의 정수 box[0], box[1], ..., box[N-1]이 공백으로 구분되어 주어진다.

출력
배송기사들에게 보관함을 나누어 맡겼을 때,
가장 큰 작업량을 최소화한 값을 출력한다.

제한
1 ≤ M ≤ N ≤ 100,000
1 ≤ box[i] ≤ 10,000

예제 입력 1
5 3
1 2 3 4 5

예제 출력 1
6
(설명: [1 2 3] [4] [5] 로 나누면 구간 합은 6, 4, 5 이고
       이때 최대 작업량 6이 가능한 최솟값이다.)

예제 입력 2
4 2
10 10 10 10

예제 출력 2
20
(설명: [10 10] [10 10] -> 20, 20)

힌트
- 정답(최대 작업량의 최솟값) X 를 [max(box) ~ sum(box)] 범위에서 이분 탐색한다.
- "최대 작업량이 X 이하가 되도록 M개 이하의 구간으로 나눌 수 있는가?"를
  Greedy(앞에서부터 X를 넘지 않게 한 구간씩 채우기)로 판정한다.
- 필요한 구간 수가 M 이하이면 X는 가능 -> 더 줄여본다.
 */

import java.io.IOException;

public class Solution2 {
    public static void main(String[] args) throws IOException {
        /*
        * N - 1 C M => 시간 초과
        *
        * 누적합?
        * 통 모르겠네??
        * 이진탐색 + 누적합 응용인가?
        *
        * (i, j)구간 누적합 = SUM(j) - SUM(i - 1)
        *
        * */
    }
}

/*
 * 핵심 아이디어: 정답(최대 작업량의 최솟값)을 [max(box) ~ sum(box)] 범위에서
 *   파라메트릭 이분 탐색한다. 판정함수 feasible(X)는 "한 구간 합이 X를 넘지 않게
 *   앞에서부터 그리디로 채울 때 필요한 구간 수 <= M" 인지를 본다.
 *   정답은 단조성을 가지므로(X가 크면 항상 가능) lower bound를 찾는다.
 *
 * 누적합은 불필요하다: 그리디 판정은 한 번 훑으며 합을 누적하면 되므로 O(N)이다.
 *
 * 시간복잡도 O(N log(sum)), 공간복잡도 O(N).
 */
class Solution2Answer {
    static int n, m;
    static int[] box;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        box = new int[n];
        st = new StringTokenizer(br.readLine());
        long lo = 0, hi = 0; // lo는 최댓값(한 구간이 단일 보관함이라도 그 값 이상이어야 함)
        for (int i = 0; i < n; i++) {
            box[i] = Integer.parseInt(st.nextToken());
            lo = Math.max(lo, box[i]);
            hi += box[i];
        }

        // 가능한 X 중 최솟값을 찾는 lower-bound 이분 탐색.
        while (lo < hi) {
            long mid = (lo + hi) / 2;
            if (feasible(mid)) hi = mid;
            else lo = mid + 1;
        }
        System.out.println(lo);
    }

    // 한 구간 합이 limit을 넘지 않게 그리디로 나눌 때 필요한 구간 수가 M 이하인가?
    static boolean feasible(long limit) {
        int groups = 1;
        long cur = 0;
        for (int v : box) {
            if (cur + v > limit) {  // 현재 구간에 더 못 담으면 새 구간 시작
                groups++;
                cur = v;
                if (groups > m) return false;
            } else {
                cur += v;
            }
        }
        return true;
    }
}
