import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 2억 이하
         * 128MB -> 10^6 * 64 인트형
         *
         * 목표 인원 : C
         * 도시 수 : N
         *
         * 구하려는 수 : 투자 최소 비용
         *
         * 행 : 투자할 도시
         * 열 : 목표인원
         *
         * (열, 행) 원소 : 투자 비용
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());

        int c, n;
        c = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        final int MAX_COST = 1000 * 1000 + 1;
        int[][] dp = new int[n + 1][c + 1];

        for (int i = 0; i <= n; i++)
            for (int j = 1; j <= c; j++)
                dp[i][j] = MAX_COST;

        for (int k = 0; k < n; k++) {
            st = new StringTokenizer(br.readLine());

            int cost = Integer.parseInt(st.nextToken());
            int peopleNum = Integer.parseInt(st.nextToken());

            for (int i = 0; i <= c; i++) {
                dp[k + 1][i] = Math.min(dp[k][i], dp[k + 1][i]);
            }

            for (int i = 0; i < c; i++) {
                int j = 0;

                while (i + j * peopleNum < c) {
                    j++;
                    int next = i + j * peopleNum;
                    if (next > c) next = c;
                    int nextCost = dp[k][i] + j * cost;
                    dp[k + 1][next] = Math.min(dp[k + 1][next], nextCost);
                }
            }
        }

        System.out.println(dp[n][c]);
    }
}