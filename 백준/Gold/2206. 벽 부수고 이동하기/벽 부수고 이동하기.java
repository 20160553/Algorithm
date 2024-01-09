import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int MAX_VALUE = 1000000;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx=  {0, -1, 0, 1};

    public static void findStartBFS(int sy, int sx, int gy, int gx, int[][][] maze) {
        Queue<int[]> q = new LinkedList<>();
        maze[gy][gx][1] = 1;
        q.offer(new int[] {gy, gx}); // y좌표, x좌표, 방문 최소 비용

        while (!q.isEmpty()) {
            int[] currentInformation = q.poll();

            for (int i = 0; i < 4; i++) {
                int nextY = currentInformation[0] + dy[i];
                int nextX = currentInformation[1] + dx[i];

                //맵 범위 벗어난 경우 생략
                if (nextY < 1 || nextY > gy || nextX < 1 || nextX > gx) {
                    continue;
                }
                //벽 이거나 이미 방문한 경우 생략
                if (maze[nextY][nextX][0] != 0 || maze[nextY][nextX][1] != 0) {
                    continue;
                }
                maze[nextY][nextX][1] = maze[currentInformation[0]][currentInformation[1]][1] + 1;
                q.offer(new int[] {nextY, nextX});
            }
        }
    }

    private static int findGoalBFS(int sy, int sx, int gy, int gx, int[][][] maze ) {
        Queue<int[]> q = new LinkedList<>();
        int answer = MAX_VALUE, destroyWallVisitedNum = 0;

        maze[sy][sx][2] = 1;

        if (maze[sy][sx][1] != 0) {
            answer = maze[sy][sx][1];
        } else {
            q.offer(new int[] {sy, sx, destroyWallVisitedNum}); // y좌표, x좌표, 벽 부술 수 있는 지 여부 0: 가능 1 : 불가능, 벽 부순 것 분기
            // 벽 안부수며 bfs
            while (!q.isEmpty()) {
                int[] currentInformation = q.poll();

                for (int i = 0; i < 4; i++) {
                    int nextY = currentInformation[0] + dy[i];
                    int nextX = currentInformation[1] + dx[i];

                    //맵 범위 벗어난 경우 생략
                    if (nextY < 1 || nextY > gy || nextX < 1 || nextX > gx) {
                        continue;
                    }
                    //다음이 목적지 갈 수 있는 경우 answer 갱신
                    if (maze[nextY][nextX][1] != 0) {
                        answer = Math.min(answer, maze[currentInformation[0]][currentInformation[1]][2] + maze[nextY][nextX][1]);
                        continue;
                    }
                    //벽이거나 이미 방문한 경우 생략
                    if (maze[nextY][nextX][0] != 0 || maze[nextY][nextX][2] != 0) {
                        continue;
                    }
                    maze[nextY][nextX][2] = maze[currentInformation[0]][currentInformation[1]][2] + 1;
                    q.offer(new int[] {nextY, nextX});
                }
            }
        }

        maze[sy][sx][3] = 1;
        q.clear();
        q.offer(new int[] {sy, sx, 0}); // y좌표, x좌표, 벽 부술 수 있는 지 여부 0: 가능 1 : 불가능

        while (!q.isEmpty()) {
            int[] currentInformation = q.poll();

            for (int i = 0; i < 4; i++) {
                int nextY = currentInformation[0] + dy[i];
                int nextX = currentInformation[1] + dx[i];
                int nextDestroyable = currentInformation[2];

                //맵 범위 벗어난 경우 생략
                if (nextY < 1 || nextY > gy || nextX < 1 || nextX > gx) {
                    continue;
                }
                //이미 방문한 경우 생략
                if (maze[nextY][nextX][3] != 0) {
                    continue;
                }
                //다음이 목적지에 접근할 수 있는 경우 (벽이 아닌 것 확실)
                if (maze[nextY][nextX][1] != 0) {
                    answer = Math.min(answer, maze[currentInformation[0]][currentInformation[1]][3] + maze[nextY][nextX][1]);
                    if (currentInformation[2] != 0)
                        continue;
                }
                //이미 벽 부쉈는데 벽 안부수고 방문 할 수 있는 곳인 경우
                if (currentInformation[2] != 0 && maze[nextY][nextX][2] != 0) {
                    continue;
                }
                // 다음이 벽인 경우
                if (maze[nextY][nextX][0] != 0) {
                    if (currentInformation[2] == 0) { //부술 수 O
                        nextDestroyable = 1;
                    } else { // 부술 수 X
                        continue;
                    }
                }
                maze[nextY][nextX][3] = maze[currentInformation[0]][currentInformation[1]][3] + 1;
                q.offer(new int[] {nextY, nextX, nextDestroyable});
            }
        }

        if (answer == MAX_VALUE)
            answer = -1;
        return answer;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n, m, sx, sy, gx, gy;

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        sx = 1; sy = 1;
        gx = m; gy = n;

        //maze[][][0] : 벽있는 지도, [1] : 골 -> 시작점 거리 저장, [2] : 방문 처리 벽 부순 케이스 별
        int[][][] maze = new int[n + 1][m + 1][4];

        for (int i = 1; i <= n; i++) {
            String row = in.readLine();
            for (int j = 1; j <= m; j++) {
                maze[i][j][0] = row.charAt(j - 1) - '0';
            }
        }

        findStartBFS(sy, sx, gy, gx, maze);
        System.out.println(findGoalBFS(sy, sx, gy, gx, maze));
    }
}