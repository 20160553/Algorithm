import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] p = new int[n];
        int[][] costs = new int[n][n];

        LinkedList<int[]> flows = new LinkedList<>();
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            p[i] = i;
            for (int j = 0; j < n; j++) {
                int cost = Integer.parseInt(st.nextToken());
                costs[i][j] = cost;
                if (j > i) flows.add(new int[]{i, j, cost});
            }
        }
        Collections.sort(flows, Comparator.comparingInt(o -> o[2]));

        long answer = 0;

        for (int[] flow: flows) {
            if (union(flow[0], flow[1], p)) answer += flow[2];
        }

        System.out.println(answer);
    }

    private static int find(int a, int[] p) {
        if (a == p[a]) return a;
        return p[a] = find(p[a], p);
    }

    private static boolean union(int a, int b, int[] p) {
        a = find(a, p);
        b = find(b, p);

        if (a == b) return false;
        p[b] = a;
        return true;
    }

}