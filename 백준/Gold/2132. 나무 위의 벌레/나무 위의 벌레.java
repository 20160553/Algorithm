import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int maxFruit = -1;
    static int maxIdx = -1;

    static int[] fruits;
    static List<List<Integer>> edges;

    static void dfs(int idx, int previous, int fruit) {
        fruit += fruits[idx];
        if (maxFruit < fruit) {
            maxFruit = fruit;
            maxIdx = idx;
        } else if (maxFruit == fruit) {
            maxIdx = Math.min(idx, maxIdx);
        }

        for (int i : edges.get(idx)) {
            if (i == previous) continue;
            dfs(i, idx, fruit);
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
        fruits = new int[n + 1];
        edges = new ArrayList<>();

        st = new StringTokenizer(br.readLine());
        edges.add(new ArrayList<>());
        for (int i = 1; i <= n; i++) {
            fruits[i] = Integer.parseInt(st.nextToken());
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            edges.get(a).add(b);
            edges.get(b).add(a);
        }

        int idx = 1;
        dfs(idx, -1, 0);
        int start = maxIdx;
        maxFruit = -1;
        maxIdx = -1;

        dfs(start, -1, 0);
        System.out.println(maxFruit + " " + Math.min(maxIdx, start));
        br.close();
    }
}