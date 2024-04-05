import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        /*
         * 1억 이하
         *
         * */
        int answer = 0, T;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        T = Integer.parseInt(in.readLine());

        for (int t = 0; t < T; t++) {
            int n = Integer.parseInt(in.readLine());
            int[][] dp = new int[n + 1][10];
            if (n == 1) {
                sb.append(10 + "\n");
                continue;
            }

            for (int i = 0; i < dp[0].length; i++) {
                dp[1][i] = 1;
            }

            for (int i = 2; i <= n; i++) {
                dp[i][0] = dp[i - 1][7];
                dp[i][1] = dp[i - 1][4] + dp[i - 1][2];
                dp[i][2] = dp[i - 1][1] + dp[i - 1][3] + dp[i - 1][5];
                dp[i][3] = dp[i - 1][6] + dp[i - 1][2];
                dp[i][4] = dp[i - 1][5] + dp[i - 1][7] + dp[i - 1][1];
                dp[i][5] = dp[i - 1][8] + dp[i - 1][4] + dp[i - 1][6] + dp[i - 1][2];
                dp[i][6] = dp[i - 1][9] + dp[i - 1][5] + dp[i - 1][3];
                dp[i][7] = dp[i - 1][4] + dp[i - 1][8] + dp[i - 1][0];
                dp[i][8] = dp[i - 1][7] + dp[i - 1][9] + dp[i - 1][5];
                dp[i][9] = dp[i - 1][6] + dp[i - 1][8];

                for (int j = 0; j < 10; j++)
                    dp[i][j] %= 1_234_567;
            }
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += dp[n][i];
                sum %= 1_234_567;
            }
            sum %= 1_234_567;
            sb.append(sum + "\n");
        }

        System.out.println(sb);
    }

}