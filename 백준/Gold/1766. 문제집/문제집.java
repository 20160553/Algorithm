import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        StringBuilder answer = new StringBuilder();

        int n, m, cnt = 0;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[] inDegree = new int[n + 1];

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        ArrayList<LinkedList<Integer>> edges = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            edges.add(new LinkedList<>());

        for (int i = 0; i < m; i++) {
            int a, b;
            st = new StringTokenizer(in.readLine());

            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            inDegree[b]++;
            edges.get(a).add(b);
        }

        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0)
                pq.offer(i);
        }

        while (!pq.isEmpty()) {
            int node = pq.poll();
            answer.append(node + " ");
            for (int destination : edges.get(node)) {
                if (--inDegree[destination] == 0)
                    pq.offer(destination);
            }
        }
        System.out.println(answer);
    }


}