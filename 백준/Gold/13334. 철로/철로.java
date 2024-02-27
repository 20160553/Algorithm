import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int MIN = -100_000_000;
    static int MAX = 100_000_000;

    public static void main(String[] args) throws IOException {
        /*
        시간제한 1초 -> 1억 연산 이하
        n명 < 10만

        -1억 ~ 1억
        노선 길이 <= 2억

         */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(in.readLine());
        int[][] peopleInfo = new int[n][2];
        int[] arr = new int[200_000_001];
        StringTokenizer st;

        int min = MAX, max = MIN;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            int h, o;
            h = Integer.parseInt(st.nextToken()) + MAX;
            o = Integer.parseInt(st.nextToken()) + MAX;

            if (h > o) {
                int temp = h;
                h = o;
                o = temp;
            }

            peopleInfo[i][0] = h;
            peopleInfo[i][1] = o;
            max = Math.max(max, o);
        }

        int d = Integer.parseInt(in.readLine());

        for (int[] p: peopleInfo) {
            if (p[1] - p[0] > d) {
                continue;
            }
            int availStart;
            availStart = p[1] - d;
            if (availStart < 0) {
                availStart = 0;
            }
            min = Math.min(min, availStart);
            arr[availStart]++;
            arr[p[0] + 1]--;
        }

        int answer = arr[min];

        for (int i = min + 1; i <= max; i++) {
            arr[i] += arr[i - 1];
            answer = Math.max(answer, arr[i]);
        }
        System.out.println(answer);
    }
}