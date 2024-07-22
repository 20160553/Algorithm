import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

    static int n, m;

    //동, 서, 남, 북
    static int[] dx = new int[]{1, -1, 0, 0};
    static int[] dy = new int[]{0, 0, 1, -1};


    public static void main(String[] args) throws IOException {

        /*
         * 2억 이하 연산
         *
         * 지도 크기 : N * M => 100 ^ 2
         *
         * 문제 : 시작 위치에서 목표 위치까지 최단 탐색, 목표 위치 도달 후 목표 방향까지 설정해주어야함.
         *
         * 기본 원리 : 4방위 탐색, visited를 3차원으로 해야하나?
         *
         * 방문 처리 방법 : 특정 좌표에 들어왔을 경우
         *     1. 전진
         *     2. 회전
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        int sy, sx, sp, ey, ex, ep;
        int[][] map = new int[m][n];
        boolean[][][] v = new boolean[m][n][4];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        sy = Integer.parseInt(st.nextToken());
        sx = Integer.parseInt(st.nextToken());
        sp = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        ey = Integer.parseInt(st.nextToken());
        ex = Integer.parseInt(st.nextToken());
        ep = Integer.parseInt(st.nextToken());

        LinkedList<int[]> q = new LinkedList<>();
        v[sy - 1][sx - 1][sp - 1] = true;
        q.offer(new int[] {sy - 1, sx - 1, sp - 1, 0});
        int answer = 0;

        mainLoop: while(!q.isEmpty()) {
            int[] current = q.poll();
            int cy = current[0];
            int cx = current[1];
            int cp = current[2];
            int cmdNum = current[3];

            //전진 1, 2, 3칸
            for (int i = 1; i <= 3; i++) {
                int ny = cy + dy[cp] * i;
                int nx = cx + dx[cp] * i;
                int np = cp;

                if (ny >= 0 && nx >= 0 && ny < m && nx < n && map[ny][nx] == 0) {
                    if (!v[ny][nx][np]) {
                        v[ny][nx][np] = true;
                        q.add(new int[]{ny, nx, np, cmdNum + 1});
                        if (ny == ey - 1 && nx == ex - 1 && np == ep - 1) {
                            answer = cmdNum + 1;
                            break;
                        }
                    }
                } else {
                    break;
                }
            }

            for (int i = 1; i <= 3; i++) {
                int nextCmdNum = cmdNum;
                int np = (cp + i) % 4;
                if (cp % 2 == 0) {
                    if (i == 1) nextCmdNum += 2;
                    else nextCmdNum++;
                } else {
                    if (i == 3) nextCmdNum += 2;
                    else nextCmdNum += 1;
                }
                if (!v[cy][cx][np]) {
                    v[cy][cx][np] = true;
                    q.add(new int[]{cy, cx, np, nextCmdNum});
                    if (cy == ey - 1 && cx == ex - 1 && np == ep - 1) {
                        answer = nextCmdNum;
                        break mainLoop;
                    }
                }
            }
        }

        System.out.println(answer);
    }
}