package com.algo.day13;

/*
벽 부수고 이동하기 3
시간 제한	메모리 제한	제출	정답	맞힌 사람	정답 비율
2초	512 MB	14807	3634	2409	23.685%
문제

N×M의 행렬로 표현되는 맵이 있다. 맵에서 0은 이동할 수 있는 곳을 나타내고, 1은 이동할 수 없는 벽이 있는 곳을 나타낸다. 당신은 (1,1)에서 (N,M)의 위치까지 이동하려 하는데, 이때 최단 경로로 이동하려 한다. 최단 경로는 맵에서 가장 적은 개수의 칸을 지나는 경로를 말하는데, 이때 시작하는 칸과 끝나는 칸도 포함해서 센다. 이동하지 않고 같은 칸에 머물러 있는 경우도 가능하다. 이 경우도 방문한 칸의 개수가 하나 늘어나는 것으로 생각해야 한다.

이번 문제에서는 낮과 밤이 번갈아가면서 등장한다. 가장 처음에 이동할 때는 낮이고, 한 번 이동할 때마다 낮과 밤이 바뀌게 된다. 이동하지 않고 같은 칸에 머무르는 경우에도 낮과 밤이 바뀌게 된다.

만약 이동하는 도중에 벽을 부수고 이동하는 것이 좀 더 경로가 짧아진다면, 벽을 K개까지 부수고 이동해도 된다. 단, 벽은 낮에만 부술 수 있다.

한 칸에서 이동할 수 있는 칸은 상하좌우로 인접한 칸이다.

맵이 주어졌을 때, 최단 경로를 구해 내는 프로그램을 작성하시오.

입력

첫째 줄에 N(1≤N≤1,000), M(1≤M≤1,000), K(1≤K≤10)가 주어진다. 다음 N개의 줄에 M개의 숫자로 맵이 주어진다. (1,1)과 (N,M)은 항상 0이라고 가정하자.

출력

첫째 줄에 최단 거리를 출력한다. 불가능할 때는 -1을 출력한다.
 */

import java.io.*;
import java.util.*;

public class Solution2 {

    static int n, m;
    static int[] dy = {-1, 0, 1, 0, 0};
    static int[] dx = {0, -1, 0, 1, 0};


    public static void main(String[] args) throws IOException {
        /*
         * K개까지 벽 뽀갤 수 있음
         *
         * 표현 필요 : 낮 / 밤
         *
         * 할 수 있는 행위
         * 낮 : 상하좌우 (낮에 가만히 있으면 무조건 손해)
         * 밤 : 상하좌우, 가만히
         *
         * 마스킹? ㄴㄴ 마스킹 필요 없음
         *
         * 핵심 => visited 처리를 어떻게 할 것이냐?
         *
         * 1. 부술 수 있는 벽의 수로 visited 처리? => 가만히 있을 경우 처리 안됨.
         *
         * BFS 돌릴 때, day 정보, 가만히 있을 수 있는지 정보 추가로 넘기면 되나?
         *
         * int[][] visited
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, m, k;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        int sy = 1;
        int sx = 1;

        int[][] v = new int[n + 1][m + 1];
        int[][] map = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                v[i][j] = -1;
            }
        }

        Queue<int[]> q = new LinkedList<>();
        Deque<int[]> nQ = new LinkedList<>();

        q.add(new int[]{0, sy, sx, 0, 0});

        while (!q.isEmpty()) {
            int qSize = q.size();
            nQ.clear();
            for (int i = 0; i < qSize; i++) {
                int[] c = q.poll();
                int date = c[0];
                int cy = c[1];
                int cx = c[2];
                int destroyed = c[3];
                boolean isDay = date % 2 == 0;
                boolean canStay = !isDay && c[4] == 0;
                if (cy == n && cx == m) {
                    System.out.println(date);
                    return;
                }

                for (int j = 0; j < dy.length; j++) {
                    int ny = cy + dy[j];
                    int nx = cx + dx[j];
                    if (ny < 1 || nx < 1 || ny > n || nx  > m) continue;
                    boolean isWall = map[ny][nx] == 1;

                    if (j == dy.length - 1) {
                        if (canStay) {
                            nQ.addLast(new int[] {date + 1, ny, nx, destroyed, 1});
                        }
                    } else {
                        if (v[ny][nx] <= destroyed) {
                            continue;
                        }
                        if (isWall) {
                            if (destroyed >= k) continue;
                            v[ny][nx] = destroyed;
                            nQ.addLast(new int[] {date + 1, ny, nx, destroyed + 1, 0});
                        } else {
                            v[ny][nx] = destroyed;
                            nQ.addFirst(new int[] {date + 1, ny, nx, destroyed, 0});
                        }
                    }
                }
                q.addAll(nQ);
            }
        }

    }
}

/*
 * 모범답안: 상태 4차원 BFS  (y, x, 부순 벽 수, 시간 패리티)
 *
 * 핵심 아이디어
 *  - 모든 행동(이동/대기)은 1칸 비용으로 동일 -> 일반 BFS가 곧 최단거리.
 *  - 낮/밤은 "지금까지 지난 칸 수(cells)"의 패리티로 결정된다.
 *    첫 이동이 낮이므로, 현재 cells가 홀수이면 다음 행동은 낮.
 *  - 벽은 낮에만 부술 수 있다. 따라서 밤에 도착했는데 벽을 부숴야 하면
 *    "대기"로 한 칸 더 머물러 낮으로 바꿔야 한다 -> 대기 동작이 반드시 필요.
 *  - 방문 배열은 (y, x, 부순 벽 수, 패리티) 4차원이어야 한다.
 *    같은 칸·같은 벽 수라도 낮에 왔는지/밤에 왔는지에 따라 미래 선택이 달라지기 때문.
 *
 * 사용자 풀이 대비 개선점
 *  - visited를 부순 벽 수만으로 관리 -> 낮/밤 패리티 구분 누락(상태 부족). 4차원으로 수정.
 *  - 모든 간선이 단위 비용이므로 0-1 BFS(addFirst/addLast)는 불필요·혼란. 일반 큐로 단순화.
 *  - "낮에 가만히 있으면 손해"라고 대기를 사실상 막았으나, 낮 벽 파괴를 위한
 *    대기가 정답에 필요할 수 있다 -> 대기를 항상 허용하고 패리티 visited로 무한루프 방지.
 *  - v[ny][nx] <= destroyed 비교가 뒤집혀 있어 갱신 로직이 부정확했다.
 *
 * 시간복잡도: O(N * M * K)   상태 수 = N*M*(K+1)*2, 각 상태 O(1) 전이
 * 공간복잡도: O(N * M * K)
 */
class Solution2Answer {
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] map = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            String line = br.readLine();
            for (int j = 1; j <= m; j++) {
                map[i][j] = line.charAt(j - 1) - '0';
            }
        }

        // visited[y][x][부순 벽 수][cells 패리티]
        boolean[][][][] visited = new boolean[n + 1][m + 1][k + 1][2];

        // 노드: {y, x, broken, cells}  (cells = 지나온 칸 수 = 답)
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{1, 1, 0, 1});
        visited[1][1][0][1] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int y = cur[0], x = cur[1], broken = cur[2], cells = cur[3];

            if (y == n && x == m) {
                System.out.println(cells);
                return;
            }

            boolean isDay = (cells % 2 == 1); // 다음 행동의 낮/밤
            int np = (cells + 1) & 1;

            for (int d = 0; d < 4; d++) {
                int ny = y + dy[d], nx = x + dx[d];
                if (ny < 1 || nx < 1 || ny > n || nx > m) continue;

                if (map[ny][nx] == 1) {
                    // 벽: 낮이고 부술 여유가 있을 때만
                    if (!isDay || broken >= k) continue;
                    if (visited[ny][nx][broken + 1][np]) continue;
                    visited[ny][nx][broken + 1][np] = true;
                    q.add(new int[]{ny, nx, broken + 1, cells + 1});
                } else {
                    if (visited[ny][nx][broken][np]) continue;
                    visited[ny][nx][broken][np] = true;
                    q.add(new int[]{ny, nx, broken, cells + 1});
                }
            }

            // 대기: 제자리에서 시간만 흘려 낮/밤 전환 (밤->낮 만들어 벽 파괴 대비)
            if (!visited[y][x][broken][np]) {
                visited[y][x][broken][np] = true;
                q.add(new int[]{y, x, broken, cells + 1});
            }
        }

        System.out.println(-1);
    }
}
