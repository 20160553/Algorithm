import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
         * 1. a > b
         *  => 뒤에서부터 b - 1개 1-> 오름차순
         *  => n - b번 건물 높이는 a
         *  => 왼쪽에서 n - (a + b) 개 건물은 높이가 1
         * 2. b < a
         * 3.
         *
         * */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, a, b;

        n = Integer.parseInt(st.nextToken());
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        int remainCnt = n - a - b;

        int[] heights = new int[n];
        int maxHeight = Math.max(a, b);
        StringBuilder sb = new StringBuilder();

        if (a + b > n + 1) {
            System.out.println(-1);
            return;
        }

        if (a > b) {
            for (int i = 0; i <= remainCnt; i++) {
                heights[i] = 1;
            }
            for (int i = 0; i < b - 1; i++) {
                heights[n - 1 - i] = i + 1;
            }
            for (int i = 0; i < a; i++) {
                heights[remainCnt + i + 1] = i + 1;
            }
        } else if (a < b) {
            if (a == 1) {
                heights[0] = maxHeight;
                for (int i = 1; i <= n - b; i++) {
                    heights[i] = 1;
                }
                for (int i = 1; i < b; i++) {
                    heights[n - i] = i;
                }
            }
            else {
                for (int i = 0; i <= remainCnt; i++) {
                    heights[i] = 1;
                }
                for (int i = 1; i <= a - 1; i++) {
                    heights[remainCnt + i] = i;
                }
                for (int i = 1; i < b; i++) {
                    heights[n - i] = i;
                }
                heights[remainCnt + a] = maxHeight;
            }
        } else {
            for (int i = 0; i <= remainCnt; i++) {
                heights[i] = 1;
            }
            for (int i = 0; i < b - 1; i++) {
                heights[n - 1 - i] = i + 1;
            }
            for (int i = 0; i < a; i++) {
                heights[remainCnt + i + 1] = i + 1;
            }
        }

        for (int i = 0; i < n; i++) {
            sb.append(heights[i] + " ");
        }
        System.out.println(sb);

    }
}