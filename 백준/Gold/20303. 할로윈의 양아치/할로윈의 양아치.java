import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static class Group {
        int stealedCandiesCnt = 0;
        int stealedFriendsCnt = 0;

        public Group(int stealedCandiesCnt, int stealedFriendsCnt) {
            this.stealedCandiesCnt = stealedCandiesCnt;
            this.stealedFriendsCnt = stealedFriendsCnt;
        }

        @Override
        public String toString() {
            return "Group{" +
                    "stealedCandiesCnt=" + stealedCandiesCnt +
                    ", stealedFriendCnt=" + stealedFriendsCnt +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        /*
        제한시간 : 1초 -> 1억 연산 이하
        메모리 제한 : 1024MB => 1기가 바이트 이하

        n, m, k 아이들 수, 간선 수, 뺏는 인원 제한 수

        1 <= n <= 3만
        0 <= m <= 100_000
        1 <= K <= 3000

        간선은 한 번 생기고 변할 일이 없음 => ArrayList

        일단은 BFS 갈기면 끝날 것 같음
        => 아니네 여러 그룹 뻇을 수도 있네?

        1. 일단 친구 그룹 나누기
        2. 인원 수 K가 넘지 않도록 그룹 조합

        방법 : 조합?
        i) 조합 시간복잡도 : n C k => 최대 연산 횟수는 3만 C 3천 => 1억연산을 가뿐이 넘는다.
        ii ) 그러면 DP인가? 냅색 비슷한 것 같기도 하다. 연산횟수 : 3 * 10 ^ 6

        * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int n, k, m;
        StringTokenizer st = new StringTokenizer(in.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[] candies = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        boolean[] v = new boolean[candies.length];
        ArrayList<ArrayList<Integer>> friendShips = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            friendShips.add(new ArrayList());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            int a, b;

            a = Integer.parseInt(st.nextToken()) - 1;
            b = Integer.parseInt(st.nextToken()) - 1;

            friendShips.get(a).add(b);
            friendShips.get(b).add(a);
        }

        int answer = 0;
        ArrayList<Group> groups = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            q.clear();
            if (!v[i]) {
                v[i] = true;
                int stealedCandiesNum = 0;
                int stealedFriendsCnt = 0;
                q.add(i);

                while (!q.isEmpty()) {
                    int  current = q.poll();
                    stealedFriendsCnt++;
                    stealedCandiesNum += candies[current];
                    for (int next: friendShips.get(current)) {
                        if (v[next]) continue;
                        v[next] = true;
                        q.offer(next);
                    }
                }
                if (stealedFriendsCnt < k) {
                    groups.add(new Group(stealedCandiesNum, stealedFriendsCnt));
                }
            }
        }


        //냅색
        int[][] dp = new int[groups.size() + 1][k];
        for (int i = 1; i < dp.length; i++) {
            int currentStealedFriendsCnt = groups.get(i - 1).stealedFriendsCnt;
            int currentStealedCandiesCnt = groups.get(i - 1).stealedCandiesCnt;
            for (int j = 0; j < dp[0].length; j++) {
                if (j < currentStealedFriendsCnt)
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - currentStealedFriendsCnt] + currentStealedCandiesCnt);
            }
        }
        System.out.println(dp[dp.length - 1][k - 1]);
    }

}