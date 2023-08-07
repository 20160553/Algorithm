import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        int n, m;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.valueOf(br.readLine());
        ArrayList<String> list = new ArrayList<>();

        char first = ' ', last = ' ';
        for (int i = 0; i < n; i++) {
            list.add(br.readLine());
        }
        for (int i = 0; i < n; i++) {
            if (n == 1) break;
            if (list.get(i).equals("?")) {
                if (i == 0) {
                    String next = list.get(i + 1);
                    first = ' ';
                    last = next.charAt(0);
                }
                else if (i == n - 1) {
                    String prev = list.get(i - 1);
                    first = prev.charAt(prev.length() - 1);
                    last = ' ';
                }
                else {
                    String prev = list.get(i - 1);
                    String next = list.get(i + 1);
                    first = prev.charAt(prev.length() - 1);
                    last = next.charAt(0);
                }
            }
        }

        m = Integer.valueOf(br.readLine());
        for (int i = 0; i < m; i++) {
            String current = br.readLine();

            if (!list.contains(current) &&
            (first == ' ' || current.charAt(0) == first) &&
            (last == ' ' || current.charAt(current.length() - 1) == last)){
                System.out.println(current);
                return;
            }
        }
    }
}
