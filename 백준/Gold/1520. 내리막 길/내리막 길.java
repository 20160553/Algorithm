import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[] dy = new int[]{1, 0, -1, 0};
    static int[] dx = new int[]{0, 1, 0, -1};
    static int[][] map;
    static int[][] dp;
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        /*
         * 2초 -> 2억 이하
         * 128 MB -> KB -> 2 ^ 17 => 2^16 Int
         *
         * M, N <= 500
         * 높이 <= 10_000
         *
         * 총 경우의 수?
         *
         * 1. 완탐 => 500 * 500, BFS로는 풀릴려나?
         * 2. DP 일 것 같네요?
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int n, m;

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][m];
        dp = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1;
            }
        }
        dfs(0, 0);

        System.out.println(dp[0][0]);
    }

    static int dfs(int y, int x) {
        if ((y == map.length - 1 && x == map[0].length - 1)) {
            return 1;
        }

        if (dp[y][x] != -1) return dp[y][x];

        dp[y][x] = 0;

        for (int i = 0; i < dy.length; i++) {
            int ny, nx;
            ny = y + dy[i];
            nx = x + dx[i];

            if (ny < 0 || nx < 0 || ny >= map.length || nx >= map[0].length)
                continue;
            if (map[y][x] <= map[ny][nx]) continue;
            dp[y][x] += dfs(ny, nx);
        }

        return dp[y][x];
    }

}