import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(in.readLine());
        StringTokenizer st;
        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(in.readLine());
            int n, k;
            n = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());

            int[] delays = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int[] destinationEdgeNums = new int[n];
            ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

            for (int i = 0; i < n; i++)
                graph.add(new ArrayList<>());

            for (int i = 0; i < k; i++) {
                int[] edgeInfo = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                graph.get(edgeInfo[0] - 1).add(edgeInfo[1] - 1);
                destinationEdgeNums[edgeInfo[1] - 1]++;
            }

            int w = Integer.parseInt(in.readLine());

            int answer = 0;
            LinkedList<Integer> q = new LinkedList<Integer>();

            for (int i = 0; i < n; i++) {
                if (destinationEdgeNums[i] == 0) {
                    destinationEdgeNums[i]--;
                    q.add(i);
                }
            }
            
            int[] spendedTime = new int[n];
            
            while (!q.isEmpty()) {
                int current = q.poll();
                if (current == w - 1) break;
                for (int next: graph.get(current)) {
                    spendedTime[next] = Math.max(delays[current] + spendedTime[current], spendedTime[next]);
                    destinationEdgeNums[next]--;
                    if (destinationEdgeNums[next] == 0) {
                        destinationEdgeNums[next]--;
                        q.add(next);
                    }
                }
            }
            System.out.println(spendedTime[w - 1] + delays[w - 1]);
        }
    }
}