package com.algo.day17;

/*
수영장 만들기

지민이는 수영장을 만들려고 한다. 수영장을 만들 곳의 크기는 N×M이고, 각 칸은 직육면체이다. 따라서, 각 칸의 직육면체의 높이가 쓰여 있는 다음과 같은 땅을 생각할 수 있다.

16661
61116
16661

이 수영장은 15만큼의 물이 들어있는 수영장을 만들 수 있다. 가운데 3개의 칸에 5만큼 물을 채우면 되기 때문이다.

자 이제 가운데 물을 더 추가했다고 생각하면, 벽(높이가 6인 직육면체)을 넘어서 밖으로 나갈 것이다. 물은 항상 높이가 더 낮은 곳으로만 흐르고, 직육면체의 위의 표면에는 물이 없다. 그리고, 땅의 높이는 0이고, 땅은 물을 무한대로 흡수할 수 있다.

땅의 모양이 주어질 때, 수영장에 물이 얼마나 있을 수 있는지 구하는 프로그램을 작성하시오.

입력

첫째 줄에 N과 M이 주어진다.
N과 M은 50보다 작거나 같은 자연수이다.

둘째 줄부터 N개의 줄에 땅의 높이가 주어진다.
높이는 1보다 크거나 같고, 9보다 작거나 같은 자연수이다.

출력

첫째 줄에 문제의 정답을 출력한다.

예제 입력 1
3 5
16661
61116
16661
예제 출력 1
15
예제 입력 2
4 6
999999
955119
955119
999999
예제 출력 2
48
예제 입력 3
5 9
111111111
115111611
131516161
115111611
111111111
예제 출력 3
7
예제 입력 4
9 13
1111111111111
1555555555551
1511111111151
1511199911151
1511192911151
1511199911151
1511111111151
1555555555551
1111111111111
예제 출력 4
151
 */

import java.util.*;
import java.io.*;

public class Solution2 {
    public static void main(String[] args) throws IOException {
        /*
        * 음...? 빡구현인가?
        *
        * 빡구현으로 풀리면 넘 쉬운 문젠데?
        * 아니지 않겠나??
        *
        * 일단 풀이는
        * 2 -> 9까지 BFS 돌리면 되긴함.
        *
        * 0행 or 0열 접근가능할 때 카운트 안하고.
        *
        * 진짜 이거라면 풀 의미가 없는뎅.
        * */
    }
}

/*
 * [모범답안] "빗물 트래핑 II" — 경계에서 시작하는 우선순위 큐(다익스트라식 확장).
 *   핵심: 물은 자신을 둘러싼 "가장 낮은 벽"을 넘어야 밖으로 빠져나간다.
 *         => 각 칸이 담는 물 = max(0, (그 칸을 둘러싼 최소 벽 높이) - (칸 높이)).
 *   최소 힙에 경계 칸(벽 후보)을 넣고 낮은 벽부터 안쪽으로 확장한다.
 *     - 이웃의 담수량 = max(0, 현재벽 - 이웃높이)
 *     - 이웃의 새 벽 높이 = max(현재벽, 이웃높이)  (더 낮은 곳이면 물이 차서 벽이 올라감)
 * 사용자 접근(2→9 반복 BFS) 대비:
 *   물 높이가 칸마다 달라 여러 수위를 반복 처리해야 하고 재방문/경계 판정이 까다롭다.
 *   힙 한 번의 확장으로 "가장 낮은 탈출 경로"가 자연스럽게 먼저 확정돼 O(NM log NM)에 끝난다.
 * 시간복잡도: O(N·M·log(N·M)), 공간복잡도: O(N·M)
 */
class Solution2Answer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] h = new int[n][m];
        for (int i = 0; i < n; i++) {
            String row = br.readLine();
            for (int j = 0; j < m; j++) h[i][j] = row.charAt(j) - '0';
        }

        boolean[][] visited = new boolean[n][m];
        // {벽 높이, r, c} — 낮은 벽부터 확장
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || j == 0 || i == n - 1 || j == m - 1) { // 경계는 물이 그대로 빠져나감
                    visited[i][j] = true;
                    pq.offer(new int[]{h[i][j], i, j});
                }
            }
        }

        int[] dr = {-1, 1, 0, 0}, dc = {0, 0, -1, 1};
        long water = 0;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int wall = cur[0], r = cur[1], c = cur[2];
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d], nc = c + dc[d];
                if (nr < 0 || nc < 0 || nr >= n || nc >= m || visited[nr][nc]) continue;
                visited[nr][nc] = true;
                water += Math.max(0, wall - h[nr][nc]); // 벽보다 낮으면 그 차이만큼 물이 고임
                pq.offer(new int[]{Math.max(wall, h[nr][nc]), nr, nc});
            }
        }
        System.out.println(water);
    }
}
