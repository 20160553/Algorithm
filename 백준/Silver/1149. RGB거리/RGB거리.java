import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        int n = Integer.parseInt(br.readLine());
        int [][] arr = new int[n+1][3];
        for (int i = 1; i < n+1; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                if (i != 1) {
                    int t1 = (j + 1) % 3;
                    int t2 = (j + 2) % 3;
                    arr[i][j] += Math.min(arr[i-1][t1], arr[i-1][t2]);
                }
            }
        }
        System.out.println(Math.min(Math.min(arr[n][0], arr[n][1]), arr[n][2]));
    }
}
