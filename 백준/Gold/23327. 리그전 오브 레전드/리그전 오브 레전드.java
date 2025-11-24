import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        1. 분할 정복
        if (y - x == 1) return Ax * Ay
        f(x, y) = f(x, y - 1) + f(x + 1, y) - f(x + 1, y - 1) + Ax * Ay
        2.
        */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, q;
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        int[] arr = new int[n + 1];
        long[] square = new long[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            int num = Integer.parseInt(st.nextToken());
            arr[i] = arr[i - 1] + num;
            square[i] = square[i - 1] + num * num;
        }


        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int x, y;
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());

            long result = 0;
            int num = arr[y] - arr[x - 1];
            result = (((long)num * num) - (square[y] - square[x-1])) / 2;

            sb.append(result + "\n");
        }

        System.out.println(sb);
    }
}