import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
         * n <= 4_000
         *
         * 시간 제한 : 12초 -> 12억 연산 이하
         *
         * 1. 완탐: N ^ 4 = 10 ^ 3 ^ 4 = 10 ^ 12 => 시간초과
         * 2. 정렬?
         *
         * answer 범위는 long
         *
         * i) A, B, C, D 오름차순
         * ii) A + B, C + D 리스트 생성
         * iii) 이 둘을 이진탐색하며 0되는 수 있는지 확인
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[][] arr = new int[4][n];

        int[] ab = new int[n * n];
        int[] cd = new int[n * n];

        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++) {
                arr[j][i] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ab[i * n + j] = arr[0][i] + arr[1][j];
                cd[i * n + j] = arr[2][i] + arr[3][j];
            }
        }

        Arrays.sort(ab);
        Arrays.sort(cd);

        long answer = 0;

        int l = 0, r = cd.length - 1;

        while (l < ab.length) {
            int a = ab[l];
            int abCnt = 0;

            while (l < ab.length && ab[l] == a) {
                l++;
                abCnt++;
            }

            while (r >= 0) {
                int c = cd[r];

                if (a + c == 0) {
                    int cdCnt = 0;
                    while (r >= 0 && cd[r] == c) {
                        r--;
                        cdCnt++;
                    }
                    answer += (long)abCnt * cdCnt;
                    break;
                } else if (a + c < 0) {
                    break;
                }
                r--;
            }
        }

        System.out.println(answer);
    }
}