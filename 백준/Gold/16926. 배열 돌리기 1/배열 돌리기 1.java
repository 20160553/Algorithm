import java.util.*;
import java.io.*;

public class Main {

    private static int[][] turn(int[][] arr) {

        int n = arr.length;
        int m = arr[0].length;
        int[][] ansArr = new int[n][m];
        int i = 0;
        while (i < n / 2) {

            for (int j = i + 1; j < m - i; j++) {
                ansArr[i][j - 1] = arr[i][j];
            }
            for (int j = i; j < m - i - 1; j++) {
                ansArr[n - i - 1][j + 1] = arr[n - i - 1][j];
            }
            i++;
        }

        i = 0;
        while (i < m / 2) {
            for (int j = i; j < n - i - 1; j++) {
                ansArr[j + 1][i] = arr[j][i];
            }
            for (int j = i + 1; j < n - i; j++) {
                ansArr[j - 1][m - i - 1] = arr[j][m - i - 1];
            }
            i++;
        }

        return ansArr;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n, m, r;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        int[][] arr = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < r; i++) {
            arr = turn(arr);
        }

        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                answer.append(arr[i][j] + " ");
            }
            answer.append("\n");
        }

        System.out.println(answer);
        br.close();
    }
}