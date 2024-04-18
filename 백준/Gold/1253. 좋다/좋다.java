import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * N <= 2000
         * 숫자 <= 10억
         * 2000 * 1999 / 2;
         *
         * 0이 있을 때 0 3개 이상일 경우 -> 다 가능
         * 0 아닌 특정 숫자 갯수가 2이상일 경우 -> 다 가능
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        HashMap<Integer, Integer> numberCnt = new HashMap<>();
        HashMap<Integer, Integer> remainGoodNumber = new HashMap<>();
        int answer = 0;

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            numberCnt.put(num, numberCnt.getOrDefault(num, 0) + 1);
            remainGoodNumber.put(num, remainGoodNumber.getOrDefault(num, 0) + 1);
        }

        ArrayList<Integer> list = new ArrayList<>();
        list.addAll(remainGoodNumber.keySet());
        list.sort(Comparator.comparingInt(o -> o));

        if (numberCnt.containsKey(0))
            numberCnt.put(0, numberCnt.get(0) - 1);

        for (int i = 0; i < list.size(); i++) {
            int a = list.get(i);
            if (numberCnt.get(a) > 1) {
                if (remainGoodNumber.containsKey(a * 2)) {
                    answer += remainGoodNumber.remove(a * 2);
                }
                if (numberCnt.containsKey(0) && remainGoodNumber.containsKey(a)) {
                    answer += remainGoodNumber.remove(a);
                }
            }

            if (a != 0)
                for (int j = i + 1; j < list.size(); j++) {
                    int b = list.get(j);
                    if (b == 0) continue;
                    int sum = a + b;
                    if (remainGoodNumber.containsKey(sum)) {
                        answer += remainGoodNumber.remove(sum);
                    }
                }
        }

        System.out.println(answer);
    }
}