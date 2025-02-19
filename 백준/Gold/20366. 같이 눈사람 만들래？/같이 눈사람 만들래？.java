import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {

    private static class Snowman implements Comparable<Snowman> {
        int height;
        HashSet<Integer> snowballs = new HashSet<>();

        Snowman(int height) {
            this.height = height;
        }

        void addSnowball(int snowball) {
            this.snowballs.add(snowball);
        }

        boolean contains(HashSet<Integer> snowballs) {
            for (int snowball : snowballs) {
                if (this.snowballs.contains(snowball))
                    return true;
            }
            return false;
        }

        @Override
        public int compareTo( Snowman o) {
            return Integer.compare(this.height, o.height);
        }
    }

    public static void main(String[] args) throws Exception {

        /*
         * N <= 600, H <= 10억
         *
         * 1. 완탐
         * 선택, 선택안함을 600번 -> 2^600 => 10^3^60 => 시간초과
         *
         * 2. 눈사람 만들어놓고 여기서 선택.
         *   => 눈사람 만든 재료가 중복 선택될 여지가 있음.. => 탈락
         *       => 눈사람 만든 재료를 저장하면되는 거 아님?
         * 3.
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] snowballs = new int[n];
        ArrayList<Snowman> snowmans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            snowballs[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Snowman snowman = new Snowman(snowballs[i] + snowballs[j]);
                snowman.addSnowball(i);
                snowman.addSnowball(j);
                snowmans.add(snowman);
            }
        }

        Collections.sort(snowmans);

        int answer = Integer.MAX_VALUE;

        int l = 0;

        while (l < snowmans.size() - 1) {
            Snowman lS = snowmans.get(l);
            for (int r = l + 1; r < snowmans.size(); r++) {
                Snowman rS = snowmans.get(r);
                if (!lS.contains(rS.snowballs)) {
                    answer = Math.min(rS.height - lS.height, answer);
                    break;
                }
            }
            l++;
        }

        System.out.println(answer);

    }
}