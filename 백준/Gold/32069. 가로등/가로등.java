import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        * 2억 이하 연산
        *
        * l : double
        * n : int
        * k : int
        *
        * k <= l + 1
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long l;
        int n, k;
        l = Long.parseLong(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        StringBuilder answer = new StringBuilder();

        long[] p = new long[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            p[i] = Long.parseLong(st.nextToken());
        }

        int cnt = 0;
        long idx = 0;
        boolean flag = p[n - 1] != l;
        HashSet<Long> set = new HashSet<>();
        mainLoop: while (cnt < k) {
            for (int i = 0; i < n; i++) {
                boolean flag_1 = true;

                if (i == 0) {
                    if (p[i] - idx < 0) flag_1 = false;
                } else {
                    if (p[i] - idx <= p[i - 1] ) flag_1 = false;
                }
                if (flag_1 && !set.contains(p[i] - idx)) {
                    set.add(p[i] - idx);
                    answer.append(idx + "\n");
                    cnt++;
                    if (cnt == k) break mainLoop;
                }

                if (i == n - 1) {
                    if (p[i] + idx > l) continue;
                } else {
                    if (p[i] + idx >= p[i + 1] ) continue;
                }
                if (!set.contains(p[i] + idx)) {
                    set.add(p[i] + idx);
                    answer.append(idx + "\n");
                    cnt++;
                    if (cnt == k) break mainLoop;
                }
            }
            idx++;
        }

        System.out.println(answer);
    }
}