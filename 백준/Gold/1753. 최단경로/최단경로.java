import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair> {
    int a;
    int b;

    public Pair(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int compareTo(Pair o) {
        return this.a - o.a;
    }

    @Override
    public String toString() {
        return this.a + " " + this.b;
    }
}

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

    // 연결리스트 기반
    public static void dijkstra(int start) {
        PriorityQueue<Pair> pQ = new PriorityQueue<>();
        pQ.add(new Pair(0, start));

        distance[start] = 0;

        while (!pQ.isEmpty()) {
            Pair cP = pQ.poll();
            int dist = cP.a;
            int now = cP.b;

            if (distance[now] < dist || edges[now] == null)
                continue;
            for (ListIterator<int[]> iterator = edges[now].listIterator(); iterator.hasNext();) {
                int[] list = iterator.next();
                int key = list[0];
                int val = list[1];
                int cost = dist + val;
                if (distance[key] > cost) {
                    distance[key] = cost;
                    if (start == now)
                        iterator.add(new int[] {key, cost});
                    else edges[start].add(new int[] {key, cost});
                    pQ.add(new Pair(cost, key));
                }
            }
        }

    }

    static LinkedList<int[]>[] edges;
    static double[] distance;

    public static void main(String[] args) {
        FastIn st = new FastIn();

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = st.nextInt();
        int eNum = st.nextInt();
        int start = st.nextInt();

        distance = new double[n + 1];
        edges = new LinkedList[n + 1];
        final double MAX = Double.MAX_VALUE;
        for (int i = 0; i <= n; i++) {
            distance[i] = MAX;
        }

        // 배열 초기화
        for (int i = 1; i <= eNum; i++) {
            int v, e, w;
            v = st.nextInt();
            e = st.nextInt();
            w = st.nextInt();
            if (edges[v] == null)
                edges[v] = new LinkedList<>();
            edges[v].add(new int[] {e, w});
        }
        // 다익스트라
        dijkstra(start);

        for (int i = 1; i <= n; i++) {
            try {
                if (start == i)
                    bw.write(0+"\n");
                else if (distance[i] == MAX)
                    bw.write("INF\n");
                else
                    bw.write((int)distance[i]+"\n");
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
