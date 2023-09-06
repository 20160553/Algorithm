import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        int n, s, answer = 0, sum = 0, cnt = 0;
        n = Integer.valueOf(st.nextToken());
        s = Integer.valueOf(st.nextToken());

        int[] arr = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.valueOf(st.nextToken());
        }


        int start = 0;
        for (int current = 0; current < n; current++) {
            cnt++;
            sum += arr[current];

            while (sum >= s) {
                if (answer == 0 || cnt < answer) {
                    answer = cnt;
                }
                sum -= arr[start++];
                cnt--;
            }
        }
        System.out.println(answer);
    }
}