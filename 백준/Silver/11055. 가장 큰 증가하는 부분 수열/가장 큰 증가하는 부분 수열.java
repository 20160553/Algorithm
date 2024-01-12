import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {

        /*
        * 시간 제한 : 1초 -> 1억 연산 이하
        * 수열 크기 : 1000 => 연산 횟수는 1000! => 브루트 포스 불가능
        * 따라서 다른 방법
        *
        * 1. DP
        * 2...?
        *
        * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(in.readLine());
        int[] a = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] dp = a.clone();
        int answer = a[0];
        for (int i = 1; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i]) {
                    dp[i] = Math.max(dp[j] + a[i], dp[i]);
                }
            }
            answer = Math.max(answer, dp[i]);
        }

        System.out.println(answer);
    }
}