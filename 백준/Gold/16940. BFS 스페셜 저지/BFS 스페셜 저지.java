import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 2억 이하
         * 512MB
         *
         * n <= 10만
         *
         *
         * */
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(in.readLine());

        ArrayList<HashSet<Integer>> vertexes = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            vertexes.add(new HashSet<>());
        }

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            vertexes.get(a).add(b);
            vertexes.get(b).add(a);
        }

        Queue<Integer> q = new LinkedList<>();

        boolean[] v = new boolean[n + 1];
        int[] visitList = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        q.add(1);
        v[1] = true;
        int pointer = 1;
        int answer = 0;


        if (visitList[0] == 1)
            bfs:
                    while (!q.isEmpty()) {
                        int qSize = q.size();
                        for (int i = 0; i < qSize; i++) {
                            int current = q.poll();

                            for (int j : vertexes.get(current)) {
                                if (pointer >= visitList.length) {
                                    answer = 1;
                                    break bfs;
                                }
                                if (vertexes.get(current).contains(visitList[pointer])) {
                                    q.add(visitList[pointer++]);
                                }
                            }
                        }
                    }
        System.out.println(answer);
    }
}