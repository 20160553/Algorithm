import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        /*
         * M점 모으기 위한 최소 헌혈 횟수
         * 1초 -> 1억 이하
         * n <= 1000
         * M <= 10 ^ 7
         * 헌혈 점수 a, 휴식일 d
         *
         * 완탐 (헌열 하는 경우 / 안하는 경우) -> 2 ^ 1000 => 1000 ^ 100
         *
         * 그리디 => 안됨
         * DP => 헌혈 횟수당 최대 점수를 기준으로
         *
         * 1. 이전과 현재 둘 다 헌혈 안 한 경우
         * 2. 이전 헌혈 안했고 이번엔 한 경우
         * 3. 이전 헌혈 했고 이번엔 안 한 경우
         * 4. 이전 헌혈 했고 이번에도 한 경우
         *
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int n, m;
        StringTokenizer st = new StringTokenizer(in.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int bloodDonationScore, restPeriod;

        int[] scores = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        st = new StringTokenizer(in.readLine());
        bloodDonationScore = Integer.parseInt(st.nextToken());
        restPeriod = Integer.parseInt(st.nextToken());

        int[][] dp = new int[n + 1][n + restPeriod +1 ];

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < dp[0].length; j++)
                dp[i][j] = -1;
        }

        int answer = -1;

        //i번째 수
        for (int i = 0; i < dp[0].length - 1; i++) {
            for (int j = 0; j <= n; j++) {
                if (dp[j][i] == -1) break;
                if (j < n && i < n)
                    dp[j + 1][i + restPeriod] = Math.max(dp[j + 1][i + restPeriod], dp[j][i] + bloodDonationScore);
                if (i < n)
                    dp[j][i + 1] = Math.max(dp[j][i] + scores[i], dp[j][i + 1]);
                else
                    dp[j][i + 1] = Math.max(dp[j][i], dp[j][i + 1]);
            }
        }

        for (int i = 0; i <= n; i++){
            if (dp[i][dp[0].length - 1] >= m) {
                answer = i;
                break;
            }
        }
        System.out.println(answer);
    }

}