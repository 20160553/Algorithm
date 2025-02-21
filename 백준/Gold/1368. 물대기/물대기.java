import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static class FastIn {
        BufferedReader br = null;
        StringTokenizer st = null;

        public FastIn() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            if (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }

    static class Group {

        Group(int wellIdx, int wellCost) {
            this.wellIdx = wellIdx;
            this.wellCost = wellCost;
        }

        int wellIdx;
        int wellCost;
        int linkCost = 0;

        Group union(Group group, int cost) {
            int linkCostSum = this.linkCost + group.linkCost;
            int maxWellCost = Math.max(this.wellCost, group.wellCost);
            if (cost > maxWellCost) {
                return null;
            }
            if (group.wellCost < this.wellCost) {
                this.wellIdx = group.wellIdx;
                this.wellCost = group.wellCost;
            }
            this.linkCost = linkCostSum + cost;
            return this;
        }

        int getTotalCost() {
            return linkCost + wellCost;
        }
    }

    static int find(int a, int[] p) {
        if (a == p[a]) return a;
        return p[a] = find(p[a], p);
    }

    public static void main(String[] args) {
        /*
        * n <= 300인 자연수 3 * 10 ^ 2
        *
        * 2초 => 2억 이하
        *
        * n^3 => 3^3 * 10^6
        *
        * */

        FastIn in = new FastIn();

        int n = in.nextInt();
        final int MAX_VALUE = 50_000_000;

        int[] wellCosts = new int[n];
        int[] p = new int[n];
        int[][] linkCosts = new int[n][n];
        Group[] groups = new Group[n];

        ArrayList<int[]> links = new ArrayList<>();


        for (int i = 0; i < n; i++) {
            wellCosts[i] = in.nextInt();
            p[i] = i;
            groups[i] = new Group(i, wellCosts[i]);
        }


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                linkCosts[i][j] = in.nextInt();
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                links.add(new int[] {i, j, linkCosts[i][j]});
            }
        }

        links.sort((o1, o2) -> o1[2] - o2[2]);

        for (int[] link: links) {
            int a = link[0];
            int b = link[1];
            int cost = link[2];
            a = find(a, p);
            b = find(b, p);
            if (a == b) continue;
            Group result = groups[a].union(groups[b], cost);
            if (result == null) continue;
            p[b] = p[a] = result.wellIdx;
            groups[a] = groups[b] = result;
        }

        boolean[] v = new boolean[n];
        int answer = 0;

        for (int id: p) {
            id = find(id, p);
            if (v[id]) continue;
            v[id] = true;
            Group g = groups[id];
            answer += g.getTotalCost();
        }

        System.out.println(answer);
    }
}