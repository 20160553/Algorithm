import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 1억 이하 연산 -> 10^9
         * 64 * 10 ^ 6
         *
         * N <= 5_000
         * -10억 ~ 10억
         *
         * N개 중 3개를 뽑아 0에 가깝게 만들어라
         *
         * ===================
         * 완탐 -> 5000 * 4999 * 4998 / 6 : 시간초과
         *
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine()), a1 = 0, a2 = 0, a3 = 0;
        long min = 3_000_000_001L;
        int[] liquids = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            liquids[i] = Integer.parseInt(st.nextToken());

        Arrays.sort(liquids);

        loop1:
        for (int i = 0; i < liquids.length - 2; i++) {
            int mid = i + 1, end = liquids.length - 1;

            while (mid < end) {
                long sum = (long)liquids[i] + liquids[mid] + liquids[end];
                long absSum = Math.abs(sum);
                if (min > absSum) {
                    min = absSum;
                    a1 = liquids[i];
                    a2 = liquids[mid];
                    a3 = liquids[end];
                }
                if (sum == 0) {
                    break;
                } else if (sum > 0) {
                    end--;
                } else {
                    mid++;
                }
            }
        }

        System.out.println(a1 + " " + a2 + " " + a3);
    }
}