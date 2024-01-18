import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
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

    static int n, m;
    static int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
    static int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };
    static int[][] s2d2;
    static int[][] grounds;
    static Comparator<int[]> myComparator = new Comparator<int[]>() {

        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[0] - o2[0];
        }
    };
    static PriorityQueue<int[]> trees = new PriorityQueue<>(myComparator);
    static PriorityQueue<int[]> aliveTrees = new PriorityQueue<>(myComparator);

    public static void main(String[] args) {
        FastIn sc = new FastIn();

        n = sc.nextInt();
        m = sc.nextInt();
        int k = sc.nextInt();

        grounds = new int[n + 1][n + 1];
        s2d2 = new int[n + 1][n + 1];

        // 초기화
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                s2d2[i][j] = sc.nextInt();
                grounds[i][j] = 5;
            }
        }
        for (int i = 0; i < m; i++) {
            int y = sc.nextInt();
            int x = sc.nextInt();
            trees.offer(new int[] { sc.nextInt(), y, x });
        }

        for (int i = 0; i < k; i++) {
            // 봄, 여름
            springAndSummerAction();
//            // 가을
            fallAction();
//            // 겨울
            winterAction();
        }
        System.out.println(trees.size());
    }

    public static void springAndSummerAction() {
        PriorityQueue<int[]> t = trees;
        int[][] diedTrees = new int[n + 1][n + 1];
        // 봄
        while (!trees.isEmpty()) {
            int[] tree = trees.poll();

            if (tree[0] > grounds[tree[1]][tree[2]]) {
                diedTrees[tree[1]][tree[2]] += tree[0] / 2;
            } else {
                grounds[tree[1]][tree[2]] -= tree[0];
                tree[0] += 1;
                aliveTrees.offer(tree);
            }
        }
        trees = aliveTrees;
        aliveTrees = t;
        // 여름
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (diedTrees[i][j] == 0)
                    continue;
                grounds[i][j] += diedTrees[i][j];
            }
        }
    }

    public static void fallAction() {
        PriorityQueue<int[]> t = trees;
        // 봄
        while (!trees.isEmpty()) {
            int[] tree = trees.poll();
            aliveTrees.offer(tree);
            if (tree[0] % 5 == 0) { // 번식
                for (int i = 0; i < dx.length; i++) {
                    int ty = tree[1] + dy[i];
                    int tx = tree[2] + dx[i];
                    if (0 < ty && ty <= n && 0 < tx && tx <= n) {
                        aliveTrees.offer(new int[] { 1, ty, tx });
                    }
                }
            }
        }
        trees = aliveTrees;
        aliveTrees = t;
    }

    public static void winterAction() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grounds[i][j] += s2d2[i][j];
            }
        }
    }
}