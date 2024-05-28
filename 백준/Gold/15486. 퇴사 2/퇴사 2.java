import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
//    DP
    public static void main(String[] args) {
        FastInput fi = new FastInput();
        int n = fi.nextInt();
        int[] arr = new int[n + 2];
        int[] tArr = new int[n + 2];
        int[] pArr = new int[n + 2];

        for (int i = 1; i <= n; i++) {
            tArr[i] = fi.nextInt();
            pArr[i] = fi.nextInt();
        }

        for (int i = 1; i <= n+1; i++) {
            arr[i] = Math.max(arr[i], arr[i-1]);
            if (i + tArr[i] < n + 2)
                arr[i + tArr[i]] = Math.max(arr[i + tArr[i]], arr[i] + pArr[i]);
        }
//        System.out.println(Arrays.toString(arr));
        System.out.println(arr[n+1]);
    }

    static class FastInput {
        BufferedReader br;
        StringTokenizer st;

        public FastInput() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
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
}