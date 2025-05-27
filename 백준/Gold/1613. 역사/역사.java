import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static void floyd_warshall(int[][] costs) {

        int n = costs.length - 1;

        for (int k = 1; k <= n; k++) { //경유지
            for (int i = 1; i <= n ; i++) { //출발지
                if (k == i) continue;
                if (costs[i][k] == -1) continue;
                for (int j = 1; j <= n ; j++) { //목적지
                    if (i == j || k == j) continue;
                    if (costs[i][j] == 1 || costs[k][j] == -1) continue;
                    costs[i][j] = 1;
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {

        /*
         * n <= 400, k, r <= 50,000
         *
         * 최단 거리? => 다익스트라 or 밸먼포드 or 플로이드 워샬
         *
         * 모든 노드 -> 모든 노드... => 플로이드 워샬 N ^ 3....
         *
         * 4^3 * 10^6 => 될 듯?
         *
         * 될 거 같은데?
         *
         */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        int n, k, r;
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[][] costs = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                costs[i][j] = -1;
            }
        }

        //그래프 만들기
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());

            int fast, last;
            fast = Integer.parseInt(st.nextToken());
            last = Integer.parseInt(st.nextToken());

            costs[fast][last] = 1;
        }

        //모든 경로에 대한 최단거리 구하기
        floyd_warshall(costs);

        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());

            int v1, v2;
            v1 = Integer.parseInt(st.nextToken());
            v2 = Integer.parseInt(st.nextToken());

            int result1, result2;
            result1 = costs[v1][v2];
            result2 = costs[v2][v1];

            if (result1 + result2 < -1) {
                answer.append(0);
            } else if (result1 == 1) {
                answer.append(-1);
            } else {
                answer.append(1);
            }

            answer.append("\n");

        }
        System.out.println(answer);

        br.close();
    }
}