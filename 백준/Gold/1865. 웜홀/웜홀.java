import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node implements Comparable<Node> {
    int distance = 0;
    int destination = 0;

    public Node(int distance, int destination) {
        this.destination = destination;
        this.distance = distance;
    }


    @Override
    public int compareTo(Node o) {
        return this.distance - o.distance;
    }
}

public class Main {

    static int DISTANCE_INIFINITE = 25_000_001;

    public static void main(String[] args) throws IOException {
        /*
        입력
        N개 지점
        M개 도로 => 양방향
        W개 웜홀 => 단방향

       ======
       입력
       TC => 테스트케이스 수
       N M W
       S E T (도로, M개)
       S E T (웜홀, N개)

       음수가 가능한 최소 시간 => 벨만포드 사용
       */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int testCase = Integer.valueOf(br.readLine());

        //테스트 케이스 만큼
        for (int i = 0; i < testCase; i++) {
            int n, m, w;
            st = new StringTokenizer(br.readLine());

            n = Integer.valueOf(st.nextToken());
            m = Integer.valueOf(st.nextToken());
            w = Integer.valueOf(st.nextToken());

            ArrayList<int[]> edges = new ArrayList<>();

            // 터널
            for (int j = 0; j < m; j++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.valueOf(st.nextToken());
                int e = Integer.valueOf(st.nextToken());
                int t = Integer.valueOf(st.nextToken());

                edges.add(new int[] {s, e, t});
                edges.add(new int[] {e, s, t});
            }
            // 웜홀
            for (int j = 0; j < w; j++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.valueOf(st.nextToken());
                int e = Integer.valueOf(st.nextToken());
                int t = Integer.valueOf(st.nextToken());

                edges.add(new int[] {s, e, -t});
            }

            //밸만포드

            int [] distances = new int[n + 1];
            for (int j = 0; j < n + 1; j++) {
                Arrays.fill(distances, DISTANCE_INIFINITE);
            }

            distances[1] = 0;

            boolean flag = false;
            roop1: for (int j = 0; j < n; j++) {
                for (int[] edge: edges) {
                    int s = edge[0];
                    int e = edge[1];
                    int t = edge[2];
                    if (distances[e] > distances[s] + t)
                        if (j == n - 1) {
                            flag = true;
                            break roop1;
                        } else {
                            distances[edge[1]] = distances[edge[0]] + edge[2];
                        }
                }
            }

            if (flag) sb.append("YES\n");
            else sb.append("NO\n");
        }
        System.out.println(sb);
    }
}