import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int find(int a, int[] p) {
        if (a == p[a]) return a;
        return p[a] = find(p[a], p);
    }

    static boolean union(int a, int b, int[] p) {
        a = find(a, p);
        b = find(b, p);

        if (a == b) return false;
        p[a] = b;
        return true;
    }

    static class Line {
        int start;
        int end;
        int cost;

        public Line(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        /*
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        int n;
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        ArrayList<Line> lines = new ArrayList<>();
        int[] p = new int[n];
        int remained = 0;
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            p[i] = i;
            for (int j = 0; j < str.length(); j++) {
                char c = str.charAt(j);

                if (c == '0') continue;
                int cost = 0;
                if (c >= 'a') cost = c - 'a' + 1;
                else cost = c - 'A' + 27;
                lines.add(new Line(i, j, cost));
                remained += cost;
            }
        }

        Collections.sort(lines, Comparator.comparingInt(o -> o.cost));

        for (Line line : lines) {
            if (union(line.start, line.end, p))
                remained -= line.cost;
        }

        for (int i = 1; i < p.length; i++) {
            if (find(i, p) != find(i - 1, p)) {
                remained = -1;
                break;
            }
        }

        answer.append(remained);
        System.out.println(answer);
        br.close();
    }
}