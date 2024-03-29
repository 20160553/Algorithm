import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        다익스트라
        메모리 : 128MB => 10 ^ 6 * 128
        n, m <= 50_000

        배열 선언 시
        4 * 25 * 10 ^ 8

        넘네?



        * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n, m;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        ArrayList<HashMap<Integer, Integer>> roads = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            roads.add(new HashMap<>());
            roads.get(i).put(i, 0);
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            int start, end, value;
            start = Integer.parseInt(st.nextToken());
            end = Integer.parseInt(st.nextToken());
            value = Integer.parseInt(st.nextToken());

            HashMap<Integer, Integer> startBarn = roads.get(start);
            HashMap<Integer, Integer> endBarn = roads.get(end);

            startBarn.put(end, Math.min(value, startBarn.getOrDefault(end, Integer.MAX_VALUE)));
            endBarn.put(start, Math.min(value, startBarn.get(end)));
        }

        System.out.println(dijkstra(n, roads));
    }

    private static int dijkstra(int n, ArrayList<HashMap<Integer, Integer>> roads) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((Comparator.comparingInt(o -> o[1])));

        for (Map.Entry<Integer, Integer> road: roads.get(1).entrySet()) {
            int dest = road.getKey();
            int value = road.getValue();
            if (dest == 1) continue;

            pq.add(new int[] {dest, value});
        }

        while(!pq.isEmpty()) {
            int[] current = pq.poll();
            int stopover = current[0];
            int stopoverValue = current[1];

            if (roads.get(1).getOrDefault(stopover, Integer.MAX_VALUE) < stopoverValue) {
                continue;
            }

            for (Map.Entry<Integer, Integer> road: roads.get(stopover).entrySet()) {
                int dest = road.getKey();
                int value = stopoverValue + road.getValue();

                if(roads.get(1).getOrDefault(dest, Integer.MAX_VALUE) <= value) continue;
                roads.get(1).put(dest, value);
                pq.add(new int[] {dest, value});
            }
        }
        return roads.get(1).get(n);
    }

}