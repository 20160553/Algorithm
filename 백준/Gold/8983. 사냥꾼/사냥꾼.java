import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Coordinate implements Comparable<Coordinate> {
        int y, x;

        public Coordinate(int x, int y) {
            this.y = y;
            this.x = x;
        }

        @Override
        public int compareTo(Coordinate o) {
            return this.x - o.x;
        }

        public boolean canHunt(int x, int l) {
            return Math.abs(this.x - x) + this.y <= l;
        }
    }

    public static void main(String[] args) throws IOException {

        /*
         * m <= 10 ^ 5
         * n <= 10 ^ 5
         * l <= 10 ^ 9
         *
         * 시간제한 : 1초
         *
         * 완탐은 안됨.
         *
         * 투포인터?
         *
         * 1. 사대,
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int m, n, l;

        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());

        int[] shootingAreas = new int[m];
        ArrayList<Coordinate> animals = new ArrayList<>();
        boolean[] v;

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            shootingAreas[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            Coordinate animal = new Coordinate(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            if (animal.y > l) continue;
            animals.add(animal);
        }

        Arrays.sort(shootingAreas);

        int answer = 0;
        for (Coordinate animal : animals) {
            int d = l - animal.y;
            int start = animal.x - d, end = animal.x + d;
            int left, right;
            left = Arrays.binarySearch(shootingAreas, start);
            right = Arrays.binarySearch(shootingAreas, end + 1);

            if (left < 0) left = -left - 1;
            if (right < 0) right = -right - 1;
            if (left < right) answer++;
        }

        System.out.println(answer);
        br.close();
    }
}