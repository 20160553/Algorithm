import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        /*
        * 앞 뒤와 색깔이 같지 않아야 한다.
        *
        * 맨 처음이 특정 색깔일 때,
        * 몇 번 집이,
        * 몇 번 색일 때 비용을 저장 (3차원 배열)
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        final int MAX_COST = 1_000_001;
        int n = Integer.parseInt(br.readLine());
        int answer = MAX_COST;
        int[][] map = new int[n][3];
        int[][][] dp = new int[3][n][3];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                dp[i][0][j] = MAX_COST;
                if (i == j) dp[i][0][j] = map[0][i];
            }
            for (int k = 1; k < n; k++) {
                for (int j = 0; j < 3; j++) {
                    dp[i][k][j] = MAX_COST;
                }
            }
        }

        for (int firstColor = 0; firstColor < 3; firstColor++) {
            for (int i = 1; i < n; i++) {
                for (int currentColor = 0; currentColor < 3; currentColor++) {
                    for (int previousColor = 0; previousColor < 3; previousColor++) {
                        if (currentColor == previousColor) continue;
                        dp[firstColor][i][currentColor] = Math.min(dp[firstColor][i][currentColor], dp[firstColor][i - 1][previousColor] + map[i][currentColor]);
                    }
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) continue;
                answer = Math.min(answer, dp[i][n - 1][j]);
            }
        }

        System.out.println(answer);
    }
}