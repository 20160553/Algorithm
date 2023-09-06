import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        /*
        * 시간제한 : 0.5초 (연산 횟수 5억?)
        * 메모리제한 : 128MB
        *
        * N : 최대 10만
        * S : 최대 1억
        *
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        int n, s, answer = 0, sum = 0, cnt = 0;
        n = Integer.valueOf(st.nextToken());
        s = Integer.valueOf(st.nextToken());

        int[] arr = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.valueOf(st.nextToken());
        }


        int start = 0;
        for (int current = 0; current < n; current++) {
            //합 카운트 증가
            cnt++;
            //start ~ current 까지의 부분합
            sum += arr[current];

            //여태 부분합이 s보다 큰 동안에 반복
            while (sum >= s) {
                //답 갱신
                if (answer == 0 || cnt < answer) {
                    answer = cnt;
                }
                //start를 오른쪽으로 옮기며 갱신
                sum -= arr[start++];
                cnt--;
            }

        }
        System.out.println(answer);
    }
}