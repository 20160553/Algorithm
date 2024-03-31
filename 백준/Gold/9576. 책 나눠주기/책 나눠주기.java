import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 연산 : 2억이하
         *
         * n <= 1000
         * m <= 1000
         *
         * a <= b
         *
         * 책 주는 최대 인원?
         *
         * ============
         * 알고리즘
         *
         * 흠.. 완탐은 딱 봐도 안되는데?
         *
         * DP? 1000 * 1000 * 1000
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(in.readLine());
        StringBuilder sb = new StringBuilder();

        for (int t = 0; t < T; t++) {
            int n, m;
            st = new StringTokenizer(in.readLine());


            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());

            boolean[] books = new boolean[n + 1];
            int[][] students = new int[m][2];
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < 2; j++) {
                    students[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            Arrays.sort(students, (o1, o2) -> {
                if (o1[1] == o2[1])
                    return o1[0] - o2[0];
                return o1[1] - o2[1];
            });

            int answer = 0;

            for (int i = 0; i < students.length; i++) {
                int[] student = students[i];
                for (int j = student[0]; j <= student[1]; j++) {
                    if (!books[j]) {
                        books[j] = true;
                        answer++;
                        break;
                    }
                }
            }
            sb.append(answer + "\n");
        }
        System.out.println(sb);
    }
}