import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        /*
        * 시간제한 : 2초 => 2억 연산 이하
        * 메모리제한 : 64메가
        * -10억 < t < 10억
        * 1 <= n <= 1000
        * 1 <= m <= 1000
        * 배열 원소는 절대값이 100만을 넘기지 않음
        *
        * 합 최대 => 100만 * 1000 이하 : 10억 이하 => int 범위 내
        *
        * 구간 합 => 세그먼트 트리
        *
        *
        * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t, n, m;
        int[] a, b;

        t = Integer.parseInt(in.readLine());
        n = Integer.parseInt(in.readLine());

        a = new int[n];
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
            if (i > 0)
                a[i] += a[i - 1];
        }

        m = Integer.parseInt(in.readLine());
        b = new int[m];
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < m; i++) {
            b[i] = Integer.parseInt(st.nextToken());
            if (i > 0)
                b[i] += b[i - 1];
        }

        long answer = 0;

        ArrayList<Integer> a_sum = new ArrayList<>();
        ArrayList<Integer> b_sum = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int sum = a[j];
                if (i > 0) sum -= a[i - 1];
                a_sum.add(sum);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = i; j < m; j++) {
                int sum = b[j];
                if (i > 0) sum -= b[i - 1];
                b_sum.add(sum);
            }
        }

        Collections.sort(a_sum);
        Collections.sort(b_sum);

        for (int i: a_sum) {
            int cnt = 0;
            int left = 0;
            int right = b_sum.size() - 1;
            while (left <= right) {
                int middle = (left + right) / 2;
                if (i + b_sum.get(middle) <= t) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }

            cnt = left;
            left = 0;
            right = b_sum.size() - 1;
            while (left <= right) {
                int middle = (left + right) / 2;
                if (i + b_sum.get(middle) < t) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }

            answer += cnt - left;
        }

        System.out.println(answer);

    }
}