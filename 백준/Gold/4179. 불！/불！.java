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

    static int n, m, ans, day;
    static char[][] map;
    static boolean[][] vJ;
    static Queue<int []> fireQ = new LinkedList<int[]>(); 
    static Queue<int []> jQ = new LinkedList<int[]>();
    static int[] dy = {1, 0, -1, 0};
    static int[] dx = {0, 1, 0, -1};
    
    public static void bfs() {
        while(!jQ.isEmpty()) {
            int fireSize = fireQ.size();
            for (int idx = 0; idx < fireSize; idx++) {
                int[] tempF = fireQ.poll();
                int fy, fx;
                fy = tempF[0];
                fx = tempF[1];
                
                for (int i = 0; i < 4; i++) {
                    int cy = fy + dy[i];
                    int cx = fx + dx[i];
                    //배열범위 밖이면 스킵
                    if (cy < 0 || cy >= n || cx < 0 || cx >= m) continue;
                    if (map[cy][cx] == '#' || map[cy][cx] == 'F') continue;
                    map[cy][cx] = 'F';
                    fireQ.add(new int[] {cy, cx});
                }
            }
            int jSize = jQ.size();
            for (int idx = 0; idx < jSize; idx++) {
                int[] tempJ = jQ.poll();
                int jy, jx;
                jy = tempJ[0];
                jx = tempJ[1];
                if (jy == 0 || jx == 0 || jy == n - 1 || jx == m - 1) {
                    ans = day + 1;
                    return;
                }
                for (int i = 0; i < 4; i++) {
                    int cy = jy + dy[i];
                    int cx = jx + dx[i];
                    if (cy < 0 || cy >= n || cx < 0 || cx >= m) continue;
                    if (vJ[cy][cx]) continue;
                    if (map[cy][cx] != '.') continue;
                    vJ[cy][cx] = true;
                    jQ.add(new int[] {cy, cx});
                }
            }
            day++;
        }
    }
    
    public static void main(String[] args) {
        
        FastIn sc = new FastIn();
        ans = -1;
        day = 0;
        n = sc.nextInt();
        m = sc.nextInt();
        map = new char[n][m];
        vJ = new boolean[n][m];
        
        for (int i = 0; i < n; i++) {
            String row = sc.next();
            for (int j = 0; j < m; j++) {
                map[i][j] = row.charAt(j);
                if (map[i][j] == 'J') {
                    jQ.add(new int[] {i, j});
                    vJ[i][j] = true;
                    map[i][j] = '.';
                }
                if (map[i][j] == 'F') {
                    fireQ.add(new int[] {i, j});
                }
            }
        }
        
        bfs();
        
        if (ans < 0) System.out.println("IMPOSSIBLE");
        else System.out.println(ans);
    }

}