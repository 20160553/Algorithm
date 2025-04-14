import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[] scores;
    static int[] dices;
    static boolean[] v;
    static ArrayList<ArrayList<Integer>> edgesList;
    static int maxScore = 0;
    static int[] horses;

    static void init() {
        scores = new int[]{
                0, 2, 4, 6, 8,
                10, 12, 14, 16, 18,
                20, 22, 24, 26, 28,
                30, 32, 34, 36, 38,
                40, 13, 16, 19, 22,
                24, 28, 27, 26, 25,
                30, 35, 0
        };
        v = new boolean[scores.length];
        edgesList = new ArrayList<>();

        for (int i = 0; i < 33; i++) {
            ArrayList<Integer> edges = new ArrayList<>();
            edgesList.add(edges);

            if (i == 23 || i == 25 || i == 28) {
                edges.add(29);
            }
            if ((i != 20 && i < 23) || i == 24 || (26 <= i && i <= 27) || (29 <= i && i < 31)) {
                edges.add(i + 1);
            }
            if (i == 31) {
                edges.add(20);
            }
            if (i == 20) {
                edges.add(32);
            }
        }
        edgesList.get(5).add(21);
        edgesList.get(10).add(24);
        edgesList.get(15).add(26);

        horses = new int[4];
        dices = new int[10];
    }

    static void dfs(int depth, int score) {
        boolean endFlag = true;
        for (int i = 0; i < horses.length; i++) {
            if (horses[i] != 32) {
                endFlag = false;
                break;
            }
        }
        if (endFlag || depth == 10) {
            maxScore = Math.max(maxScore, score);
            return;
        }

        for (int i = 0; i < horses.length; i++) {
            int prev = horses[i];
            int next = move(dices[depth], dices[depth], prev);
            if (!v[next]) {
                v[prev] = false;
                if (next != 32)
                    v[next] = true;
                horses[i] = next;
                dfs(depth + 1, score + scores[next]);
                v[next] = false;
                horses[i] = prev;
                v[prev] = true;
            }
        }

    }

    static int move(int initailCnt, int cnt, int position) {
        if (cnt == 0 || position == 32)
            return position;

        ArrayList<Integer> result = new ArrayList<>();

        ArrayList<Integer> edges = edgesList.get(position);
        int dest = 0;

        if (edges.size() > 1 && initailCnt == cnt) {
            dest = 1;
        }


        return move(initailCnt, cnt - 1, edges.get(dest));
    }

    public static void main(String[] args) throws IOException {
        /*
         *
         * k 중 c개 선택하는 문제
         *
         * 조건 : 같은 위치에서도 여러번 커팅이 가능함
         *
         * 1. 이분탐색으로 가장 짧게 자르는 경우 S를 찾아야 함
         * 2. 그 과정에서 모든 조각이 M보다 작은지를 확인해야함
         * 3. 조건에 맞다면 조각을 더 작게, 아니라면 더 크게 시도해봐야 함
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        init();
        for (int i = 0; i < dices.length; i++)
            dices[i] = Integer.parseInt(st.nextToken());

        dfs(0, 0);

        answer.append(maxScore);
        System.out.println(answer);
        br.close();
    }
}