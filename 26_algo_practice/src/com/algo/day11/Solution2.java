package com.algo.day11;

/*
문제

반도체를 설계할 때 n개의 포트를 다른 n개의 포트와 연결해야 할 때가 있다.

예를 들어 왼쪽 그림이 n개의 포트와 다른 n개의 포트를 어떻게 연결해야 하는지를 나타낸다. 하지만 이와 같이 연결을 할 경우에는 연결선이 서로 꼬이기 때문에 이와 같이 연결할 수 없다. n개의 포트가 다른 n개의 포트와 어떻게 연결되어야 하는지가 주어졌을 때, 연결선이 서로 꼬이지(겹치지, 교차하지) 않도록 하면서 최대 몇 개까지 연결할 수 있는지를 알아내는 프로그램을 작성하시오.

입력

첫째 줄에 정수 n(1 ≤ n ≤ 40,000)이 주어진다. 다음 줄에는 차례로 1번 포트와 연결되어야 하는 포트 번호, 2번 포트와 연결되어야 하는 포트 번호, ..., n번 포트와 연결되어야 하는 포트 번호가 주어진다. 이 수들은 1 이상 n 이하이며 서로 같은 수는 없다고 가정하자.

출력

첫째 줄에 최대 연결 개수를 출력한다.

예제 입력 1
6
4 2 6 3 1 5
예제 출력 1
3
 */

import java.util.*;
import java.io.*;

public class Solution2 {
    public static void main(String[] args) throws IOException {
        /*
        * 꼬이지 않는 경우 => (증가/감소)하는 최대 부분 수열
        *
        * 전선 연결하는 포트가 증가(감소)하다 감소(증가)하는 경우 선이 꼬임.
        *
        * 무조건 한 방향이 유지되어야 함.
        *
        * 쓰읍.. 감도 안잡히네 또,,,
        *
        * 느낌상 DP 아닌 것 같은데?
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] dest = new int[n + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            dest[i] = Integer.parseInt(st.nextToken());
        }

    }
}

/*
 * 모범답안: 최장 증가 부분 수열 (LIS, O(n log n))
 *
 * 핵심 아이디어:
 *   - 왼쪽 포트는 1..n 순서로 고정 → 선이 꼬이지 않으려면 오른쪽 포트 번호가 증가해야 한다.
 *     즉 dest 배열의 "최장 증가 부분 수열 길이"가 최대 연결 개수다.
 *   - n≤40,000 이므로 O(n²) DP는 위험. tails 배열 + 이분탐색으로 O(n log n)에 푼다.
 *
 * 사용자 풀이 대비 개선점:
 *   - 방향 유지(증가 부분 수열)라는 관찰은 정확했음. 이를 LIS로 정식화하고 O(n log n) 구현으로 완성.
 *     (LIS도 엄연히 DP 기반이지만, 이분탐색 최적화 버전을 쓴다.)
 *
 * 시간복잡도: O(n log n)
 * 공간복잡도: O(n)
 */
class Solution2Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] tails = new int[n];   // tails[k] = 길이 k+1인 증가 수열들의 가능한 최소 끝값
        int len = 0;

        for (int i = 0; i < n; i++) {
            int x = Integer.parseInt(st.nextToken());
            // x가 들어갈 위치를 lowerBound로 찾아 교체(없으면 뒤에 추가) → 끝값을 항상 최소로 유지
            int pos = lowerBound(tails, len, x);
            tails[pos] = x;
            if (pos == len) len++;
        }

        System.out.println(len);
    }

    // [0, len) 구간에서 key 이상이 처음 나오는 인덱스
    private static int lowerBound(int[] arr, int len, int key) {
        int lo = 0, hi = len;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < key) lo = mid + 1; else hi = mid;
        }
        return lo;
    }
}
