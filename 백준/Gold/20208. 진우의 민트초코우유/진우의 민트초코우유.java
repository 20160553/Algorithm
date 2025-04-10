import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static int n, m, h, sy = 0, sx = 0;
    static int[][] arr;
    static boolean[] v;

    private static int dfs(int y, int x, int score, int health, ArrayList<int[]> mintChocoMilks) {

        int maxScore = 0;
        if (health -(Math.abs(y - sy) + Math.abs( x - sx)) >= 0) maxScore = score;
        for (int i = 0; i < mintChocoMilks.size(); i++) {
            if (v[i])
                continue;
            int[] milk = mintChocoMilks.get(i);
            int cost = Math.abs(y - milk[0]) + Math.abs( x - milk[1]);
            if (cost > health) continue;
            v[i] = true;
            maxScore = Math.max(dfs(milk[0], milk[1], score + 1, health + h - cost, mintChocoMilks), maxScore);
            v[i] = false;
        }
        return maxScore;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());

        arr = new int[n][n];
        ArrayList<int[]> mintChocoMilks = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                if (arr[i][j] == 1) {
                    sy = i;
                    sx = j;
                }
                if (arr[i][j] == 2) {
                    mintChocoMilks.add(new int[] {i, j});
                }
            }
        }

        v = new boolean[mintChocoMilks.size()];


        System.out.println(dfs(sy, sx, 0, m, mintChocoMilks));

        br.close();
    }
}