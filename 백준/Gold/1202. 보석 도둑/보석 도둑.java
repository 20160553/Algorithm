import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static class Jewel implements Comparable<Jewel> {
        int weight, value;

        public Jewel(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }

        public int compareTo(Jewel o) {
            if (o.weight == this.weight)
                return this.value - o.value;
            return this.weight - o.weight;
        }

        @Override
        public String toString() {
            return "Jewel{" +
                    "weight=" + weight +
                    ", value=" + value +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {

        /*
         * 시간 : 1초 -> 1억 연산 이하
         *
         * n, k <= 30만
         *
         * 가방 최대 무게 : 1억
         *
         * n C k => 30만 C 30만
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n, k;
        long answer = 0;
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[] bags = new int[k];
        Jewel[] jewels = new Jewel[n];
        PriorityQueue<Integer> candidateJewel = new PriorityQueue<>((v1, v2) -> v2 - v1);

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            jewels[i] = new Jewel(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        Arrays.sort(jewels);

        int idx = 0;

        for (int i = 0; i < k; i++) {
            bags[i] = Integer.parseInt(in.readLine());
        }

        Arrays.sort(bags);

        for (int i = 0; i < k; i++) {
            while (idx < n && jewels[idx].weight <= bags[i]) {
                candidateJewel.add(jewels[idx].value);
                idx++;
            }
            if (!candidateJewel.isEmpty())
                answer += candidateJewel.poll();
        }
        System.out.println(answer);
    }
}