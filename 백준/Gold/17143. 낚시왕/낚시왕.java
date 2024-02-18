import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[] dy = new int[]{-1, 1, 0, 0};
    static int[] dx = new int[]{0, 0, 1, -1};

    static class Shark {
        int y, x, speed, d, size;

        public Shark(int y, int x, int speed, int d, int size) {
            this.y = y;
            this.x = x;
            this.speed = speed;
            this.d = d;
            this.size = size;
        }

        public void move(int id, int r, int c, HashMap<Integer, Shark> sharks, ArrayList<ArrayList<Integer>> map, ArrayList<Integer> feededSharks) {
            int nextY = this.y + dy[d] * speed;
            int nextX = this.x + dx[d] * speed;

            while (nextY < 0 || nextY >= r) {
                if (nextY < 0) {
                    nextY *= -1;
                } else {
                    nextY = r - 1 - (nextY - (r - 1));
                }
                d = (d + 1) % 2;
            }
            while (nextX < 0 || nextX >= c) {
                if (nextX < 0) {
                    nextX *= -1;
                } else {
                    nextX = c - 1 - (nextX - (c - 1));
                }
                d = (d + 1) % 2 + 2;
            }

            y = nextY;
            x = nextX;

            int preSharkId = map.get(y).get(x);
            if (preSharkId < 0)
                map.get(y).set(x, id);
            else {
                if (sharks.get(preSharkId).size > sharks.get(id).size) {
                    int temp = preSharkId;
                    preSharkId = id;
                    id = temp;
                }
                feededSharks.add(preSharkId);
                map.get(y).set(x, id);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        /*
         * 1초 -> 1억 연산
         * r, c <= 100
         * M <= r * c
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int r, c, m, answer = 0;

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        ArrayList<ArrayList<Integer>> sharkMap = new ArrayList<>();
        HashMap<Integer, Shark> sharks = new HashMap<>();

        for (int i = 0; i < r; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < c; j++) {
                list.add(-1);
            }
            sharkMap.add(list);
        }

        for (int i = 0; i < m; i++) {
            int sharkR, sharkC, sharkSpeed, sharkDirection, sharkSize;
            st = new StringTokenizer(in.readLine());

            sharkR = Integer.parseInt(st.nextToken()) - 1;
            sharkC = Integer.parseInt(st.nextToken()) - 1;
            sharkSpeed = Integer.parseInt(st.nextToken());
            sharkDirection = Integer.parseInt(st.nextToken()) - 1;
            sharkSize = Integer.parseInt(st.nextToken());

            sharkMap.get(sharkR).set(sharkC, i);
            sharks.put(i, new Shark(sharkR, sharkC, sharkSpeed, sharkDirection, sharkSize));
        }

        for (int i = 0; i < c; i++) {

            //fishing;
            for (int j = 0; j < r; j++) {
                int fishedSharkId = sharkMap.get(j).get(i);
                if (fishedSharkId >= 0) {
                    Shark fishedShark = sharks.get(fishedSharkId);
                    answer += fishedShark.size;
                    sharks.remove(fishedSharkId);
                    break;
                }
            }

            //moving
            ArrayList<Integer> feededSharks = new ArrayList<>();
            for (int k = 0; k < r; k++) {
                for (int l = 0; l < c; l++) {
                    sharkMap.get(k).set(l, -1);
                }
            }
            for (Map.Entry<Integer, Shark> s : sharks.entrySet()) {
                s.getValue().move(s.getKey(), r, c, sharks, sharkMap, feededSharks);
            }

            //feeding
            for (int s: feededSharks) {
                sharks.remove(s);
            }
        }

        System.out.println(answer);

    }
}