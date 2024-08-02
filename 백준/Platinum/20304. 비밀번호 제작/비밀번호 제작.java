import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int binaryOfLength;
    static Queue<Integer> q;
    static int[] pwdSafety;

    public static void main(String[] args) throws IOException {
        /*
        n <= 100만
         */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        binaryOfLength = Integer.toBinaryString(n).length();
        q = new LinkedList<>();
        int m = Integer.parseInt(br.readLine());
        pwdSafety = new int[n + 1];
        Arrays.fill(pwdSafety, Integer.MIN_VALUE);

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            int triedPwd = Integer.parseInt(st.nextToken());
            pwdSafety[triedPwd] = 0;
            q.add(triedPwd);
        }

        System.out.println(getBestSafety());
    }

    private static int getBestSafety() {
        int best = Integer.MIN_VALUE;

        while (!q.isEmpty()) {
            int current = q.poll();
            best = Math.max(best, pwdSafety[current]);

            for (int i = 0; i < binaryOfLength; i++) {
                int result = current ^ (1 << i);
                if (result > n || pwdSafety[result] != Integer.MIN_VALUE) continue;
                pwdSafety[result] = pwdSafety[current] + 1;
                q.add(result);
            }
        }
        return best;
    }

}