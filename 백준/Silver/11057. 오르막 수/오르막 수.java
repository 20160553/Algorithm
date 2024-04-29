import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        * 1억이하
        * 1000의 자리까지
        * 앞 부터 끝까지 오름차순 유지 (똑같은건 허용)
        *
        * 특정 자리수가 X일 때 가능한 경우의 수
        *
        * 0 1 2 3 4 5 6 7 8 9 SUM
        * 1의 자리 : 각 자리수 1
        * 1 1 1 1 1 1 1 1 1 1 10
        * 2의 자리 : 자기보다 낮은거
        * 10 9 8 7 6 5 4 3 2 1 55
        * 3의 자리 :
        * 55 45 36 28 21 15 10 6 3 1 220
        *
        * 4
        * 220 165 120 84 56 35 20 10 4 1 x
        *
        * sum * 10
        *
        * dp = int[n + 1][11]
        *
        * dp[n + 1][0] = dp[n][10]
        * dp[n + 1][x] = dp[n + 1][x - 1] - dp[n][x - 1] (x < 10)
        * dp[n + 1][10] = sum(dp[n + 1][k]) % 10_007 (k < 10)
        *
        * answer = dp[n][10]
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int MODULAR = 10_007;
        int n = Integer.parseInt(br.readLine());

        int[][] dp = new int[n + 1][11];

        for (int i = 0; i < 9; i++)
            dp[1][i] = 1;
        dp[1][10] = 10;

        for (int i = 2; i < dp.length; i++) {
            dp[i][0] = dp[i - 1][10];
            int sum = dp[i][0];
            for (int j = 1; j < 10; j++) {
                dp[i][j] = dp[i][j - 1] - dp[i - 1][j - 1];
                if (dp[i][j] < 1) dp[i][j] = dp[i][j] + MODULAR;
                sum = (sum +  dp[i][j]) % MODULAR;
            }
            dp[i][10] = sum;
        }

        System.out.println(dp[n][10]);
    }
}