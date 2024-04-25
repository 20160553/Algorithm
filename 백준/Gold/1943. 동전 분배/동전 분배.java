import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 2초 미만
         * 128MB -> 2^20 * 128 / 4 -> 약 int 3000만개까지 사용가능
         *
         * 흠.. 빡통으로 풀면 시간초과 나려나?
         *
         * 최대 금액 -> 10만. 구해야 하는 금액은 주신 금액의 반 -> 5만
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        firstLoop:
        for (int t = 0; t < 3; t++) {
            int n = Integer.parseInt(in.readLine());
            int totalMoney = 0;
            Map<Integer, Integer> coins = new HashMap<>();

            //초기화
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(in.readLine());
                int value = Integer.parseInt(st.nextToken());
                int num = Integer.parseInt(st.nextToken());

                totalMoney += value * num;
                coins.put(value, num);
            }


            int HALF_MONEY = totalMoney / 2;
            boolean[] dp = new boolean[HALF_MONEY + 1];
            dp[0] = true;
            if (totalMoney % 2 == 1) {
                sb.append(0 + "\n");
                continue;
            }
            if (coins.containsKey(HALF_MONEY)) {
                sb.append(1 + "\n");
                continue;
            }
            for (Map.Entry<Integer, Integer> coin : coins.entrySet()) {
                if (coin.getKey() > HALF_MONEY) {
                    dp[HALF_MONEY] = false;
                    break;
                }


                if (dp[HALF_MONEY]) {
                    sb.append(1 + "\n");
                    continue firstLoop;
                }

                for (int k = 0; k < coin.getKey(); k++){
                    int start = k;
                    loop1: while (start <= HALF_MONEY - coin.getKey()) {
                        for (int i = 1; i <= coin.getValue(); i++) {
                            int current = start + coin.getKey() * i;
                            if (current > HALF_MONEY) break;
                            if (dp[current]) {
                                start = current;
                                continue loop1;
                            }
                            dp[current] |= dp[start];
                        }
                        start += coin.getKey() * (coin.getValue() + 1);
                    }
                }
            }

            if (dp[HALF_MONEY]) {
                sb.append(1 + "\n");
            } else {
                sb.append(0 + "\n");
            }

        }
        System.out.println(sb);
    }
}