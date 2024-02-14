import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 시간제한 : 1초 -> 1억 연산 이하라는 의미
         * n <= 100만, m <= 1_000
         * 수열은 n개로 이루어짐
         *
         * 합이 M으로 나누어짐
         *
         * 완탐 시 -> 10^12 => 1억 초과
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n, m;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int sum = 0;
        Map<Integer, Integer> modularMap = new HashMap<>();
        st = new StringTokenizer(in.readLine());
        modularMap.put(0, 1);
        while (st.hasMoreTokens()) {
            int num = Integer.parseInt(st.nextToken()) % m;
            sum = (sum + num) % m;
            modularMap.put(sum, modularMap.getOrDefault(sum, 0) + 1);
        }

        long answer = 0;

        for (int value : modularMap.values()) {
            answer += (value * (value - 1L)) / 2;
        }

        System.out.println(answer);
    }
}