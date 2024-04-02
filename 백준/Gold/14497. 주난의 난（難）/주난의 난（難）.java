import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};


    public static void main(String[] args) throws IOException {
        /*
         * n, m <= 300
         * 완탐 : 9 * 10 ^ 4
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n, m;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int sy, sx, gy, gx;
        st = new StringTokenizer(in.readLine());

        sy = Integer.parseInt(st.nextToken()) - 1;
        sx = Integer.parseInt(st.nextToken()) - 1;
        gy = Integer.parseInt(st.nextToken()) - 1;
        gx = Integer.parseInt(st.nextToken()) - 1;

        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            String s = in.readLine();
            for (int j = 0; j < m; j++) {
                if (s.charAt(j) == '#')
                    map[i][j] = 1;
                else if (s.charAt(j) == '*')
                    map[i][j] = 0;
                else map[i][j] = s.charAt(j) - '0';
            }
        }


        int answer = 0;
        Queue<int[]> q = new LinkedList<>();

        if (gy == sy && sx == gx) {
            System.out.println(0);
            return;
        }

        while (map[gy][gx] > 0) {
            answer++;
            q.add(new int[]{sy, sx});
            map[sy][sx] = -answer;
            while (!q.isEmpty()) {
                int[] current = q.poll();
                int cy = current[0];
                int cx = current[1];

                for (int i = 0; i < dy.length; i++) {
                    int ny = cy + dy[i];
                    int nx = cx + dx[i];

                    if (ny < 0 || nx < 0 || ny >= n || nx >= m) continue;
                    if (ny == gy && nx == gx) {
                        System.out.println(answer);
                        return;
                    }
                    if (map[ny][nx] != -answer) {
                        if (map[ny][nx] != 1) {
                            q.add(new int[]{ny, nx});
                        }
                        map[ny][nx] = -answer;
                    }
                }
            }
        }

        System.out.println(answer);
    }
}