package com.algo.day15;

/*
D2 S2 복습
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

import java.io.*;
import java.util.*;

public class Solution4 {
    static int n, k, s;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};

    static int[][] map;
    static boolean[][] v;

    static int dfs(int y, int x, int cnt, int valueSum, int prev) {
        if (cnt == k) {
            if (valueSum == s)
                return 1;
            return 0;
        }
        int cur = map[y][x];
        int answer = 0;
        for (int i = 0; i < dy.length; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (ny < 0 || nx < 0 || ny >= n || nx >= n || v[ny][nx] || map[ny][nx] == -1 || valueSum > s) {
                continue;
            }

            int value = map[ny][nx];
            if (prev >= 0 && ((prev < cur && cur < value) || (prev > cur && cur > value))) continue;
            v[ny][nx] = true;
            answer += dfs(ny, nx, cnt + 1, valueSum + value, cur);
            v[ny][nx] = false;
        }
        return answer;
    }

    public static void main(String[] args) throws IOException {
        /*
         * 완탐 + 가지치기
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        v = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < n; j++) {
                if (str.charAt(j) == '#')
                    map[i][j] = -1;
                else
                    map[i][j] = str.charAt(j) - '0';
            }
        }
        System.out.println(dfs(0, 0, 1, map[0][0], -1));
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
 * 사용자 풀이(Solution4) 대비 개선점(버그 수정):
 * - ★ 시작칸 방문처리 누락 버그: 원본은 main에서 v[0][0]=true를 하지 않고
 *   dfs 안에서 "다음 칸"만 v[ny][nx]=true로 마킹한다. 그래서 (0,0)은 끝까지
 *   방문 처리되지 않아 경로가 시작칸으로 되돌아올 수 있어 과대 카운트된다.
 *   → 모범답안은 dfs 진입 시 현재 칸 vis[y][x]=true로 마킹해 시작칸까지 포함.
 * - 단조 검사를 좌표값(prev)만 넘겨 직접 비교(문자열 substring 불필요).
 *
 * ※ 흔한 실수 (참고):
 *   (1) depth==K로 종료하면 시작칸 포함 K+1칸 방문 → cnt==K로 정확히 K칸
 *   (2) 단조 검사를 직전 2칸 이후부터 하면 시작 포함 첫 3칸을 빠뜨림
 *       → prev(>=0)만 유효하면 모든 연속 3칸을 빠짐없이 검사
 *
 * 시간복잡도: O(4^K) 최악, 가지치기로 대폭 감소 (N<=8, K<=20)
 * 공간복잡도: O(N^2) 방문 배열 + O(K) 재귀 스택
 */
class Solution4Answer {
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
