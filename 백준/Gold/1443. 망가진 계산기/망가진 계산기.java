import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 시간제한 : 2초
         *
         * 2 <= d <= 8 -> 1억 미만
         * p <= 30
         *
         * 알고리즘
         *
         * 완탐 => 8 ^ 30
         * DP?
         * 체 이용?
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int d, p;


        d = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken());

        int MAX_VALUE = (int) Math.pow(10.0, d) - 1;
        int MIN_VALUE = (int) Math.pow(2, p);

        if (Math.pow(2, p) > MAX_VALUE) {
            System.out.println(-1);
            return;
        }
        if (p == 0) {
            System.out.println(1);
            return;
        }
        if (p == 1) {
            System.out.println(9);
            return;
        }

        LinkedList<Integer> q = new LinkedList<>();


        for (int i = 2; i < 10; i++) {
            q.add(i);
        }

        int answer = -1;

        int cnt = 1;
        while(!q.isEmpty() && cnt <= p) {
            int qSize = q.size();
            HashSet<Integer> candidates = new HashSet<>();
            for (int i = 0; i < qSize; i++) {
                int now = q.poll();
                if (cnt == p)
                    answer = Math.max(answer, now);
                for (int j = 2; j < 10; j++) {
                    int next = now * j;
                    if (next > MAX_VALUE)
                        continue;
                    candidates.add(next);
                }
            }
            q.addAll(candidates);
            cnt++;
        }

        System.out.println(answer);
    }
}