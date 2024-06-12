import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

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

    public static void main(String[] args) throws IOException {
        /*
         * 2억 이하
         * 1. 남 여 변갈아
         * 2. 모든 대학교 이동 가능
         * 3. 최단 거리
         *
         * 그냥 MST 인가?
         *
         *
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, m;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[] p = new int[n + 1];
        // false면 남 true면 여
        boolean[] universities = new boolean[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            p[i] = i;
            if (st.nextToken().equals("W")) {
                universities[i] = true;
            }
        }

        ArrayList<int[]> roads = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s, e, cost;
            s = Integer.parseInt(st.nextToken());
            e = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());

            if (universities[s] == universities[e]) continue;

            roads.add(new int[]{s, e, cost});
        }

        Collections.sort(roads, (o1, o2) -> o1[2] - o2[2]);

        int answer = 0;
        for (int[] road : roads) {
            if (union(road[0], road[1], p)) {
                answer += road[2];
            }
        }

        for (int i = 2; i <= n; i++) {
            if (find(i, p) != p[1]) answer = -1;
        }
        System.out.println(answer);
    }
}