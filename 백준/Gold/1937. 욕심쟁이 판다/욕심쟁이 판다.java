import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int[][] dp;
    static int[][] map;
    static int[] dy = new int[] {-1, 0, 1, 0};
    static int[] dx = new int[] {0, 1, 0, -1};
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PriorityQueue<int []> pQ = new PriorityQueue<int[]>((o1, o2) -> o2[2] - o1[2]);
        
        int n = Integer.valueOf(br.readLine());
        map = new int[n][n];
        dp = new int[n][n];
        
        int result = 0;
        
        //map initialize
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n ;j++) {
                dp[i][j] = 0;
                map[i][j] = Integer.valueOf(st.nextToken());
                pQ.add(new int[] {i, j, map[i][j]});
            }
        }
            
        while(!pQ.isEmpty()) {
            int[] current = pQ.poll();
            int cy = current[0];
            int cx = current[1];
            int cValue = current[2]; 
            int nextMaxValue = 0;
            for (int i = 0; i < 4; i++) {
                int ny = cy + dy[i];
                int nx = cx + dx[i];
                if (ny < 0 || nx < 0 || ny >= n || nx >= n) continue;
                if (cValue < map[ny][nx])
                    nextMaxValue = Math.max(nextMaxValue, dp[ny][nx]);
            }
            dp[cy][cx] = nextMaxValue + 1;
            result = Math.max(result, dp[cy][cx]);
        }
        
        System.out.print(result);
    }

}