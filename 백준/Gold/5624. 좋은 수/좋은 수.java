import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         *
         * n < 5000,
         * -10만 <= Ai <= 10만
         *
         * 5000 C 3 => 시간초과
         *
         * DP 이용
         *
         * DP배열 [200_001][3]
         * DP[0] => -10만
         *
         * 5 * 10^3 * 2 * 10^5
         * 시간초과날 것 같네? 흠... 컬렉션 써야겠네
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        final int ZERO_INDEX = 200_001;

        ArrayList<BitSet> dp = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            dp.add(new BitSet());
        }

        dp.get(0).set(ZERO_INDEX);

        int answer = 0;
        for (int i = 0; i < n; i++) {
            int c = Integer.parseInt(st.nextToken());
            if (i != 0) {
                BitSet finalSet = dp.get(3);
                int idx = c + ZERO_INDEX;
                if (idx >= 0 && idx < finalSet.length() && finalSet.get(idx)) {
                    answer++;
                }
            }

            for (int j = 0; j < 3; j++) {
                BitSet currentSet = dp.get(j);
                BitSet nextSet = dp.get(j + 1);

                for (int num = currentSet.length(); (num = currentSet.previousSetBit(num - 1)) >= 0;) {
                    int nextNum = c + num;
                    if (nextNum < 0) continue;
                    nextSet.set(nextNum);
                }
            }

        }
        System.out.println(answer);
    }
}