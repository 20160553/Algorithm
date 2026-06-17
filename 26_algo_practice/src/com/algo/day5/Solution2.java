package com.algo.day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
문제: 합이 K 이하인 가장 긴 연속 부분 수열

정수 배열 arr와 정수 K가 주어진다.
배열에서 연속된 부분 수열 중 원소들의 합이 K 이하인 것들 중에서, 길이가 가장 긴 부분 수열의 길이를 구하라.

단, 배열의 모든 원소는 양의 정수이다.

입력 형식
N K
arr[0] arr[1] ... arr[N-1]
출력 형식
합이 K 이하인 가장 긴 연속 부분 수열의 길이
제한 조건
1 ≤ N ≤ 100,000
1 ≤ K ≤ 1,000,000,000
1 ≤ arr[i] ≤ 10,000
예제 입력 1
8 10
1 2 3 4 5 1 1 2
0 1 3 6 10 15 16 17 19
예제 출력 1
4
 */
public class Solution2 {
    public static void main(String[] args) throws IOException {
        /*
        * K 이하인 가장 긴 부분 수열
        *
        * 투 포인터로 풀리나?
        * l = 0; r = 1
        *
        * 1. sum[r] - sum[l] <= k => r증가
        * 2. sum[r] - sum[l] > k 면 (l - r - 1) 기록하고 l증가
        * 3. l==r이면 r증가. r > n이면 끝
        *
        * */

        int n, k;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[] sum = new int[n+1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + Integer.parseInt(st.nextToken());
        }

        int l = 0; int r = 1;
        int answer = 0;
        while (r <= n) {
            if (r == l) {
                r++;
                continue;
            }
            if (sum[r] - sum[l] <= k) {
                answer = Math.max(answer, r - l);
                r++;
            } else {
                l++;
            }
        }
        System.out.println(answer);
    }
}

/*
 * 모범답안: 슬라이딩 윈도우 (투 포인터)
 *
 * 핵심 아이디어:
 *   모든 원소가 양수 → 윈도우를 오른쪽으로 넓히면 합은 증가, 왼쪽을 줄이면 합은 감소(단조성).
 *   right를 한 칸씩 늘리며 합이 K를 넘으면 left를 당겨 합을 K 이하로 유지하고, 그때의 길이를 기록.
 *
 * 사용자 풀이 대비 개선점:
 *   - 사용자 코드의 입력 루프가 `i < n`이라 마지막 원소를 읽지 않고 sum[n]이 0으로 남는 버그가 있다
 *     (예제는 우연히 4가 나오지만, 답이 마지막 원소를 포함하면 오답).
 *   - 누적합 배열 없이 윈도우 합 변수 하나로 처리해 메모리/인덱스 실수를 줄였다.
 *   - 합/ K 비교를 long으로 두어(최대 1e9 근처) 경계 안전성 확보.
 *
 * 시간복잡도: O(N)  (left, right 각각 최대 N번 이동)
 * 공간복잡도: O(N)  (입력 저장) / 윈도우 자체는 O(1)
 */
class Solution2Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        int[] arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(st.nextToken());

        long windowSum = 0;
        int left = 0, answer = 0;
        for (int right = 0; right < n; right++) {
            windowSum += arr[right];
            // 모든 원소가 양수이므로 left를 당기면 합이 단조 감소 → while로 K 이하까지 축소
            while (windowSum > k) {
                windowSum -= arr[left++];
            }
            answer = Math.max(answer, right - left + 1);
        }
        System.out.println(answer);
    }
}
