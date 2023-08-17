import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        //최대한 많은 곳
        //공유기 사이 거리가 최대

        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n, c;
        n = Integer.valueOf(st.nextToken());
        c = Integer.valueOf(st.nextToken());

        int[] houses = new int[n];
        for (int i = 0; i < n; i++) {
            houses[i] = Integer.valueOf(br.readLine());
        }

        Arrays.sort(houses);

        int start = 1, end = houses[n - 1] - start + 1, mid = 0;
        int louterNum = 0, preLouter = 0;
        while (start <= end) {
            mid = (end + start) / 2;

            louterNum = 1;
            preLouter = houses[0];
            for (int i = 1; i < n; i++) {
                if (houses[i] - preLouter >= mid) {
                    louterNum++;
                    preLouter = houses[i];
                }
            }
            if (louterNum >= c) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        System.out.println(end);
    }
}