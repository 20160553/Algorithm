import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, m;
        final long MAX = 10_000_000_001l;
        ArrayList<HashMap<Integer, Long>> distances = new ArrayList<>();

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        boolean[] seeFlag = new boolean[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            seeFlag[i] = Integer.parseInt(st.nextToken()) == 1;
            distances.add(new HashMap<>());
        }

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o[1]));

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int start, end, cost;
            start = Integer.parseInt(st.nextToken());
            end = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());

            if ((seeFlag[start] && start != n - 1) || (seeFlag[end] && end != n - 1)) continue;
            distances.get(start).put(end, (long) cost);
            distances.get(end).put(start, (long) cost);
        }

        for (Map.Entry<Integer, Long> i : distances.get(0).entrySet()) {
            pq.add(new long[]{i.getKey(), i.getValue()});
        }

        //dijkstra
        while (!pq.isEmpty()) {
            long[] current = pq.poll();
            int stopover = (int) current[0];
            long distance = current[1];

            if (distances.get(0).getOrDefault(stopover, MAX) < distance) continue;

            for (Map.Entry<Integer, Long> i : distances.get(stopover).entrySet()) {
                int dest = i.getKey();
                long stopoverToDestination = i.getValue();

                if (dest == 0 || dest == stopover) continue;

                boolean flag = distances.get(0).get(stopover) + stopoverToDestination < distances.get(0).getOrDefault(dest, MAX);
                if (flag) {
                    distances.get(0).put(
                            dest,
                            distances.get(0).get(stopover) + stopoverToDestination
                    );
                    pq.add(new long[]{
                            dest, distances.get(0).get(dest)
                    });
                }
            }
        }

        Long answer = distances.get(0).getOrDefault(n - 1, -1l);
        System.out.println(answer);
    }
}