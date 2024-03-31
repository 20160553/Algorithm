import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {

    static class Tower {
        int height, position;

        public Tower(int height, int position) {
            this.height = height;
            this.position = position;
        }
    }

    public static void main(String[] args) throws IOException {

        /*
         * 1억 5천만 이하 => 10^8 * 1.5
         *
         * n <= 50만
         * 높이 <= 1억
         *
         * 완탐 -> (10^5) ^ 2
         *
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(in.readLine());
        int[] towers = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        StringBuilder sb = new StringBuilder();

        LinkedList<Tower> receivedTowers = new LinkedList<>();

        for (int i = 0; i < towers.length; i++) {
            int tower = towers[i];
            int receivedTower = 0;

            while (!receivedTowers.isEmpty()) {
                Tower current = receivedTowers.peek();
                if (current.height > tower) {
                    receivedTower = current.position;
                    break;
                } else
                    receivedTowers.poll();
            }

            receivedTowers.addFirst(new Tower(tower, i + 1));
            sb.append(receivedTower + " ");
        }

        System.out.println(sb);
    }
}