import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int[][] arr = null;
    //주사위 윗면 : 1, 아랫면 : 6, 
    static int[] dice = new int[7];
    static int n, m;
    static int x;
    static int y;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    
    public static void moveDice(int orientation) {
        int tx = x + dx[orientation];
        int ty = y + dy[orientation];
        if (0 > tx || m - 1 < tx || 0 > ty || n - 1 < ty) {
            return;
        }
        x = tx;
        y = ty;
        int preTop = dice[1];
        int preBottom = dice[6];
        if (orientation < 2) { //동, 서
            if (orientation == 0) {
                dice[1] = dice[4];
                dice[6] = dice[3];
                dice[4] = preBottom;
                dice[3] = preTop;
            } else {
                dice[1] = dice[3];
                dice[6] = dice[4];
                dice[4] = preTop;
                dice[3] = preBottom;
            }
        } else { //북, 남
            if (orientation == 2) {
                dice[1] = dice[5];
                dice[6] = dice[2];
                dice[5] = preBottom;
                dice[2] = preTop;
            } else {
                dice[1] = dice[2];
                dice[6] = dice[5];
                dice[2] = preBottom;
                dice[5] = preTop;
            }
        }
        if (arr[y][x] == 0) {
            arr[y][x] = dice[6];
        } else {
            dice[6] = arr[y][x];
            arr[y][x] = 0;
        }
        System.out.println(dice[1]);
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int k;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        arr = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
//          System.out.println(Arrays.toString(arr[i]));
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            moveDice(Integer.parseInt(st.nextToken()) - 1);
        }
    }
}
