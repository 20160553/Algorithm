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

        ArrayList<ArrayList<int[]>> lines = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            lines.add(new ArrayList<>());

        for (int i = 0; i < p; i++) {
            int[] line = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            lines.get(line[0]).add(new int[]{line[1], line[2]});
            lines.get(line[1]).add(new int[]{line[0], line[2]});
            edges[i] = line[2];
        }

        edges[p] = 0;
        Arrays.sort(edges);

        int[] dist = new int[n + 1];

        int left = 0, right = p;
        if (p >= k)
            right = p - k;
        while (left <= right) {
            int middle = (left + right) / 2;

            if (dijkstra(edges[middle], lines, n, k, dist)) {
                right = middle - 1;
                answer = edges[middle];
            } else {
                left = middle + 1;
            }
        }

        System.out.println(answer);
    }

    private static boolean dijkstra(int cost, ArrayList<ArrayList<int[]>> lines, int n, int k, int[] dist) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(Comparator.comparingInt(o -> o[1]));

        Arrays.fill(dist, MAX);

        for (int[] line : lines.get(1)) {
            if (line[1] > cost)
                dist[line[0]] = 1;
            else
                dist[line[0]] = 0;
            pq.add(new int[]{line[0], dist[line[0]]});
        }
        dist[1] = 0;

        if (dist[n] <= k)
            return true;

        while (!pq.isEmpty()) {
            int[] stopoverInfo = pq.poll();
            int stopover = stopoverInfo[0];
            int stopoverCost = stopoverInfo[1];

            if (dist[stopover] < stopoverCost)
                continue;

            for (int[] line: lines.get(stopover)) {
                int next = line[0];
                int nextCost = stopoverCost;
                if (line[1] > cost) nextCost++;

                if (nextCost < dist[next]) {
                    dist[next] = nextCost;
                    pq.add(new int[]{next, nextCost});
                }
            }

        }

        return dist[n] <= k;
    }

}