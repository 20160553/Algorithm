import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * n <= 3 * 10^5
         * n ^2 => 9 * 10^10
         *
         * 조합 : 2 ^ 1000 => 2 ^ 10 ^ 100
         * => 완탐으로는 시간초과
         * dp인가보네
         *
         * dp => 2 거듭제곱
         * dp[0] => 1
         * dp[1] => 2
         * dp[2] => 4
         * dp[3] => 8
         * dp[5] => 16
         * dp[6] => 32
         * ...
         *
         * 주현지수 : 최대 - 최소
         *
         *
         * 스코빌 지수 : 정수 최대값 가능 => 2개 더하면 정수 범위 벗어남 합은 long으로
         *
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        long[] hotIndexes = new long[n];
        long[] dp = new long[n];
        final int MODULAR_NUM = 1_000_000_007;

        dp[0] = 1;

        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1] * 2 % MODULAR_NUM;
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            hotIndexes[i] = Long.parseLong(st.nextToken());
        }

        //정렬
        long answer = 0;
        Arrays.sort(hotIndexes);

        for (int i = 0; i < n; i++) {
            answer += (hotIndexes[i] % MODULAR_NUM) * dp[i] % MODULAR_NUM;
            answer -= (hotIndexes[i] % MODULAR_NUM) * dp[n - i - 1] % MODULAR_NUM;
            answer = ((answer % MODULAR_NUM) + MODULAR_NUM) % MODULAR_NUM;
        }

        System.out.println(answer % MODULAR_NUM);
    }
}