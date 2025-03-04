import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        * 교차한다.
        *   A(s1, e1), B(s2, e2)일 때, s1 < s2 && e1 > e2
        *
        * 시간제한 : 1초
        * 메모리 : 128MB => 4 * 32 * 10 ^ 6 Byte
        *
        * 풀이방법
        * 1. 완탐 : 2 ^ 100 => 10 ^ 3 ^ 10 흠.. 시간초과 날 듯?
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[][] lines = new int[n][2];
        StringTokenizer st;

        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            lines[i][0] = Integer.parseInt(st.nextToken());
            lines[i][1] = Integer.parseInt(st.nextToken());
            dp[i] = 1;
        }

        Arrays.sort(lines, (o1, o2) -> o1[0] - o2[0]);

        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (lines[i][1] > lines[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        System.out.println(n - max);
    }
}