import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static long calculateTokaRemained(long i,long a, long b, long k) {
        if (k != 0)
            i = Math.min(i, b / k + 1);
        long An = a + (i - 1) * (2 * -b + (i - 2) * k) / 2;
        return Math.max(0, An);
    }

    public static long calculateDoldolRemained(long i, long a, long c, long d) {
        return Math.max(0, a + c - d * i);
    }

    public static void main(String[] args) throws IOException {
        /*
        * 1초 -> 1억 이하
        * 1024MB -> 1기가
        *
        * i번 째 집까지 남은 거리
        * 토카 : max(0, a - max(0,b*i - k * (i - 1))
        * 돌돌이 : max(0, a + c - d * i)
        *
        * i범위 => 0 ~ 돌돌이가 집에 도착할 수 있을 경우
        *
        * 그 후 이진탐색?
        * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        long a, b, c, d, k;

        StringTokenizer st = new StringTokenizer(in.readLine());
        a = Long.parseLong(st.nextToken());
        b = Long.parseLong(st.nextToken());

        st = new StringTokenizer(in.readLine());
        c = Long.parseLong(st.nextToken());
        d = Long.parseLong(st.nextToken());

        st = new StringTokenizer(in.readLine());
        k = Long.parseLong(st.nextToken());

        long l = 1, r, m;
        r = (a + c) / d + 1;

        while (l <= r) {
            m = (l + r) / 2;
            long tokaRemained;
            tokaRemained = calculateTokaRemained(m + 1, a, b, k);

            if (tokaRemained > 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        long answer = l;
        if (calculateDoldolRemained(l, a, c, d) == 0)
            answer = -1;
        System.out.println(answer);
    }
}