import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n, m, cnt = 0;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        Queue<Integer> q = new LinkedList<>();
        StringBuilder answer = new StringBuilder();

        int[] inDegree = new int[n + 1];
        ArrayList<ArrayList<Integer>> nodes = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            nodes.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            int k = Integer.parseInt(st.nextToken());
            int pre = Integer.parseInt(st.nextToken());
            for (int j = 1; j < k; j++) {
                int current = Integer.parseInt(st.nextToken());
                nodes.get(pre).add(current);
                inDegree[current]++;
                pre = current;
            }
        }

        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0)
                q.add(i);
        }

        while (!q.isEmpty()) {
            int currentNode = q.poll();
            cnt++;

            for (int next: nodes.get(currentNode)) {
                if (--inDegree[next] == 0) {
                    q.offer(next);
                }
            }
            answer.append(currentNode).append("\n");
        }
        if (cnt < n)
            answer = new StringBuilder("0");
        System.out.println(answer);
    }
}