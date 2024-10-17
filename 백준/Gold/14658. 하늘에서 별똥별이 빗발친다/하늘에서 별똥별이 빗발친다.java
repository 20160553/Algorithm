import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, m, l, k;
        n = Integer.parseInt(st.nextToken()); // x
        m = Integer.parseInt(st.nextToken()); // y
        l = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        ArrayList<int[]> starfalls = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int[] position = new int[2];
            position[0] = Integer.parseInt(st.nextToken());
            position[1] = Integer.parseInt(st.nextToken());
            starfalls.add(position);
        }

        int answer = 0;

        for (int t = 0; t < k; t++) {
            int sx, sy, ex, ey;
            int[] starfall = starfalls.get(t);
            sx = starfall[0];

            for (int i = -l; i <= 0; i++) {
                sy = starfall[1];
                sy += i;
                ex = sx + l;
                ey = sy + l;
                int cnt = 0;
                for (int j = 0; j < k; j++) {
                    int cx = starfalls.get(j)[0];
                    int cy = starfalls.get(j)[1];

                    if (cx <= ex && cx >= sx && sy <= cy && cy <= ey) cnt++;
                }
                answer = Math.max(answer, cnt);
            }
        }

        System.out.println(k - answer);

    }
}