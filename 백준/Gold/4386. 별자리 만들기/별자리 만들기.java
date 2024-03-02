import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Main {

    static double calculateCost(double[] a, double[] b) {

        double dx, dy;

        dx = a[0] - b[0];
        dy = a[1] - b[1];

        return Math.sqrt(dx * dx + dy * dy);
    }

    static class Line implements Comparable<Line> {
        int start, destination;
        double cost;

        public Line(int start, int destination, double cost) {
            this.start = start;
            this.destination = destination;
            this.cost = cost;
        }

        @Override
        public int compareTo(Line o) {
            if (this.cost - o.cost == 0)
                return 0;
            else if (this.cost - o.cost > 0)
                return 1;
            else return -1;
        }

        @Override
        public String toString() {
            return "Line{" +
                    "start=" + start +
                    ", destination=" + destination +
                    ", cost=" + cost +
                    '}';
        }
    }

    public static int find(int a, int[] parents) {
        if (a == parents[a])
            return a;
        return parents[a] = find(parents[a], parents);
    }

    public static boolean union(int a, int b, int[] parents) {

        int pa = find(a, parents), pb = find(b, parents);

        if (pa == pb)
            return false;
        parents[b] = pa;
        parents[pb] = pa;
        return true;
    }


    public static void main(String[] args) throws IOException {
        /*
         * 1초 -> 1억 이하
         * 128 MB ->
         * n < 100
         *
         * MST
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());

        double[][] stars = new double[n][2];
        int[] parents = new int[n];

        LinkedList<Line> lines = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            double[] position = Arrays.stream(in.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
            stars[i] = position;
            parents[i] = i;
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                lines.add(new Line(i, j, calculateCost(stars[i], stars[j])));
            }
        }

        Collections.sort(lines);

        double answer = 0;

        for (Line line: lines) {
            if (union(line.start, line.destination, parents)) {
                answer += line.cost;
            }
        }
        System.out.println(Math.round(answer * 100) / 100.0);
    }
}