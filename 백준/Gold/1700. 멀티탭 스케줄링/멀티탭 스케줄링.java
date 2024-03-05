import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        /*
         * n, k <= 100
         * 2초 => 2억 연산 이하
         *
         * 완탐 => 100!
         *
         * dp?
         *
         * 맨 처음 Integer.MAX_VALUE로 초기화
         * 처음 멀티탭에서 뺄 때까지 돌리고
         * dp[i][j] = Math.min(dp[i - 1[j], dp[i - 1][m]);
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n, k;
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[] electronicsArr = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        HashSet<Integer> set = new HashSet<>();
        int answer = 0;
        for (int i = 0; i < k; i++) {
            if (set.contains(electronicsArr[i]))
                continue;
            if (set.size() >= n) {
                answer++;
                LinkedList<Integer> removedCandidates = new LinkedList<>(set);
                for (int j = i + 1; j < k; j++) {
                    if (removedCandidates.size() == 1)
                        break;
                    if(removedCandidates.contains(Integer.valueOf(electronicsArr[j]))) {
                        removedCandidates.remove(Integer.valueOf(electronicsArr[j]));
                    }
                }
                set.remove(removedCandidates.peek());
            }
            set.add(electronicsArr[i]);
        }

        System.out.println(answer);
    }
}