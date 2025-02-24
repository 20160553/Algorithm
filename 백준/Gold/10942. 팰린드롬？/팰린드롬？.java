import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private  static int[][] determinePalindrome(int[] numbers) {
        int[][] palindrome = new int[numbers.length][numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            palindrome[0][i] = 1;

            if (i + 1 < numbers.length && numbers[i] == numbers[i + 1])
                palindrome[1][i] = 1;
        }

        for (int size = 0; size < palindrome.length - 2; size++) {
            for (int index = 1; index < numbers.length - size - 1; index++) {
                if (palindrome[size][index] == 0) {
                    continue;
                }

                if (numbers[index - 1] == numbers[index + size + 1]) {
                    palindrome[size + 2][index - 1] = 1;
                }
            }
        }
        return palindrome;
    }

    static BufferedReader br;
    public static void main(String[] args) throws IOException {
        /*
         * 자바 : 2.5초 제한
         * 2억 5천만 이하 연산
         *
         * n <= 2_000,
         * m <= 1_000_000
         *
         * 1. 완탐
         *  => m * n -> 2 * 10 ^ 9 : 시간초과
         *
         * 2. 누적합 이용 가지치기
         *  => 통과는 됨 ㅋㅋ. 하지만 더 좋은 풀이가 있는 듯
         *
         * 3. DP
         *
         *
         * */

        br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] numbers = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }

        int[][] palindrome = determinePalindrome(numbers);

        int m = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()) - 1;
            int e = Integer.parseInt(st.nextToken()) - 1;

            answer.append(palindrome[e - s][s] + "\n");
        }
        System.out.print(answer);

        br.close();
    }
}