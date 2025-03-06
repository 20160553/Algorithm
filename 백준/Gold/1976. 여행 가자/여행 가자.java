import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int find(int a, int[] p) {
        if (p[a] == a) return a;
        return p[a] = find(p[a], p);
    }

    private static void union(int a, int b, int[] p) {
        a = find(a, p);
        b = find(b, p);

        if (a == b) return;

        p[b] = a;
    }

    public static void main(String[] args) throws IOException {
        /**
         * n <= 200
         * m <= 1000
         *
         * MST로 풀이 가능
         *
         */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        int[] p = new int[n];
        StringTokenizer st;

        for (int i = 0; i < n; i++) {
            p[i] = i;
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < n; j++) {
                int linked = Integer.parseInt(st.nextToken());
                if (j <= i || linked == 0) continue;
                union(i, j, p);
            }
        }

        st = new StringTokenizer(br.readLine());
        String answer = "YES";
        int preParent = find(Integer.parseInt(st.nextToken()) - 1, p);
        for (int i = 1; i < m; i++) {
            int city = Integer.parseInt(st.nextToken()) - 1;
            if (find(city, p) != preParent) {
                answer = "NO";
                break;
            }
        }
        System.out.println(answer);
        br.close();
    }
}