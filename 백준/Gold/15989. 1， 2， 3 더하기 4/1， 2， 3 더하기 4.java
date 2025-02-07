import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
        * 1초 -> 1억 연산 이하
        *
        * 1, 2, 3의 합으로 나타내는 방법의 경우의 수
        *
        * 흠... 딱 봐도 DP인 것 같은데
        *
        * F_1 -> [1 * 1]
        * F_2 -> [1 * 2], [2 * 1]
        * F_3 -> [1 * 3], [2 + 1], [3]
        * F_4 -> [1 * 4], [2 + 1 * 2, 2 + 2], [3 + 1]
        * F_5 -> [1 * 5], [2 + 1 * 3, 2 + 2 * 2 + 1], [3 + 1 * 2 + 3 + 2 * 1]
        * F_6 -> [1 * 6], [2 + 1 * 4, 2 + 2 + 1 * 2, 2 + 2 * 2], [3 + 1 * 3, 3 + 2 + 1, 3 + 3]
        * F_7 -> [1 * 7], [2 + 1 * 5, 2 + 2 * 1 + 1 * 3, 2 + 2 * 2 + 1], [3 + 1 * 4, 3 + 2 * 1 + 1 * 2, 3 + 2 * 2, 3 + 3 + 1]
        * F_8 -> 10
        * F_9 -> 12
        * F_10 -> 14
        * F_11 -> 16
        * F_12 -> 19
        *
        *
        *
        * * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int tc = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();

        int[][] dp = new int[10_001][3];

        dp[1][0] = 1;
        dp[2][0] = 1;
        dp[2][1] = 1;
        dp[3][0] = 1;
        dp[3][1] = 1;
        dp[3][2] = 1;

        int[] cmd = new int[tc];
        int max = 0;

        for (int i = 0; i < tc; i++) {
            cmd[i] = Integer.parseInt(br.readLine());
            max = Math.max(max, cmd[i]);
        }

        for (int i = 4; i <= max; i++) {
            dp[i][0] = dp[i - 1][0];
            dp[i][1] = dp[i - 2][0] + dp[i - 2][1];
            dp[i][2] = dp[i - 3][0] + dp[i - 3][1] + dp[i - 3][2];
        }

        for (int i = 0; i < tc; i++) {
            int tc_answer = dp[cmd[i]][0] + dp[cmd[i]][1] + dp[cmd[i]][2];
            answer.append(tc_answer + "\n");
        }

        System.out.print(answer);
    }
}