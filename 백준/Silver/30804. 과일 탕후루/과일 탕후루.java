import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        *
        * 과일 2종류 밖에 사용 못함
        *
        * l, r 두 개의 포인터를 이용해 계산
        *
        * l은 젤 왼쪽 지점, r은 오른쪽 지점
        *
        * 1. 기본적으로 r 증가
        * 2. l, r 사이 과일 개수가 2 초과시 -> l 증가
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] fruits = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int l = 0;
        int answer = 0;
        int cnt = 0;
        HashMap<Integer, Integer> fruitMap = new HashMap<>();
        for (int r = 0; r < n; r++) {
            int c = fruits[r];
            cnt++;
            fruitMap.put(c, fruitMap.getOrDefault(c, 0) + 1);

            if (fruitMap.size() > 2) {
                cnt--;
                fruitMap.put(fruits[l], fruitMap.get(fruits[l]) - 1);
                if (fruitMap.get(fruits[l]) == 0) fruitMap.remove(fruits[l]);
                l++;
            }
            answer = Math.max(answer, cnt);
        }
        System.out.println(answer);
    }
}