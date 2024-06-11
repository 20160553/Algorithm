import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 2억 이하
         *
         * n, m => 1000
         *
         * n * m => 10 ^ 6
         *
         * 1. 알고리즘 : 이진탐색 + bfs?
         * log2(10^6) : 19.9316....
         *
         * log2(10^6) * n * m : 10 ^ 7 * 2 : 2천만?
         *
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, m, k;
        final int MAX = 1_000_000;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[][] mineral = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                mineral[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int left = 1, right = MAX, answer = 0;

        //이진탐색
        while (left <= right) {
            int middle = (left + right) / 2;

            //BFS
            boolean result = mine(middle, k, mineral);
            if (result) {
                right = middle - 1;
                answer = middle;
            } else {
                left = middle + 1;
            }
        }

        System.out.println(answer);
    }

    private static boolean mine(int d, int k, int[][] mineral) {
        int h = mineral.length, w = mineral[0].length;
        boolean[][] v = new boolean[h][w];

        int minedAmount = 0;

        for (int i = 1; i < h; i++) {
            minedAmount += bfs(d, i, 0, mineral, v);
            minedAmount += bfs(d, i, w - 1, mineral, v);
        }
        for (int i = 0; i < w; i++) {
            minedAmount += bfs(d, 0, i, mineral, v);
        }

        boolean result = minedAmount >= k;
        return result;
    }

    private static int bfs(int d, int y, int x, int[][] mineral, boolean[][] v) {
        int[] dy = {-1, 0, 1, 0};
        int[] dx = {0, 1, 0, -1};

        int count = 0;
        if (v[y][x] || mineral[y][x] > d) return count;

        LinkedList<int[]> q = new LinkedList<>();

        count++;
        v[y][x] = true;
        q.offer(new int[]{y, x});

        while (!q.isEmpty()) {
            int[] current = q.poll();

            for (int i = 0; i < 4; i++) {
                int ny = current[0] + dy[i];
                int nx = current[1] + dx[i];

                if (ny < 0 || nx < 0 || ny >= v.length || nx >= v[0].length) {
                    continue;
                }
                if (v[ny][nx]) continue;

                v[ny][nx] = true;
                if (mineral[ny][nx] <= d) {
                    count++;
                    q.add(new int[]{ny, nx});
                }
            }
        }
        return count;
    }
}