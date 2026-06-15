package com.algo.day3;

import java.util.*;
import java.io.*;
import java.util.function.Function;


/*
TODO: 복습
문제 설명

문제 설명
    w × h 크기의 직사각형 격자 모양의 땅 속에 보물이 있습니다. 보물은 단 하나 존재하며, 격자 칸 중 한 곳에 있습니다. 당신은 굴착 로봇으로 보물을 찾아내 발굴하려 합니다.
    굴착 로봇에 명령을 보내면 격자에서 원하는 열을 세로로 파낼 수 있습니다.
    격자의 각 열은 굴착 가능한 최대 깊이가 각각 다를 수 있습니다. 굴착 로봇이 col열을 파도록 명령을 보내면 가능한 최대 깊이만큼 땅을 판 뒤 돌아옵니다.
    만약 col열에 보물이 있었다면 보물을 가지고 돌아오며, 보물이 없었더라도 col열을 기준으로 왼쪽 방향에 보물이 있는지 오른쪽 방향에 보물이 있는지에 대한 정보를 가지고 돌아옵니다.
    이때 땅을 팔 때마다 굴착 가능한 깊이만큼 비용이 발생합니다.
    각 열의 최대 깊이를 담은 1차원 정수 배열 depth와 사용 가능한 총비용을 나타내는 정수 money가 매개변수로 주어집니다. 또한 굴착 로봇이 특정 열을 파도록 하는 excavate 함수가 주어집니다.
    이때, excavate 함수를 호출해 보물을 찾아 발굴하고, 보물이 있었던 열을 return 하도록 solution 함수를 완성해 주세요.
    정답 판정을 받으려면 총비용이 money를 초과하지 않아야 하며, excavate 함수를 호출해 한 번 이상 0을 return 받고, 보물이 있었던 열을 return 해야 합니다.
    money보다 적은 비용으로 보물을 찾더라도 추가 점수가 주어지지는 않습니다.
    따라서 각 채점 테스트 케이스에서 총비용이 money를 초과하지 않고 보물을 찾아냈다면, 사용한 총비용과 관계없이 동일한 점수를 받습니다.

제한사항
    2 ≤ depth의 길이 = w ≤ 200
        depth[i]는 i+1열의 굴착 가능한 최대 깊이를 나타냅니다.
        1 ≤ depth[i] ≤ 100,000
    1 ≤ money ≤ depth의 원소의 합
        운에 맡기지 않고 100% 확률로 보물을 찾기 위한 최소 비용 ≤ money
    excavate 함수는 굴착할 열의 위치(1 이상 w 이하의 정수)를 전달받고, 굴착 결과에 따라 -1, 0, 1 중 하나를 return 합니다.
        1 ~ w 사이의 정수가 아닌 값을 전달하는 경우 오답으로 판정합니다.
        보물을 찾은 경우 0, 보물이 왼쪽 방향에 있다면 -1, 오른쪽 방향에 있다면 1을 return 합니다.
        excavate 함수를 호출할 때마다 굴착할 열의 최대 깊이만큼 비용이 발생합니다.
        주어진 비용을 초과하지 않는 범위 내에서 excavate 함수를 원하는 만큼 호출해도 됩니다.
        excavate 함수 사용 예시가 초기 코드로 주어집니다. 해당 코드는 1열 ~ w열을 순서대로 굴착해, 보물을 찾은 경우 해당 열을 return 하는 코드입니다.


입출력 예
depth 	money 	result
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10] 	55 	3
[1, 1, 1, 1, 1] 	3 	5
[2, 100, 1, 100, 3, 100, 1] 	200 	6
[2, 100, 1, 100, 3, 100, 1] 	200 	5
[3, 2, 1, 2, 3, 2, 1, 2] 	8 	5
[1, 1000, 1, 1, 1, 10, 15, 1] 	1002 	2

 */

public class Solution1 {
    public static void main(String[] args) {
        /*
        * 비용 제한 있어서 이진 탐색 아님.
        * 그리디도 아님.
        *
        * 이게 최적해가 된다고?
        *
        * */
    }

}

/*
 * 핵심 아이디어: "최악의 경우 비용을 최소화"하는 탐색 트리를 구간 DP로 미리 설계한다.
 *   - 구간 [lo,hi]에서 열 c를 파면: 찾으면(0) 끝, 왼쪽(-1)이면 [lo,c-1], 오른쪽(+1)이면 [c+1,hi]로 좁혀진다.
 *   - 한 열을 팔 때 비용은 항상 depth[c]이고, 결과는 우리가 고를 수 없으므로 최악(둘 중 더 비싼 쪽)을 가정해야 한다.
 *   - dp[lo][hi] = min_c ( depth[c] + max(dp[lo][c-1], dp[c+1][hi]) )  → 일반 이진탐색이 아닌 "가중치(최적 BST) 미니맥스" DP.
 * 사용자 풀이 대비: 사용자는 단순 이진/그리디로는 안 된다는 직관까지만 도달. 정답은 구간 DP로 최적 분기 열을 미리 정한 뒤 그 트리대로 excavate를 호출하는 것.
 * 시간복잡도: O(w^3) (구간 길이 × 시작점 × 분기 후보), w ≤ 200 → 약 8 × 10^6. 공간복잡도: O(w^2).
 */
class Solution1Answer {
    // --- 채점 시뮬레이션용(실제 플랫폼에서는 excavate가 외부로 주어짐) ---
    static int treasureCol;   // 1-indexed
    static long usedCost;
    static int[] simDepth;

    static int excavate(int col) {
        usedCost += simDepth[col - 1];
        if (col == treasureCol) return 0;
        return (treasureCol < col) ? -1 : 1;
    }
    // ------------------------------------------------------------------

    public static int solution(int[] depth, int money) {
        int w = depth.length;
        long[][] dp = new long[w + 1][w + 1];  // dp[lo][hi]: [lo,hi]가 보물 범위일 때 최악 비용
        int[][] pick = new int[w][w];           // [lo,hi]에서 최적으로 팔 열

        for (int len = 1; len <= w; len++) {
            for (int lo = 0; lo + len - 1 < w; lo++) {
                int hi = lo + len - 1;
                long best = Long.MAX_VALUE;
                int bc = lo;
                for (int c = lo; c <= hi; c++) {
                    long left = (c > lo) ? dp[lo][c - 1] : 0;   // c가 끝이면 한쪽 구간은 빈 구간(비용 0)
                    long right = (c < hi) ? dp[c + 1][hi] : 0;
                    long val = (long) depth[c] + Math.max(left, right);
                    if (val < best) { best = val; bc = c; }
                }
                dp[lo][hi] = best;
                pick[lo][hi] = bc;
            }
        }

        // 설계된 트리대로 실제 굴착 진행
        int lo = 0, hi = w - 1;
        while (lo <= hi) {
            int c = pick[lo][hi];
            int r = excavate(c + 1);           // excavate는 1-indexed
            if (r == 0) return c + 1;
            else if (r == -1) hi = c - 1;      // 보물이 왼쪽
            else lo = c + 1;                   // 보물이 오른쪽
        }
        return -1; // 도달 불가
    }

    public static void main(String[] args) {
        int[][] depths = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {1, 1, 1, 1, 1},
                {2, 100, 1, 100, 3, 100, 1},
                {2, 100, 1, 100, 3, 100, 1},
                {3, 2, 1, 2, 3, 2, 1, 2},
                {1, 1000, 1, 1, 1, 10, 15, 1},
        };
        int[] moneys = {55, 3, 200, 200, 8, 1002};
        int[] expected = {3, 5, 6, 5, 5, 2};

        for (int t = 0; t < depths.length; t++) {
            simDepth = depths[t];
            treasureCol = expected[t]; // 해당 테스트의 보물 위치를 숨겨두고 탐색
            usedCost = 0;
            int found = solution(depths[t], moneys[t]);
            boolean ok = (found == treasureCol) && (usedCost <= moneys[t]);
            System.out.printf("case %d: found=%d cost=%d/%d -> %s%n",
                    t + 1, found, usedCost, moneys[t], ok ? "PASS" : "FAIL");
        }
    }
}
