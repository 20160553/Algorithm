import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        long[] cables = new long[k];

        long l = 1, r = 0;

        for (int i = 0; i < k; i++) {
            cables[i] = Long.parseLong(br.readLine());
            r = Math.max(r, cables[i]);
        }

        long answer = 0;
        while (l <= r) {
            long m = (l + r) / 2;
            int cnt = 0;
            for (long cable: cables) {
                cnt += cable / m;
                if (cnt >= n) break;
            }
            if (cnt >= n) {
                l = m + 1;
                answer = Math.max(answer, m);
            } else {
                r = m - 1;
            }
        }
        System.out.println(answer);
    }
}