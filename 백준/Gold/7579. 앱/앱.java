import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        * 1초 -> 1억 미만
        * 앱 개수 : n, 필요한 메모리 : 1 <= M <= 10_000_000, 재부팅 비용 : c
        *
        * MAX_COST = 100 * 100
        *
        * */

        final int MAX_COST = 10_001;

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int n, m;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[] memories, costs;

        memories = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        costs = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        long[] dp = new long[MAX_COST];

        for (int i = 0; i < dp.length; i++)
            dp[i] = 0;

        for (int i = 0; i < n; i++) {
            int memory = memories[i];
            int cost = costs[i];
            for (int j = dp.length - 1; j >= 0; j--) {
                if (j - cost < 0)
                    break;
                dp[j] = Math.max(dp[j], dp[j - cost] + memory);
            }
        }

        int answer = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] >= m) {
                answer = i;
                break;
            }
        }
        System.out.println(answer);
    }
}