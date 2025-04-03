import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[][] map;
    static int[] dy = new int[]{-1, 0, 1, 0};
    static int[] dx = new int[]{0, -1, 0, 1};

    static class Result {
        int cnt = 0;
        boolean linkedGround = false;

        public Result(int cnt, boolean linkedGround) {
            this.cnt = cnt;
            this.linkedGround = linkedGround;
        }
    }

    static Result dfs(int y, int x, int h, boolean linkedGround) {
        Result result = new Result(1, linkedGround);
        if (y == 0 || x == 0 || y == map.length - 1 || x == map[0].length - 1 ) {
            linkedGround = true;
            result.linkedGround = true;
        }

        map[y][x]++;

        for (int i = 0; i < dy.length; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            if (ny < 0 || nx < 0 || ny >= map.length || nx >= map[0].length)
                continue;
            if (map[ny][nx] != h) continue;
            Result r = dfs(ny, nx, h, linkedGround);
            result.cnt += r.cnt;
            result.linkedGround |= r.linkedGround;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        /*
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n, m;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][m];

        for (int i = 0; i < n; i++) {
            String row = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = row.charAt(j) - '0';
            }
        }

        int cnt = 0;
        for (int k = 1; k < 9; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    Result result = null;
                    if (map[i][j] == k)
                        result = dfs(i, j, k, false);
                    if (result != null) {
                        if (!result.linkedGround)
                            cnt += result.cnt;
                    }
                }
            }
        }

        answer.append(cnt);
        System.out.println(answer);
        br.close();
    }
}