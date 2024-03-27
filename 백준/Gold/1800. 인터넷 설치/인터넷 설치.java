import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int MAX = 1_000_000_001;

    public static void main(String[] args) throws IOException {

        /*
         * 시간제한 : 2초 => 2억 이하
         * 메모리 : 128MB
         * 학생 수 : N <= 1000
         * 연결 목표 => N번이 인터넷 되는 것
         * 케이블 선의 수 : P <= 10_000
         * 공짜 케이블 수 : K < N
         *
         * --------
         * 알고리즘
         * 최단거리 => 다익?
         *
         * 1 -> K 최소 거리인데 거치는 경로를 모두 기억해야함
         * 2. -> 그 후 가장 비싼 경로 k개 제거
         *
         * 최단거리 X => 최단거리가 거치는 경로가 많을 경우 공짜 케이블 적용하는 과정에서 Cost가 더 커질 수 있음
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n, p, k, answer = -1;

        n = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[] edges = new int[p + 1];

        ArrayList<int[]> lines = new ArrayList<>();
        for (int i = 0; i < p; i++) {
            int[] line = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            lines.add(line);
            edges[i] = line[2];
        }

        edges[p] = 0;
        Arrays.sort(edges);

        int left = 0, right = p;
        if (p >= k)
            right = p - k;
        while (left <= right) {
            int middle = (left + right) / 2;

            if (dijkstra(edges[middle], lines, n, k)) {
                right = middle - 1;
                answer = edges[middle];
            } else {
                left = middle + 1;
            }
        }

        System.out.println(answer);
    }

    private static boolean dijkstra(int cost, ArrayList<int[]> lines, int n, int k) {
        int[][] costs = new int[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                costs[i][j] = MAX;
            }
        }
        for (int[] line : lines) {
            if (line[2] > cost) {
                costs[line[0]][line[1]] = Math.min(costs[line[0]][line[1]], 1);
                costs[line[1]][line[0]] = Math.min(costs[line[1]][line[0]], 1);
            } else {
                costs[line[0]][line[1]] = 0;
                costs[line[1]][line[0]] = 0;
            }
        }

        if (costs[1][n] <= k)
            return true;

        //Dijkstra
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(Comparator.comparingInt(o -> o[1]));

        for (int i = 2; i < n; i++) {
            if (costs[1][i] <= k) {
                pq.add(new int[]{i, costs[1][i]});
            }
        }

        while (!pq.isEmpty()) {
            int[] stopoverInfo = pq.poll();
            int stopover = stopoverInfo[0];
            int stopoverCost = stopoverInfo[1];

            if (costs[1][stopover] < stopoverCost)
                continue;

            for (int next = 2; next <= n; next++) {
                if (next == stopover) continue;
                if (costs[stopover][next] == MAX) continue;

                int currentCost = costs[1][next];
                int nextCost = stopoverCost + costs[stopover][next];

                if (nextCost > k) continue;
                if (currentCost > nextCost) {
                    costs[1][next] = nextCost;
                    pq.add(new int[]{next, nextCost});
                }
            }
        }

        return costs[1][n] <= k;
    }

}