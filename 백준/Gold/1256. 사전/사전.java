import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 2억 연산 이하
         * 메모리 : 128 => 62 * 10 ^ 6 인트형
         *
         * n, m <= 100
         * k <= 10 ^ 9
         *
         * a: n개 z: m개
         *
         * 완탐 : 2 ^ (n + m)
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, m, k;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++)
            dp[i][0] = 1;
        for (int i = 1; i <= m; i++)
            dp[0][i] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                if (dp[i][j] > k)
                    dp[i][j] = k;
            }
        }

        StringBuilder answer = new StringBuilder();
        if (dp[n][m] < k) {
            answer.append(-1);
        } else {
            while (n > 0 && m > 0) {
                if (dp[n - 1][m] >= k) {
                    answer.append('a');
                    n--;
                } else {
                    answer.append('z');
                    k -= dp[n - 1][m];
                    m--;
                }
            }
            while (n > 0) {
                answer.append('a');
                n--;
            }
            while (m > 0) {
                answer.append('z');
                m--;
            }
        }
        System.out.println(answer);
    }
}