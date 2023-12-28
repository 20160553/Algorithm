import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n, k;
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[][] buckets = new int[n][2];

        for (int i = 0; i < n; i++) {
            buckets[i] = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        Arrays.sort(buckets, Comparator.comparingInt(o -> o[1]));

//        for(int[] bucket: buckets) {
//            System.out.print(bucket[1] + " ");
//        }
//        System.out.println();

        int left = 0, right = 0, answer = 0;
        int sum = 0;

        while(left < n) {
            while (right < n && buckets[right][1] - buckets[left][1] <= 2 * k) {
                sum += buckets[right++][0];
            }
            answer = Math.max(sum, answer);
            sum -= buckets[left++][0];
        }

        System.out.println(answer);

    }
}