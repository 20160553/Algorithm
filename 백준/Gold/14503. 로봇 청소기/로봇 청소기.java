import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static class FastIn {
        BufferedReader br;
        StringTokenizer st;

        public FastIn() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            if (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

    }

    static int[][] map;
    static int d = 0;
    static int y;
    static int x;
    static int[] dx = { 0, 1, 0, -1 };
    static int[] dy = { -1, 0, 1, 0 };
    static int count = 0;

    static void actionRobot() {
        //현재 위치를 청소. 지도 상 청소된 곳은 -1
        map[y][x] = -1;
        count++;
        int preCount = count;
        while (!isExit()) {
            //현재 위치를 청소. 지도 상 청소된 곳은 -1
            if (map[y][x] == 0) {
                map[y][x] = -1;
                count++;
            }
//            if (preCount != count) {
//                for (int i = 0; i < map.length; i++) {
//                    System.out.println(Arrays.toString(map[i]));
//                }
//                System.out.println("----------------------------" + count);
//                preCount = count;
//            }
            //왼쪽으로 회전
            d = (d + 3) % 4;
            int leftY = y + dy[d];
            int leftX = x + dx[d];
            //직진할 수 있으면 직진
            if (!isWall(leftY, leftX) && map[leftY][leftX] != -1){
                y = leftY;
                x = leftX;
                //현재 위치를 청소. 지도 상 청소된 곳은 -1
                if (map[y][x] == 0) {
                    map[y][x] = -1;
                    count++;
                }
            }
        }
    }
    
    static boolean isExit() {
        int tx, ty;
        //종료 조건 체크, 상하좌우.
        for (int i = 0; i < 4; i++) {
            tx = x + dx[i];
            ty = y + dy[i];
            if (!isWall(ty, tx) && map[ty][tx] == 0) {
//            	System.out.println(map[ty][tx] == 0);
                return false;   
            }
        }
        //후진 불가능
        if (isWall(y - dy[d], x - dx[d]))
            return true;
        else {//후진 가능이면 후진
            y = y - dy[d];
            x = x - dx[d];
//        	System.out.println("후진 x, y : " + x + " " + y);
        	d = (d+1) % 4;
            return false;
        }
    }
    
    static boolean isWall(int ty, int tx) {
        int n = map.length;
        int m = map[0].length;
        //범위 벗어나거나 벽이면
        if (0 > tx || tx >= m || 0 > ty || ty >= n || map[ty][tx] == 1) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        FastIn sc = new FastIn();

        int n = sc.nextInt();
        int m = sc.nextInt();
        y = sc.nextInt();
        x = sc.nextInt();
        d = sc.nextInt();
        map = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                map[i][j] = sc.nextInt();
            }
        }
        actionRobot();
        System.out.println(count);
    }

}
