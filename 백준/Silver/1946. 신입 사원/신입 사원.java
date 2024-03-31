import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        /*
        * 2초 -> 2억 이하
        * 메모리 256 ->
        *
        * 서류, 면접 중 둘 다 떨어지지 않는 사람만 뽑는다.
        *
        * 최대 인원은?
        *
        * 테케 -> 20
        *
        * 지원자 수 : 20만
        *
        * ========
        *
        * 1. 어느것 중 하나가 1위인 경우 무조건 가능
        * 2.
        *
        * 1. 완탐 : 테케 한 번당 20만 ^ 2 / 2 => 시간초과
        *
        *
        * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        int T = Integer.parseInt(in.readLine());

        for (int t = 0; t < T; t++) {
            int n = Integer.parseInt(in.readLine());
            int answer = 0;
            int[][] scores = new int[n][2];

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < 2; j++)
                    scores[i][j] = Integer.parseInt(st.nextToken());
            }

            Arrays.sort(scores, Comparator.comparingInt(o -> o[0]));

            int minRank = scores[0][1];
            answer++;

            for (int i = 1; i < n; i++) {
                if (scores[i][1] < minRank) {
                    answer++;
                    minRank = scores[i][1];
                }
            }
            sb.append(answer + "\n");
        }

        System.out.println(sb);
    }
}