import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static String dfs(int day, int currentRiceCake, boolean[][] dp, String givedRiceCakes) {
        if (day == 0) {
            return givedRiceCakes;
        }
        String result = "-1";

        for (int i = 1; i < dp[0].length; i++) {
            if (dp[day - 1][i] && i != currentRiceCake) {
                String r = dfs(day - 1, i, dp, currentRiceCake + "\n" + givedRiceCakes);
                if (!r.equals("-1")) return r;
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        StringTokenizer st;

        boolean[][] dp = new boolean[n + 1][10];
        Arrays.fill(dp[0], true);

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());

            HashSet<Integer> riceCakes = new HashSet<>();
            for (int j = 0; j < m; j++) {
                int riceCake = Integer.parseInt(st.nextToken());
                riceCakes.add(riceCake);
            }
            for (int j = 1; j < dp[0].length; j++) {
                if (dp[i][j]) {
                    for (int riceCake : riceCakes) {
                        if (j != riceCake)
                            dp[i + 1][riceCake] = true;
                    }
                }
            }
        }

        String answer = "-1";
        for (int j = 1; j < dp[0].length; j++) {
            if (dp[n][j]) {
                String result = dfs(n, j, dp, "");
                if (!result.equals("-1")) {
                    answer = result;
                    break;
                }
            }
        }

        System.out.println(answer);
        br.close();
    }
}