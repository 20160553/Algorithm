import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {


    static class FastIn {
        BufferedReader br;
        StringTokenizer st;
        
        public FastIn() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        
        public String next() {
            if (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        
        public int nextInt() {
            return Integer.parseInt(next());
        }
        
    }
    
    static int k, w, h, ans;
    static int[][] map;
    static boolean[][][] v;
    static int[] hDy = { -1, 1, -2, 2, -2, 2, -1, 1 };
    static int[] hDx = { -2, -2, -1, -1, 1, 1, 2, 2 };
    static int[] dy = { 1, 0, -1, 0 };
    static int[] dx = { 0, 1, 0, -1 };

    public static boolean visit(int y, int x, Queue<int[]> q, int tk) {
        if (y < 0 || y >= h || x < 0 || x >= w)
            return false;
        if (v[tk][y][x])
            return false;
        if (map[y][x] == 1)
            return false;

        if (y == h - 1 && x == w - 1) {
            return true;
        }

        v[tk][y][x] = true;
        q.add(new int[] { y, x, tk });

        return false;
    }

    public static void bfs() {

        int day = 0;
        Queue<int[]> q = new LinkedList();

        for (int i = 0; i <= k; i++) {
            v[i][0][0] = true;
        }
        
        // y, x 순서
        q.add(new int[] { 0, 0, k });

        while (!q.isEmpty()) {
            int qSize = q.size();
            for (int idx = 0; idx < qSize; idx++) {
                int[] cP = q.poll();
                int cy = cP[0];
                int cx = cP[1];
                int ck = cP[2];

                if (ck > 0) {
                    for (int i = 0; i < hDy.length; i++) {
                        int ty = cy + hDy[i];
                        int tx = cx + hDx[i];

                        if (visit(ty, tx, q, ck - 1)) {
                            ans = day + 1;
                            return;
                        }
                    }
                }
                for (int i = 0; i < dy.length; i++) {
                    int ty = cy + dy[i];
                    int tx = cx + dx[i];

                    if (visit(ty, tx, q, ck)) {
                        ans = day + 1;
                        return;
                    }
                }
            }
            day++;
        }

    }

    public static void main(String[] args) {

        FastIn sc = new FastIn();

        k = sc.nextInt();
        w = sc.nextInt();
        h = sc.nextInt();
        ans = -1;

        if (h == 1 && w == 1) {
            System.out.println(0);
            return;
        }

        map = new int[h][w];
        v = new boolean[k+1][h][w];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        bfs();

        System.out.println(ans);
    }

}