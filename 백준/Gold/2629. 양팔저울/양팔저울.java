import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 시간제한 : 1초 => 1억 미만
         * 메모리 : 128
         *
         * 추 개수 <= 30
         * 추 무게 <= 500
         * 구슬 개수 <= 7
         * 구슬 무게 <= 40_000
         *
         * 추 위치 => 왼쪽 or 오른쪽 or 안올림
         *
         * 완탐 => 3 ^ 30
         *
         * 현재 추 무게 : w
         * 이전 확인 활 수 있는 무게 : p
         * dp[p + w] = true
         * dp[p - w] = dp[p]
         * dp[w - p] = dp[p]
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int wNum, marbleNum;
        int MAX = 40_001;

        wNum = Integer.parseInt(in.readLine());

        int[] weights = new int[wNum];

        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < wNum; i++)
            weights[i] = Integer.parseInt(st.nextToken());

        boolean[][] dp = new boolean[wNum + 1][MAX];
        dp[0][0] = true;

        for (int i = 0; i < wNum; i++) {
            int w = weights[i];
            dp[i + 1][w] = true;
            for (int j = 0; j < MAX; j++) {
                dp[i + 1][j] = dp[i][j] | dp[i + 1][j];
                if (j + w < MAX) {
                    dp[i + 1][w + j] = dp[i][j] | dp[i + 1][w + j];
                }
                if (j - w < 0) {
                    dp[i + 1][w - j] = dp[i][j] | dp[i + 1][w - j];
                } else {
                    dp[i + 1][j - w] = dp[i][j]| dp[i + 1][j - w];
                }
            }
        }

        marbleNum = Integer.parseInt(in.readLine());
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < marbleNum; i++) {
            int marbleW = Integer.parseInt(st.nextToken());
            String available = "N";
            if (dp[wNum][marbleW])
                available = "Y";
            sb.append(available + " ");
        }
        System.out.println(sb);

    }
}