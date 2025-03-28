import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static class Meat implements Comparable<Meat> {
        int weight, value;

        public Meat(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }

        @Override
        public int compareTo(Meat o) {
            if (this.value == o.value)
                return o.weight - this.weight;
            return this.value - o.value;
        }
    }

    public static void main(String[] args) throws IOException {
        /*
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        int n, m;
        StringTokenizer st = new StringTokenizer(br.readLine());


        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());


        Meat[] meats = new Meat[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            meats[i] = new Meat(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        Arrays.sort(meats);

        Meat[] sum = new Meat[n];
        int minValue = Integer.MAX_VALUE;
        sum[0] = new Meat(meats[0].weight, meats[0].value);
        if (sum[0].weight >= m) {
            minValue = sum[0].value;
        }
        for (int i = 1; i < n; i++) {

            Meat meat = meats[i];
            sum[i] = new Meat(0, 0);
            sum[i].weight = sum[i - 1].weight + meat.weight;

            if (meats[i - 1].value == meats[i].value) {
                sum[i].value = sum[i - 1].value + meat.value;
            } else {
                sum[i].value = meat.value;
            }
            if (sum[i].weight >= m) {
                minValue = Math.min(minValue, sum[i].value);
            }
        }
        if (sum[sum.length - 1].weight < m)
            System.out.println(-1);
        else
            System.out.println(minValue);

        br.close();
    }
}