import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        산성 : 1 ~ 10억
        알칼리 : 1 ~ 10억

        두개 섞어서 가장 0에 가까운 용액
         */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int value = Integer.MAX_VALUE, n;
        int[] liquid, answerLiquid;

        n = Integer.valueOf(br.readLine());

        liquid = new int[n];
        answerLiquid = new int[2];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            liquid[i] = Integer.valueOf(st.nextToken());
        }

        //정렬
        Arrays.sort(liquid);

        //왼쪽, 오른쪽 포인터 두 개 사용. 이전값, 인덱스 비교 위해 preValue, preIdx 사용
        int l = 0, r = n - 1;
        while (l < r) {
            //현재 용액 산성도 구하기
            int currentValue = liquid[l] + liquid[r];

            // 산성도가 이전보다 0에 가까워 졌을 경우
            if (value > Math.abs(currentValue)) {
                //갱신
                value = Math.abs(currentValue);
                answerLiquid[0] = liquid[l];
                answerLiquid[1] = liquid[r];
                //0인경우 최소이므로 끝
                if (value == 0)
                    break;
            }
            if (currentValue > 0) {
                r--;
            } else l++;
        }

        Arrays.sort(answerLiquid);
        System.out.println(answerLiquid[0] + " " + answerLiquid[1]);

    }
}