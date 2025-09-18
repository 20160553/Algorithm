import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Virus {
    int y;
    int x;
    int id;
    int roomNum;

    Virus(int y, int x, int id, int roomNum) {
        this.y = y;
        this.x = x;
        this.id = id;
        this.roomNum = roomNum;
    }
}

class Room {
    List<Virus> viruses = new ArrayList<>();
    int safetyCnt = 0;
}

public class Main {

    static int answer = -1;

    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};

    static int[][] map;
    static int virusCnt = 0;

    static int n, m;

    static List<Room> rooms = new ArrayList<>();
    static HashMap<Integer, Virus> virusInfos = new HashMap<>();

    static void getViruses(int y, int x, boolean[][] v) {
        if (v[y][x] || map[y][x] == 1) {
            return;
        }

        v[y][x] = true;
        int roomIdx = rooms.size() - 1;
        if (map[y][x] == 2) {
            Virus virus = new Virus(y, x, virusCnt, roomIdx);
            rooms.get(rooms.size() - 1).viruses.add(virus);
            virusInfos.put(virusCnt++, virus);
        } else {
            rooms.get(rooms.size() - 1).safetyCnt++;
        }

        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (ny < 0 || nx < 0 || ny >= map.length || nx >= map.length) continue;
            getViruses(ny, nx, v);
        }
    }

    static void combination(int i, int num, ArrayList<Integer> activateViruses) {
        if (num == 0) {
            // 답 구하기
            getAnswer(activateViruses);
            return;
        }
        if (i >= virusCnt) {
            return;
        }
        activateViruses.add(i);
        combination(i + 1, num - 1, activateViruses);
        activateViruses.remove(activateViruses.size() - 1);
        combination(i + 1, num, activateViruses);
    }


    static void getAnswer(ArrayList<Integer> activateViruses) {
        boolean[] roomFlags = new boolean[rooms.size()];

        // 방 별 bfs에 사용할 큐 리스트
        ArrayList<LinkedList<int[]>> queueList = new ArrayList<>();
        ArrayList<boolean[][]> vList = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);

            if (room.safetyCnt == 0) roomFlags[i] = true;
            queueList.add(new LinkedList<>());
            vList.add(new boolean[n][n]);
        }

        for (int idx : activateViruses) {
            Virus virus = virusInfos.get(idx);
            roomFlags[virus.roomNum] = true;
            queueList.get(virus.roomNum).add(new int[]{virus.y, virus.x});
            vList.get(virus.roomNum)[virus.y][virus.x] = true;
        }

        // 방 마다 안전공간 남는지 확인
        for (boolean flag : roomFlags) {
            if (!flag) return;
        }

        // 방 마다 bfs
        int maxDay = -1;
        for (int i = 0; i < queueList.size(); i++) {
            int result = 0;
            int safetyCnt = rooms.get(i).safetyCnt;
            if (safetyCnt != 0)
                result = bfs(queueList.get(i), vList.get(i), safetyCnt);
            maxDay = Math.max(maxDay, result);
        }
        if (answer < 0) {
            answer = maxDay;
            return;
        }
        answer = Math.min(maxDay, answer);
    }

    static int bfs(LinkedList<int[]> q, boolean[][] v, int safetyCnt) {
        int day = 0;

        while (!q.isEmpty()) {
            if (safetyCnt <= 0)
                return day;
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                int[] position = q.poll();
                int cy = position[0];
                int cx = position[1];

                for (int j = 0; j < 4; j++) {
                    int ny = cy + dy[j];
                    int nx = cx + dx[j];

                    if (ny < 0 || nx < 0 || ny >= map.length || nx >= map.length) continue;
                    if (v[ny][nx] || map[ny][nx] == 1) continue;
                    if (map[ny][nx] == 0)
                        safetyCnt--;
                    v[ny][nx] = true;
                    q.offer(new int[]{ny, nx});
                }
            }
            day++;
        }

        return day;
    }

    static void solve() {
        // 조합 구하기
        combination(0, m, new ArrayList<>());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /*
         * N*N;
         *
         * 바이러스 수 : M
         *
         * N <= 50,
         * M <= 10,
         *
         * 1. 구역 개수, 구역별 존재 바이러스 구하기
         * 2. 바이러스 감염 가능 여부 판단하기
         *   2 - 1 : 각 구역별 최소 1개 이상의 바이러스
         *   2 - 2 : 구역 개수 <= m
         * 3.
         *
         * */

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boolean[][] v = new boolean[n][n];

        // 구역별 바이러스 구하기
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!v[i][j] && map[i][j] != 1) {
                    rooms.add(new Room());
                    getViruses(i, j, v);
                }
            }
        }

        for (Room room : rooms) {
            if (room.viruses.isEmpty()) {
                System.out.println(-1);
                return;
            }
        }

        solve();

        System.out.println(answer);
        br.close();
    }
}