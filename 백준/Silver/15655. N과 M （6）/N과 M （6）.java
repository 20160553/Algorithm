import java.util.*;
import java.io.*;

public class Main {

    static List<String> answer = new ArrayList<>();

    private static void solve(int cnt, int current, int[] arr, String str) {
        if (cnt < 0) return;
        if (cnt == 0) {
            if (answer.isEmpty()) {
                answer.add(str);
            }
            else if (!answer.get(answer.size() - 1).equals(str)) {
                answer.add(str);
            }
        }
        if (current >= arr.length) return;


        String next;
        if (str.isEmpty()) next = arr[current] + "";
        else next = str + " " +  arr[current];
        solve(cnt - 1, current + 1, arr, next);
        solve(cnt, current + 1, arr, str);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, m;

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[] arr = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        solve(m, 0, arr, "");

        StringBuilder sb = new StringBuilder();

        for (String str: answer) {
            sb.append(str + "\n");
        }
        System.out.println(sb);
    }
}