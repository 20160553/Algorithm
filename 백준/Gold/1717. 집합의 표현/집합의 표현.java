import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int find(int a, int[] parents) {
        if (a == parents[a])
            return a;
        return parents[a] = find(parents[a], parents);
    }

    static void union(int a, int b, int[] parents) {
        a = find(a, parents);
        b = find(b, parents);
        if (a == b) return;

        parents[b] = a;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        int n, m;
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[] parents = new int[n + 1];

        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int cmd = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            switch (cmd) {
                case 0:
                    union(a, b, parents);
                    break;
                case 1:
                    a = find(a, parents);
                    b = find(b, parents);
                    if (a == b) answer.append("YES");
                    else answer.append("NO");
                    answer.append("\n");
                    break;
            }

        }

        System.out.println(answer);

        br.close();
    }
}