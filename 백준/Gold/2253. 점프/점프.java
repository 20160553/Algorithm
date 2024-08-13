import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * DP 문제
         *
         * 2차원 DP
         * DP[돌 번호][해당 자리 도착할 때 점프 거리] = 점프 횟수 최솟값
         *
         * DP[i][j] = min(dp[i - k][j - 1] + 1, dp[i - k][j] + 1, dp[i - k][j + 1])
         *
         * 메모리 초과 걸림
         * 128MB => 128 * 10^6 => 8 * 16 * 10 ^ 6 = 8 * 4 * 4 * 10 ^ 6 => 인트형 4백만개가 한계
         * 인트형 10 ^ 4 ^ 2는 당연히 메모리 초과....
         *
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        final int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
        final int MAX = 10_001;

        ArrayList<HashMap<Integer, Integer>> dp = new ArrayList<>();
        HashSet<Integer> disableStones = new HashSet<>();

//        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            disableStones.add(Integer.parseInt(br.readLine()));
//            disableStones.add(Integer.parseInt(st.nextToken()));
        }

        dp.add(new HashMap<>());
        for (int i = 1; i <= N; i++) {
            dp.add(new HashMap<>());
        }

        dp.get(2).put(1, 1);
        int answer = MAX;

        for (int i = 2; i <= N; i++) {
            if (disableStones.contains(i)) continue;
            for (Map.Entry<Integer, Integer> map: dp.get(i).entrySet()) {
                int j = map.getKey();
                int v = map.getValue();
                //1 뺀 거
                if (i + j - 1 <= N && j > 1)
                    dp.get(i + j - 1).put(j - 1, Math.min(dp.get(i + j - 1).getOrDefault(j - 1, MAX), v + 1));
                //그대로
                if (i + j <= N)
                    dp.get(i + j).put(j, Math.min(dp.get(i + j).getOrDefault(j, MAX), v + 1));
                //1 더하기
                if (i + j + 1 <= N)
                    dp.get(i + j + 1).put(j + 1, Math.min(dp.get(i + j + 1).getOrDefault(j + 1, MAX), v + 1));
            }
        }
        for (Map.Entry<Integer, Integer> map: dp.get(N).entrySet()) {
            answer = Math.min(answer, map.getValue());
        }
        if (answer == MAX) answer = -1;
        System.out.println(answer);

    }
}