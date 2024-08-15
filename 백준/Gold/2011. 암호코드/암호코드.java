import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static final int MODULAR_VALUE = 1_000_000;

    static public int dfs(String num, int current) {
        int returnValue = 0;
        if (current >= num.length()) return 1;

        int first = num.charAt(current) - '0';
        if (first == 0) return returnValue;
        //한 자리수로 취급
        int result = dfs(num, current + 1);
        if (result < 0) return -1;
        returnValue += result;
        returnValue %= MODULAR_VALUE;

        //두 자리수로 취급
        if (num.length() - current > 1) {
            int second = num.charAt(current + 1) - '0';
            if (first == 1) {
                returnValue += dfs(num, current + 2);
                returnValue %= MODULAR_VALUE;
            } else if (first == 2 && second < 7) {
                returnValue += dfs(num, current + 2);
                returnValue %= MODULAR_VALUE;
            } else {
                if (second == 0) {
                    return -1;
                }
            }
        }
        return returnValue % MODULAR_VALUE;
    }

    public static void main(String[] args) throws IOException {
        /*
         * 2억이하
         *
         * 암호 해석 불가 => 숫자 <= 0인경우, 0 이전 값이 3 이상인 이유
         *
         * 앞에서 두 자리씩 끊어서 dfs
         *
         * */


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();

        if (str.charAt(0) == '0' || str.charAt(0) == '-') {
            System.out.println(0);
            return;
        }

        int[][] dp = new int[str.length()][2];
        dp[0][0] = 1;
        for (int i = 1; i < str.length(); i++) {
            int pre = str.charAt(i - 1) - '0';
            int current = str.charAt(i) - '0';

            if (current == 0 && pre > 2) {
                System.out.println(0);
                return;
            }
            if (current != 0)
                dp[i][0] = (dp[i - 1][0] + dp[i - 1][1]) % MODULAR_VALUE;
            if (pre != 0 && pre * 10 + current < 27)
                dp[i][1] = dp[i - 1][0];
        }
        System.out.println((dp[str.length() - 1][0] + dp[str.length() - 1][1]) % MODULAR_VALUE);
    }
}