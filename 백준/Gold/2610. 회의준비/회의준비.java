import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

    static private int find(int i, int[] parents) {
        if (parents[i] == i)
            return i;
        return parents[i] = find(parents[i], parents);
    }

    static private void union(int a, int b, int[] parents, int[] depth) {
        a = find(a, parents);
        b = find(b, parents);
        if (a == b)
            return;
        if (depth[b] < depth[a]) {
            int temp = a;
            a = b;
            b = temp;
        }
        parents[a] = b;
        if (depth[b] == depth[a])
            depth[b]++;
    }

    public static void main(String[] args) throws IOException {
        /*
         * n <= 100
         *
         * 1초 <=
         *
         * 모든 노드 다익 => N^2 LogN = > 10_000 * LogN
         *
         * */
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n, m;
        n = Integer.parseInt(in.readLine());
        m = Integer.parseInt(in.readLine());

        int[] parents = new int[n + 1];
        int[] depth = new int[n + 1];

        boolean[][] edges = new boolean[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            parents[i] = i;
            depth[i] = 1;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            int a, b;
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            edges[a][b] = true;
            edges[b][a] = true;

            union(a, b, parents, depth);
        }

        HashMap<Integer, LinkedList<Integer>> groups = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            int p = find(i, parents);
            if (!groups.containsKey(p))
                groups.put(p, new LinkedList<Integer>());
            groups.get(p).add(i);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(groups.size() + "\n");

        LinkedList<Integer> leaders = new LinkedList<>();
        for (LinkedList<Integer> group : groups.values()) {
            int leader = -1;
            int minCnt = 103;
            for (int i: group) {
                boolean[] v = new boolean[n + 1];
                int cnt = 0;
                LinkedList<Integer> q = new LinkedList<>();
                v[i] = true;
                q.add(i);

                while(!q.isEmpty()) {
                    int qSize = q.size();
                    cnt++;
                    for (int j = 0; j < qSize; j++) {
                        int start = q.poll();
                        for (int end = 1; end <= n; end++) {
                            if (start == end || !edges[start][end] || v[end])
                                continue;
                            v[end] = true;
                            q.add(end);
                        }
                    }
                }
                if (minCnt > cnt) {
                    leader = i;
                    minCnt = cnt;
                }
            }
            leaders.add(leader);
        }
        Collections.sort(leaders);
        for (int l: leaders) {
            sb.append(l + "\n");
        }
        System.out.println(sb);
    }
}