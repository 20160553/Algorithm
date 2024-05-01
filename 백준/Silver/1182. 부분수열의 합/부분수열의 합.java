import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n, s, answer = 0;

        n = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());
        int[] arr = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        List<Integer> list = new ArrayList<>();
        list.add(0);

        for (int i = 0; i < n; i++) {
            int listSize = list.size();
            for (int j = 0; j < listSize; j++) {
                int sum = list.get(j) + arr[i];
                list.add(sum);
                if (sum == s) answer++;
            }
        }

        System.out.println(answer);
    }
}