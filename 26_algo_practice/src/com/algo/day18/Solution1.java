package com.algo.day18;


/*
구슬 탈출 2
문제

스타트링크에서 판매하는 어린이용 장난감 중에서 가장 인기가 많은 제품은 구슬 탈출이다. 구슬 탈출은 직사각형 보드에 빨간 구슬과 파란 구슬을 하나씩 넣은 다음, 빨간 구슬을 구멍을 통해 빼내는 게임이다.

보드의 세로 크기는 N, 가로 크기는 M이고, 편의상 1×1 크기의 칸으로 나누어져 있다. 가장 바깥 행과 열은 모두 막혀져 있고, 보드에는 구멍이 하나 있다. 빨간 구슬과 파란 구슬의 크기는 한 칸을 차지하며, 동시에 움직인다.

기울이기는 상, 하, 좌, 우 네 방향으로 할 수 있다. 한 번 기울이면 구슬은 벽이나 다른 구슬에 막힐 때까지 계속 움직인다. 구슬은 손으로 직접 움직일 수 없다.

빨간 구슬이 구멍에 빠지면 성공이고, 파란 구슬이 구멍에 빠지면 실패이다. 빨간 구슬과 파란 구슬이 동시에 구멍에 빠져도 실패이다.

구슬이 움직이는 동안 두 구슬이 같은 칸을 차지할 수는 없다. 이동 과정에서 같은 위치를 지나가는 것은 가능하지만, 이동이 끝났을 때는 반드시 서로 다른 칸에 위치해야 한다.

보드의 상태가 주어졌을 때, 최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 구하는 프로그램을 작성하시오.

---

입력

첫째 줄에 보드의 세로 크기 N, 가로 크기 M이 주어진다.

다음 N개의 줄에는 보드의 상태가 주어진다.

각 문자는 다음 중 하나이다.

. : 빈 칸
# : 벽
O : 구멍
R : 빨간 구슬
B : 파란 구슬

항상 가장 바깥쪽은 벽이며, 구멍은 정확히 하나 존재한다.

빨간 구슬과 파란 구슬도 각각 정확히 하나씩 존재한다.

제한
3 ≤ N, M ≤ 10

---

출력

빨간 구슬을 구멍으로 빼낼 수 있는 최소 횟수를 출력한다.

단, 10번 이하의 기울이기로 성공할 수 없으면 -1을 출력한다.

---

예제 입력 1
5 5
#####
#..B#
#.#.#
#RO.#
#####
예제 출력 1
1
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution1 {

    static int[] dy = new int[] {-1, 0, 1, 0};
    static int[] dx = new int[] {0, 1, 0, -1};
    static int[][] map;

    public static void main(String[] args) throws IOException {
        /*
        * 최소 횟수를 출력하라
        *
        * n, m < 10
        *
        * 횟수 : 최대 10회
        *
        * 10회라서 BFS인 것 같은데?
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n, m;

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][m];

        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < m; j++) {
                char c = str.charAt(j);
                if (i == '#') {
                    map[i][j] = -1;
                }
            }
        }

    }

}

/*
 * [모범답안] 상태 = (빨간 구슬 위치, 파란 구슬 위치). 4방향으로 "기울이기"를 BFS로 최대 10회까지 시도한다.
 *   - 한 번 기울이면 두 구슬이 동시에 벽('#')이나 구멍('O')을 만날 때까지 한 칸씩 굴러간다.
 *   - 굴러간 끝 위치가 같아지면(충돌), 더 많이 움직인 구슬을 한 칸 되돌려 서로 다른 칸에 위치시킨다.
 *   - 파란 구슬이 구멍에 빠지면 그 이동은 무효, 빨간 구슬이 빠지면 그 시점의 이동 횟수가 정답.
 * 사용자 코드 대비 개선점:
 *   (1) 입력 파싱 버그 수정: `if (i == '#')`는 행 인덱스와 문자를 비교해 항상 거짓 — `c`로 비교해야 함.
 *   (2) R/B/O 위치를 전혀 읽지 않음 → 구슬 좌표를 별도로 저장.
 *   (3) 기울이기 시뮬레이션과 BFS 탐색 로직이 전무 → 상태 BFS로 완전히 구현.
 * 시간복잡도: O((N·M)^2 · (N+M)), 공간복잡도: O((N·M)^2) — visited[rY][rX][bY][bX]
 */
class Solution1Answer {

    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, 1, 0, -1};
    static int n, m;
    static char[][] board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        board = new char[n][m];
        int ry = 0, rx = 0, by = 0, bx = 0;
        for (int i = 0; i < n; i++) {
            String row = br.readLine();
            for (int j = 0; j < m; j++) {
                char c = row.charAt(j);
                board[i][j] = c;
                if (c == 'R') { ry = i; rx = j; }
                else if (c == 'B') { by = i; bx = j; }
            }
        }

        System.out.println(solve(ry, rx, by, bx));
    }

    private static int solve(int ry, int rx, int by, int bx) {
        boolean[][][][] visited = new boolean[n][m][n][m];
        java.util.ArrayDeque<int[]> queue = new java.util.ArrayDeque<>();
        queue.add(new int[]{ry, rx, by, bx, 0});
        visited[ry][rx][by][bx] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cy = cur[0], cx = cur[1], dyPos = cur[2], dxPos = cur[3], cnt = cur[4];
            if (cnt == 10) continue; // 10회를 넘어서는 이동은 시도하지 않음

            for (int d = 0; d < 4; d++) {
                int[] rMove = roll(cy, cx, dy[d], dx[d]);
                int[] bMove = roll(dyPos, dxPos, dy[d], dx[d]);
                int nry = rMove[0], nrx = rMove[1];
                int nby = bMove[0], nbx = bMove[1];

                if (board[nby][nbx] == 'O') continue; // 파란 구슬이 빠지면 이 이동은 무효
                if (board[nry][nrx] == 'O') return cnt + 1; // 빨간 구슬이 빠지면 성공

                if (nry == nby && nrx == nbx) {
                    // 같은 칸에서 충돌 - 더 많이 굴러간 구슬을 한 칸 되돌린다
                    if (rMove[2] > bMove[2]) { nry -= dy[d]; nrx -= dx[d]; }
                    else { nby -= dy[d]; nbx -= dx[d]; }
                }

                if (!visited[nry][nrx][nby][nbx]) {
                    visited[nry][nrx][nby][nbx] = true;
                    queue.add(new int[]{nry, nrx, nby, nbx, cnt + 1});
                }
            }
        }
        return -1;
    }

    // 한 방향으로 굴렸을 때의 최종 위치와 이동 칸 수를 반환
    private static int[] roll(int y, int x, int dyDir, int dxDir) {
        int steps = 0;
        while (board[y + dyDir][x + dxDir] != '#' && board[y][x] != 'O') {
            y += dyDir;
            x += dxDir;
            steps++;
        }
        return new int[]{y, x, steps};
    }
}
