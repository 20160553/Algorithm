import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static class FastIn {
        BufferedReader br = null;
        StringTokenizer st = null;

        public FastIn() {
                br = new BufferedReader(new InputStreamReader(System.in));

        }

        public String next() {
            if (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }

    private static int solve(int s, int e, int[] arr, int[] prefixSum) {
        int m = (s + e) / 2;
        if ((e - s + 1) % 2 == 0) {
            if (prefixSum[e] - prefixSum[m] != prefixSum[m] - prefixSum[s - 1]) {
                return 0;
            }
        } else {
            if (prefixSum[e] - prefixSum[m] != prefixSum[m - 1] - prefixSum[s - 1]) {
                return 0;
            }
        }


        int l = s - 1, r = e - 1;

        while (l < r) {
            if (arr[l] != arr[r]) {
                return 0;
            }
            l++;
            r--;
        }

        return 1;
    }

    public static void main(String[] args) {
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
        *  
        *
        * */

        FastIn in = new FastIn();
        int n = in.nextInt();
        int[] arr = new int[n];
        int[] prefixSum = new int[n + 1];

        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
            prefixSum[i + 1] = prefixSum[i] + arr[i];
        }
        int m = in.nextInt();

        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < m; i++) {
            int s = in.nextInt();
            int e = in.nextInt();

            answer.append(solve(s, e, arr, prefixSum)+"\n");
        }
        System.out.println(answer);
    }
}