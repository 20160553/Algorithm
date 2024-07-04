import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {

        /*
         *
         * 2억 이하
         *
         * 브루트포스 : 2 ^ 100 => 2 ^ 10 ^ 10 = 10 ^ 3 ^ 10 = 10 ^ 30 => ㅋㅋㅋ 될리가 있나
         *
         *
         *
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        boolean[] v = new boolean[n + 1];
        ArrayList<ArrayList<Integer>> hates = new ArrayList<>();
        StringTokenizer st;

        hates.add(new ArrayList<>());

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            hates.add(new ArrayList<>());

            int k = Integer.parseInt(st.nextToken());
            for (int j = 0; j < k; j++)
                hates.get(i).add(Integer.parseInt(st.nextToken()));
        }

        TreeSet<Integer> blues = new TreeSet<>();
        TreeSet<Integer> whites = new TreeSet<>();

        for (int i = 1; i <= n; i++) {
            LinkedList<int[]> stack = new LinkedList<>();

            if (v[i]) continue;
            v[i] = true;
            blues.add(i);
            stack.push(new int[]{i, 0});

            while (!stack.isEmpty()) {
                int[] current = stack.pop();
                int currentNum = current[0];
                int teamColor = current[1];

                for (int hate : hates.get(currentNum)) {
                    if (v[hate]) continue;
                    v[hate] = true;
                    if (teamColor == 0) {
                        whites.add(hate);
                    } else {
                        blues.add(hate);
                    }
                    stack.add(new int[]{
                            hate, (teamColor + 1) % 2
                    });
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(blues.size() + "\n");
        blues.forEach((k) -> {
            sb.append(k + " ");
        });
        sb.append("\n");

        sb.append(whites.size() + "\n");
        whites.forEach((k) -> {
            sb.append(k + " ");
        });

        System.out.println(sb);


    }
}