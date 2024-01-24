import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        /*
        * 1 <= n <= 10만
        * 1 <= m <= n
        * lesson 길이 <= 1만
        *
        * 1_000_000_000
        *
        * */
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n, m;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[] lessons = new int[n];
        int answer = Integer.MAX_VALUE;

        st = new StringTokenizer(in.readLine());

        int left = 0, right = 0;
        lessons[0] = Integer.parseInt(st.nextToken());
        left = lessons[0];
        for (int i = 1; i < n; i++) {
            int now = Integer.parseInt(st.nextToken());
            lessons[i] = now + lessons[i - 1];
            left = Math.max(now, left);
        }
        right = lessons[n - 1];

        while (left <= right) {
            int middle = (left + right) / 2;
            int cnt = 1, pre = 0;

            for (int i = 0; i < n; i++) {
                if (lessons[i] > middle) {
                    break;
                }
                pre = i;
            }

            for (int i = pre; i < n; i++) {
                if (lessons[i] - lessons[pre] > middle) {
                    cnt++;
                    pre = i - 1;
                }
            }

            if (cnt < m) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        if (left > lessons[n - 1])
            left--;
        System.out.println(left);
    }
}