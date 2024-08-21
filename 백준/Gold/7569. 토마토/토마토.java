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
        
        FastIn() {
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
    
    static int M, N, H;
    static int[][][] boxes;
    static int[] dy = { 1, 0, -1, 0, 0, 0 };
    static int[] dx = { 0, 1, 0, -1, 0, 0 };
    static int[] dh = { 0, 0, 0, 0, -1, 1 };
    static int ripeNum = 0;
    static int TOTAL_Tomato_NUM = 0;
    static int result = 0;
    static Queue<int[]> q = new LinkedList<>();

    public static boolean isInBoxes(int h, int y, int x) {
        return (-1 < h && h < H && -1 < y && y < N && -1 < x && x < M);
    }

    public static void bfs() {
        while (!q.isEmpty()) {
            int[] cPosition = q.poll();
            int day = cPosition[0];
            result = Math.max(day, result);
            int ch = cPosition[1];
            int cy = cPosition[2];
            int cx = cPosition[3];

            for (int i = 0; i < dy.length; i++) {
                int th = ch + dh[i];
                int ty = cy + dy[i];
                int tx = cx + dx[i];

                if (isInBoxes(th, ty, tx) && boxes[th][ty][tx] == 0) {
                    boxes[th][ty][tx] = 1;
                    ripeNum++;
                    q.offer(new int[] { day + 1, th, ty, tx });
                }
            }
        }
    }

    public static void main(String[] args) {
        FastIn sc = new FastIn();
        M = sc.nextInt();
        N = sc.nextInt();
        H = sc.nextInt();
        boxes = new int[H][N][M];
        // 초기화
        for (int h = 0; h < H; h++) {
            for (int n = 0; n < N; n++) {
                for (int m = 0; m < M; m++) {
                    boxes[h][n][m] = sc.nextInt();
                    if (boxes[h][n][m] == 1) {
                        ripeNum++;
                        q.add(new int[] { 0, h, n, m });
                    }
                    if (boxes[h][n][m] != -1)
                        TOTAL_Tomato_NUM++;
                }
            }
        }
        bfs();
        if (ripeNum == TOTAL_Tomato_NUM)
            System.out.println(result);
        else
            System.out.println(-1);
    }

}
