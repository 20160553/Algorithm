import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, k;
        int[] page;
        n = Integer.valueOf(st.nextToken());
        k = Integer.valueOf(st.nextToken());

        page = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            page[i] = Integer.valueOf(st.nextToken());
        }

        int start = 0, end = 2_000_000, mid = 0;
        //구역 나누기. 맨 처음 구역은 전체 1개
        int district = 0, districtSum = 0;
        while (start <= end) {
            mid = (start + end) / 2;

            //district : 맞은 개수가 mid 이상인 구역 수
            district = 0;
            districtSum = 0;
            for (int i = 0; i < n; i++) {
                districtSum += page[i];
                if (districtSum >= mid) {
                    district++;
                    districtSum = 0;
                }
            }

            if (district >= k) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        System.out.println(end);
    }

}