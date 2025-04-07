import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[][] map;
    static int[]  dice;
    static int[] dy = {0, 0, -1, 1};
    static int[] dx = {1, -1, 0, 0};

    static void rollDice(int direction) {
        int previousTop, previousBottom;

        previousTop = dice[0];
        previousBottom = dice[5];

        if (direction < 3) {

            if (direction == 1) {
                dice[5] = dice[2];
                dice[0] = dice[3];
                dice[2] = previousTop;
                dice[3] = previousBottom;
            } else {
                dice[5] = dice[3];
                dice[0] = dice[2];
                dice[2] = previousBottom;
                dice[3] = previousTop;
            }
        } else {
            if (direction == 3) {
                dice[5] = dice[1];
                dice[0] = dice[4];
                dice[1] = previousTop;
                dice[4] = previousBottom;
            } else {
                dice[5] = dice[4];
                dice[0] = dice[1];
                dice[1] = previousBottom;
                dice[4] = previousTop;
            }
        }
    }

    static int move(int y, int x, int direction) {
        int ny = y + dy[direction - 1];
        int nx = x + dx[direction - 1];
        int result = -1;
        if (map == null || ny < 0 || nx < 0 || ny >= map.length || nx >= map[0].length)
            return result;

        rollDice(direction);
        if (map[ny][nx] == 0) {
            map[ny][nx] = dice[5];
        } else {
            dice[5] = map[ny][nx];
            map[ny][nx] = 0;
        }
        result = dice[0];
        return result;
    }

    static void initDice() {
        dice = new int[6];
    }

    public static void main(String[] args) throws IOException {
        /*
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n, m, y, x, k;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        initDice();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            int direction = Integer.parseInt(st.nextToken());
            int result = move(y, x, direction);

            if (result != -1) {
                y = y + dy[direction - 1];
                x = x + dx[direction - 1];

                answer.append(result + "\n");
            }
        }

        System.out.println(answer);
        br.close();
    }
}