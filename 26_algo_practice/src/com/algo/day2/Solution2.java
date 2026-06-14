package com.algo.day2;

import java.util.*;
import java.io.*;

/*
문제: 보석 금고 경로

N × N 금고가 있다. 각 칸에는 보석 가치가 적혀 있다.

도둑은 (0, 0)에서 시작해서 정확히 K개의 칸을 방문해야 한다. 시작 칸도 방문한 칸에 포함된다.

이동은 상하좌우로만 가능하다.

단, 다음 조건을 모두 만족해야 한다.

조건
같은 칸을 두 번 방문할 수 없다.
장애물 # 칸은 방문할 수 없다.
방문한 칸의 보석 가치 합은 정확히 S여야 한다.
방문 경로에서 인접한 세 칸의 가치가 모두 증가하거나 모두 감소하면 안 된다.

즉, 연속한 세 칸의 가치 a, b, c에 대해 다음은 금지다.

a < b < c
a > b > c
입력
N K S
N개의 줄

각 줄은 길이 N이다.

# : 장애물
0~9 : 해당 칸의 보석 가치
출력

조건을 만족하는 경로의 개수를 출력하라.

제한
3 ≤ N ≤ 8
1 ≤ K ≤ 20
0 ≤ S ≤ 100
예제 입력
4 6 18
1234
2#56
3147
2221
예제 출력
0
 */

public class Solution2 {

    static int n, k, s;
    static int[][] map;
    static boolean[][] v;
    static int answer = 0;

    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, 1, 0, -1};

    static boolean isAvailable(String str) {
        if (str.length() < 3) return true;
        int r1 = str.charAt(0) - str.charAt(1);
        int r2 = str.charAt(1) - str.charAt(2);
        boolean sameFlag = r1 == 0 || r2 == 0;
        boolean sameOrient = (r1 > 0 && r2 > 0) || (r1 < 0 && r2 < 0);

        return sameFlag || !sameOrient;
    }

    static void dfs(int y, int x, int step, int sum, StringBuilder sb) {
        if (step == k) {
            if (sum == s)
                answer++;
            return;
        }

        v[y][x] = true;
        int currentPositionJewelScore = map[y][x];

        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            if (ny < 0 || nx < 0 || ny >= n || nx >= n) continue;
            if (v[ny][nx]) continue;
            int score = map[ny][nx];

            if (score < 0) continue;

            sb.append(score);
            if (sb.length() < 3 || isAvailable(sb.substring(sb.length() - 3))) {
                dfs(ny, nx, step + 1, sum + score, sb);
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        v[y][x] = false;
    }

    public static void main(String[] args) throws IOException {
        /*
         * 1. 중복 방문 금지
         * 2. 장애물 방문 금지
         * 3. 방문한 칸의 보석 가치 합은 정확히 S여야 한다.
         *   => K칸 방문 결과 S가치만큼 보석
         * 4. 방문 경로에서 인접한 세 칸의 가치가 모두 증가하거나 모두 감소하면 안 된다.
         *   => 증가, 증가를 했으면 다음은 무조건 감소 or 같은 가치 가야하는 듯
         *
         *
         *
         * 문제 풀이
         * 1. 일단 지도, 방문 배열 만들어
         * 2. BFS, DFS
         * 3. 각 경로 하나 갈 떄마다 조건 체크해서 가지치기
         *   1) 증가, 감소를 연속으로 3번한 경우 가지치기
         *   2) K칸 방문 안했는데 S가치 초과한 경우 가지치기
         *
         * 최소 거리가 아닌 되는 경우의 수 => N <= 8이라 완탐.
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        v = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            String l = br.readLine();
            for (int j = 0; j < n; j++) {
                char c = l.charAt(j);
                if (c == '#') map[i][j] = -1;
                else map[i][j] = c - '0';
            }
        }

        int sum = map[0][0];
        int[] threeHistory = new int[3];
        v[0][0] = true;
        StringBuilder sb = new StringBuilder();
        sb.append(sum);

        dfs(0, 0, 1, sum, sb);

        System.out.println(answer);
    }

}

/*
 * 모범 답안: DFS 백트래킹 (완전탐색 + 가지치기)
 *
 * 핵심 아이디어:
 * 1. (0,0)부터 4방향 DFS, "방문 칸 수"가 정확히 K인 경로를 탐색
 * 2. 합이 S와 정확히 일치하면 경로 카운트
 * 3. 가지치기: 합 초과(가치는 0 이상이라 안전) + 연속 3칸 단조(증가/감소) 금지
 *
 * 사용자 풀이(Solution2) 대비 개선점:
 * - 합 초과 가지치기(value > S) 추가 — 주석엔 계획됐으나 미구현이던 부분
 * - 단조 검사를 문자열 substring 대신 직전 칸 가치(prev)만 넘겨 좌표값으로 직접 비교
 *
 * ※ 흔한 실수 2가지 (참고):
 *   (1) depth==K로 종료하면 시작칸 포함 K+1칸을 방문하게 됨 → cnt==K로 정확히 K칸
 *   (2) 단조 검사를 "직전 2칸 이후"부터 하면 시작칸 포함 첫 3칸을 빠뜨림
 *       → prev만 유효하면(>=0) 모든 연속 3칸을 빠짐없이 검사
 *
 * 시간복잡도: O(4^K) 최악, 가지치기로 대폭 감소 (N<=8, K<=20)
 * 공간복잡도: O(N^2) 방문 배열 + O(K) 재귀 스택
 */
class Solution2Answer {
    static int n, k, s, answer = 0;
    static int[][] map;
    static boolean[][] vis;
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    // cnt: 지금까지 방문한 칸 수, prev: 직전 칸 가치 (시작칸이면 -1)
    static void dfs(int y, int x, int cnt, int sum, int prev) {
        if (sum > s) return;            // 가치 >= 0 이므로 초과 시 더 줄지 않음
        if (cnt == k) {
            if (sum == s) answer++;
            return;
        }
        vis[y][x] = true;
        int cur = map[y][x];
        for (int d = 0; d < 4; d++) {
            int ny = y + dy[d], nx = x + dx[d];
            if (ny < 0 || nx < 0 || ny >= n || nx >= n) continue;
            if (vis[ny][nx]) continue;
            int val = map[ny][nx];
            if (val < 0) continue;       // 장애물
            // 연속 3칸 (prev, cur, val) 단조 금지: prev가 유효(>=0)할 때만 검사
            if (prev >= 0 && ((prev < cur && cur < val) || (prev > cur && cur > val))) continue;
            dfs(ny, nx, cnt + 1, sum + val, cur);
        }
        vis[y][x] = false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        vis = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            String l = br.readLine();
            for (int j = 0; j < n; j++) {
                char c = l.charAt(j);
                map[i][j] = (c == '#') ? -1 : c - '0';
            }
        }

        dfs(0, 0, 1, map[0][0], -1);     // 시작칸이 1번째 방문 칸
        System.out.println(answer);
    }
}