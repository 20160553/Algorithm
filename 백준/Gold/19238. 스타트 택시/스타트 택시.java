import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[][] map;
    static int n, m, oil;
    static final int WALL = Integer.MIN_VALUE;

    static int[] dy = new int[]{-1, 0, 1, 0};
    static int[] dx = new int[]{0, -1, 0, 1};


    static class Taxi {
        int y, x;
        int oil;
        int people;
        int spendOil;

        public Taxi(int y, int x, int oil, int people, int spendOil) {
            this.y = y;
            this.x = x;
            this.oil = oil;
            this.people = people;
            this.spendOil = spendOil;
        }

        Taxi copy() {
            return new Taxi(y, x, oil, people, spendOil);
        }
    }

    static class ArrivePoint {
        int y, x;

        public ArrivePoint(int y, int x) {
            this.y = y;
            this.x = x;
        }

        boolean isArrive(int y, int x) {
            return this.y == y && this.x == x;
        }
    }

    public static void main(String[] args) throws IOException {
        /*
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        oil = Integer.parseInt(st.nextToken());

        map = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1) {
                    map[i][j] = WALL;
                }
            }
        }

        int y, x;
        st = new StringTokenizer(br.readLine());
        y = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        ArrivePoint[] arrivePoints = new ArrivePoint[m + 1];

        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int py, px, gy, gx;
            py = Integer.parseInt(st.nextToken());
            px = Integer.parseInt(st.nextToken());
            gy = Integer.parseInt(st.nextToken());
            gx = Integer.parseInt(st.nextToken());

            map[py][px] = i;
            arrivePoints[i] = new ArrivePoint(gy, gx);
        }

        LinkedList<Taxi> q = new LinkedList<>();
        LinkedList<Taxi> q2 = new LinkedList<>();
        q.add(new Taxi(y, x, oil, -1, 0));

        while (m > 0) {
            //사람 태우기
            boolean[][] v = new boolean[n + 1][n + 1];
            PriorityQueue<Taxi> pq = new PriorityQueue<>((o1, o2) -> {
                if (o1.oil == o2.oil) {
                    if (o1.y == o2.y)
                        return o1.x - o2.x;
                    return o1.y - o2.y;
                }
                return o1.oil - o2.oil;
            });
            while (!q.isEmpty()) {
                int cnt = q.size();
                pq.clear();
                for (int i = 0; i < cnt; i++) {
                    Taxi taxi = q.poll();

                    if (map[taxi.y][taxi.x] > 0) {
                        Taxi copied = taxi.copy();
                        copied.people = map[taxi.y][taxi.x];
                        pq.offer(copied);
                    }

                    for (int j = 0; j < dy.length; j++) {
                        int ny = taxi.y + dy[j];
                        int nx = taxi.x + dx[j];
                        int nOil = taxi.oil - 1;
                        int nSpendOil = taxi.spendOil;

                        if (ny < 1 || nx < 1 || ny > n || nx > n)
                            continue;
                        if (map[ny][nx] == WALL || v[ny][nx]) continue;
                        if (nOil < 0) continue;
                        v[ny][nx] = true;
                        q.offer(new Taxi(ny, nx, nOil, 0, nSpendOil));
                    }
                }

                if (pq.size() > 0) {
                    q2.offer(pq.poll());
                    map[q2.peek().y][q2.peek().x] = 0;
                    pq.clear();
                    break;
                }
            }

            q.clear();
            v = new boolean[n + 1][n + 1];
            //사람 내리기
            while(!q2.isEmpty()) {
                int cnt = q2.size();
                for (int i = 0; i < cnt; i++) {
                    Taxi taxi = q2.poll();

                    if (arrivePoints[taxi.people].isArrive(taxi.y, taxi.x)) {
                        taxi.oil += taxi.spendOil * 2;
                        taxi.spendOil = 0;
                        taxi.people = 0;
                        m--;
                        oil = taxi.oil;
                        q.offer(taxi);
                        break;
                    }

                    for (int j = 0; j < dy.length; j++) {
                        int ny = taxi.y + dy[j];
                        int nx = taxi.x + dx[j];
                        int nOil = taxi.oil - 1;
                        int nSpendOil = taxi.spendOil + 1;

                        if (ny < 1 || nx < 1 || ny > n || nx > n)
                            continue;
                        if (map[ny][nx] == WALL || v[ny][nx]) continue;
                        if (nOil < 0) continue;
                        v[ny][nx] = true;
                        q2.offer(new Taxi(ny, nx, nOil, taxi.people, nSpendOil));
                    }
                }
            }

            if (q.isEmpty() && q2.isEmpty()) break;
        }

        if (m > 0)
            answer.append(-1);
        else {
            answer.append(oil);
        }

        System.out.println(answer);
        br.close();
    }
}