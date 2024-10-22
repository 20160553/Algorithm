import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        /*
         * 1억 이하 연산
         * N <= 10,000
         * l <= 100
         * M <= 100
         *
         * N^2 => 10 ^ 10
         * => 일반 완탐은 시간초과 날 것..
         *
         * 생각할 수 있는 것 : DP, 이진 탐색?,
         *
         * 1. l / 2 가로, 세로로 분배
         * 가로 세로 => (1, 49), (2, 48), ... , (48, 2), (49, 1) => 49개
         * (1 ~ (l / 2 - 1))
         *
         * 흠... 가로 세로 먼저 정하고 물고기 순회하며 잡혔는 지 검사 시
         * 50 * 100 * 100..?
         *
         * 이거면 되긴 하겠네..
         *
         * 먼저 물고기를 정렬시켜야 함.
         *
         * 아 이거 그물 길이 고정이라 안되네 일케하면 안되네 ㅋㅋ.
         * 그물이 큰 경우를 고려 몬함 이럼.. ㅋㅋ
         *
         *
         *
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, l, m;
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken()) / 2;
        m = Integer.parseInt(st.nextToken());

        int[][] fishes = new int[m][2];
        int answer = 0;

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            fishes[i][0] = Integer.parseInt(st.nextToken());
            fishes[i][1] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < m; i++) {
            int[] fish = fishes[i];

            int sx = fish[0];
            for (int width = 1; width < l; width++) {
                int height = l - width;
                for (int j = -height; j <= 0; j++) {
                    int cnt = 0;
                    int sy = fish[1] + j;
                    int ex = sx + width;
                    int ey = sy + height;
                    for (int k = 0; k < m; k++) {
                        if (sy <= fishes[k][1] && sx <= fishes[k][0] && fishes[k][1] <= ey && fishes[k][0] <= ex) cnt++;
                    }
                    answer = Math.max(answer, cnt);
                }
            }

        }

        System.out.println(answer);
    }

}