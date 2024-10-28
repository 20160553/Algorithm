import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    static int[] dy = new int[]{-1, 0, 1, 0};
    static int[] dx = new int[]{0, -1, 0, 1};

    public static void main(String[] args) throws IOException {

        /*
         * 1. 가장자리 찾는 BFS. 가장자리 찾아서 Queue에 넣음
         * 2. 상하좌우 다리 건설하기
         *
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, m;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][m];

        LinkedList<int[]> q = new LinkedList<>();
        boolean[][] v = new boolean[n][m];


        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int islandCnt = 0;

        LinkedList<int[]> bridgeQ = new LinkedList<>();

        //섬 초기화
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (map[i][j] == 1 && !v[i][j]) {
                    q.add(new int[]{i, j});
                    v[i][j] = true;
                    ++islandCnt;

                    //가장자리 찾는 BFS
                    while (!q.isEmpty()) {
                        int[] current = q.poll();
                        int cy = current[0];
                        int cx = current[1];
                        boolean cornerFlag = false;
                        map[cy][cx] = islandCnt;
                        for (int k = 0; k < 4; k++) {
                            int ny = cy + dy[k];
                            int nx = cx + dx[k];

                            if (ny < 0 || nx < 0 || ny >= n || nx >= m) continue;
                            if (v[ny][nx]) continue;

                            if (map[ny][nx] == 0) { //현재가 가장자리인 경우
                                cornerFlag = true;
                            } else {
                                v[ny][nx] = true;
                                q.add(new int[]{ny, nx});
                            }
                        }
                        if (cornerFlag) bridgeQ.add(new int[]{islandCnt, cy, cx});
                    }
                }
            }
        }

        int[][] linkedStatus = new int[islandCnt + 1][islandCnt + 1];

        for (int i = 1; i < linkedStatus.length; i++) {
            for (int j = 1; j < linkedStatus.length; j++) {
                if (i != j)
                    linkedStatus[i][j] = 101;
            }
        }

        //다리 건설하기
        while (!bridgeQ.isEmpty()) {
            int[] current = bridgeQ.poll();
            int startIsland = current[0];
            int y = current[1];
            int x = current[2];

            for (int i = 0; i < 4; i++) {
                int ny = y + dy[i];
                int nx = x + dx[i];

                if (ny < 0 || nx < 0 || ny >= n || nx >= m) continue;
                if (map[ny][nx] == 0) {
                    makeBridge(map, linkedStatus, startIsland, ny, nx, i, 1);
                }
            }
        }

        //MST
        int[] p = new int[linkedStatus.length];
        ArrayList<int[]> bridges = new ArrayList<>();

        for (int i = 1; i < linkedStatus.length; i++) {
            for (int j = i + 1; j < linkedStatus.length; j++) {
                int cost = linkedStatus[i][j];
                if (cost != 101)
                    bridges.add(new int[] {i, j, cost});
            }
        }

        bridges.sort(Comparator.comparingInt(o -> o[2]));
        int answer = 0;

        for (int i = 1; i < p.length; i++) p[i] = i;

        for (int[] bridge: bridges) {
            int p1 = find(p, bridge[0]);
            int p2 = find(p, bridge[1]);
            int cost = bridge[2];

            if (union(p, p1, p2)) {
                answer += cost;
            }
        }

        int p1 = find(p, 1);
        for (int i = 2; i < p.length; i++) {
            if (p1 != find(p, i)) {
                answer = -1;
                break;
            }
        }

        System.out.println(answer);
    }

    static void makeBridge(int[][] map, int[][] linkedStatus, int startIsland, int y, int x, int direction, int cnt) {
        if (map[y][x] != 0 && map[y][x] != startIsland) {
            cnt--;
            if (cnt == 1) return;
            int destination = map[y][x];
            linkedStatus[startIsland][destination] = Math.min(cnt, linkedStatus[startIsland][destination]);
            linkedStatus[destination][startIsland] = Math.min(cnt, linkedStatus[destination][startIsland]);
            return;
        }
        if (map[y][x] == startIsland) return;

        int ny = y + dy[direction];
        int nx = x + dx[direction];

        if (ny < 0 || nx < 0 || ny >= map.length || nx >= map[0].length) return;
        makeBridge(map, linkedStatus, startIsland, ny, nx, direction, cnt + 1);
    }

    static int find(int[] p, int id) {
        if (id == p[id]) return id;
        return p[id] = find(p, p[id]);
    }

    static boolean union(int[] p, int p1, int p2) {
        p1 = find(p, p1);
        p2 = find(p, p2);
        if (p1 == p2) return false;
        p[p1] = p2;
        return true;
    }


}