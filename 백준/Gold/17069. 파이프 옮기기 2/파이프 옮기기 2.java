import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[][] map;
    static long[][][] dp;

    static int[] dy = {0, 1, 1};
    static int[] dx = {1, 0, 1};

    public static void main(String[] args) throws IOException {
        /*
         * 0.5초
         * 512MB> 1024 * 512
         *
         * int 32*32 -> 4096Byte
         * long 32 * 32 -> 8192Byte
         *
         * DFS + DP
         *
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st;
        n = Integer.parseInt(in.readLine());
        map = new int[n][n];
        dp = new long[3][n][n]; // 0 : 가로 1 : 세로 2 : 대각선

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                for (int k = 0; k < 3; k++) {
                    dp[k][i][j] = -1;
                    if (map[i][j] == 1)
                        dp[k][i][j] = 0;
                }
            }
        }

        System.out.println(dfs(0, 1, 0));
    }

    static long dfs(int y, int x, int roatate) {
        if (y == n - 1 && x == n - 1) return 1;
        if (dp[roatate][y][x] != -1) return dp[roatate][y][x];

        dp[roatate][y][x] = 0;
        if (map[y][x] == 1)
            return 0;

        boolean flag = true;
        for (int i = 0; i < dy.length - 1; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (ny < 0 || nx < 0 || ny >= n || nx >= n) {
                flag = false;
                continue;
            }
            if (map[ny][nx] == 1) {
                flag = false;
                continue;
            }
            if (roatate == 2 || roatate == i)
                dp[roatate][y][x] += dfs(ny, nx, i);
        }
        int ny = y + dy[2];
        int nx = x + dx[2];
        if (flag && map[ny][nx] == 0)
            dp[roatate][y][x] += dfs(ny, nx, 2);
        return dp[roatate][y][x];
    }

}