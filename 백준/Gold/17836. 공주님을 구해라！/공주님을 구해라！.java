import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 1억이하
         * n, m <= 100
         *
         * 그람 먹을 시 벽 만나는 거 다 뿌실 수 있음
         *
         * 제한시간 T <= 10000
         *
         * 높이 : n, 폭 : m
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n, m, t, answer = -1;

        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][m];
        int[][] v = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        LinkedList<int[]> q = new LinkedList<>();

        v[0][0] = 1;
        //y, x, 걸린 시간, 그람 보유 여부
        q.add(new int[]{0, 0, 0, 0});

        int[] dy = new int[]{-1, 0, 1, 0};
        int[] dx = new int[]{0, 1, 0, -1};


        //BFS
        mainLoop:
        while (!q.isEmpty()) {
            int[] current = q.poll();
            int cy = current[0];
            int cx = current[1];
            int cTime = current[2];
            int gramFlag = current[3];

            for (int i = 0; i < dy.length; i++) {
                int ny = cy + dy[i];
                int nx = cx + dx[i];
                int nFlag = gramFlag;

                // 배열 범위 예외처리
                if (ny < 0 || nx < 0 || ny >= n || nx >= m) continue;
                // 만났거나 그람 없을 때 벽 만난 경우
                if (gramFlag == 0 && (map[ny][nx] == 1 || v[ny][nx] > 0)) continue;
                if (gramFlag == 1 && v[ny][nx] > 1) continue;
                if (cTime == t) continue;
                if (ny == n - 1 && nx == m - 1) {
                    answer = cTime + 1;
                    break mainLoop;
                }
                if (map[ny][nx] == 2) nFlag = 1;
                if (nFlag == 1)
                    v[ny][nx] = 2;
                else v[ny][nx] = 1;
                q.add(new int[]{ny, nx, cTime + 1, nFlag});
            }
        }
        if (answer == -1) System.out.println("Fail");
        else System.out.println(answer);

    }
}