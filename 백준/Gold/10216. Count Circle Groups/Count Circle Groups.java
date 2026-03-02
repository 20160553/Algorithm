import java.util.*;
import java.io.*;

public class Main {

    static int find(int a, int[] p) {
        if (p[a] == a) return a;
        return find(p[a], p);
    }

    static void union(int a, int b, int[] p) {
        int pa = find(a, p);
        int pb = find(b, p);
        if (pa > pb) {
            p[pa] = pb;
        } else {
            p[pb] = pa;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /*
        * 1. 완탐
        * 한 케이스당 최악의 경우
        * n * (n + 1) / 2 + n
        * */

        int t = Integer.parseInt(st.nextToken());
        StringBuilder sb =  new StringBuilder();
        for (int tc = 0; tc < t; tc++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());

            int[][] points = new int[n][3];
            int[] p = new int[n];

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                points[i][0] = Integer.parseInt(st.nextToken());
                points[i][1] = Integer.parseInt(st.nextToken());
                points[i][2] = Integer.parseInt(st.nextToken());
                p[i] = i;
            }

            Arrays.sort(points, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    if (o1[2] == o2[2]) {
                        if (o1[0] == o2[0]) return o1[1] - o2[1];
                        return o1[0] - o2[0];
                    }
                    return o2[2] - o1[2];
                }
            });

            for (int i = 0; i < n - 1; i++) {
                int y = points[i][0];
                int x = points[i][1];
                int r1 = points[i][2];

                for (int j = i + 1; j < n; j++) {
                    int y1 = points[j][0];
                    int x1 = points[j][1];
                    int dy = Math.abs(y1 - y);
                    int dx = Math.abs(x1 - x);
                    double dist = Math.sqrt( dy * dy + dx * dx);
                    int r2 = points[j][2];

                    if (dist <= r1 || dist <= r2 || dist <= r1 + r2) union(i, j, p);
                }
            }

            HashSet<Integer> set = new HashSet<>();

            for (int i = 0; i < n; i++) {
                set.add(find(i, p));
            }
            sb.append(set.size() + "\n");
        }

        System.out.println(sb);

    }
}