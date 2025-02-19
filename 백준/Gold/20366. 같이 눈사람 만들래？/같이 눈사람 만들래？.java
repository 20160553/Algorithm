import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {

        /*
         * N <= 600, H <= 10억
         *
         * 1. 완탐
         * 선택, 선택안함을 600번 -> 2^600 => 10^3^60 => 시간초과
         *
         * 2. 눈사람 만들어놓고 여기서 선택.
         *   => 눈사람 만든 재료가 중복 선택될 여지가 있음.. => 탈락
         *       => 눈사람 만든 재료를 저장하면되는 거 아님?
         * 3.
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] snowballs = new int[n];
        for (int i = 0; i < n; i++) {
            snowballs[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(snowballs);

        int answer = Integer.MAX_VALUE;

        for (int a = 0; a < n - 3; a++) {
            for (int b = a + 3; b < n; b++) {
                int c = a + 1;
                int d = b - 1;

                while (c < d) {
                    int h1 = snowballs[a] + snowballs[b];
                    int h2 = snowballs[c] + snowballs[d];
                    answer = Math.min(answer, Math.abs(h1 - h2));
                    if (h1 > h2) {
                        c++;
                    } else
                        d--;
                }
            }
        }

        System.out.println(answer);

    }
}