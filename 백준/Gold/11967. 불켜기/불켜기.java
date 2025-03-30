import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int turnOnCnt = 0;

    static class Room {
        int y;
        int x;
        boolean visited = false;
        boolean turnOn = false;

        ArrayList<Room> switchList = new ArrayList<>();

        Room(int y, int x) {
            this.y = y;
            this.x = x;
        }

        boolean canVisit(Room[][] rooms) {
            int[] dy = new int[]{-1, 0, 1, 0};
            int[] dx = new int[]{0, -1, 0, 1};

            for (int i = 0; i < dy.length; i++) {
                int ny = this.y + dy[i];
                int nx = this.x + dx[i];
                if (ny < 1 || nx < 1 || ny >= rooms.length || nx >= rooms.length) {
                    continue;
                }
                Room next = rooms[ny][nx];
                if (next.visited && next.turnOn)
                    return true;
            }
            return false;
        }

        ArrayList<Room> visit(Room[][] rooms) {
            /*
            * switch 킴.
            * 다음에 방문할 수 있는 방 리스트
            * */

            ArrayList<Room> result = new ArrayList<>();
            int[] dy = new int[]{-1, 0, 1, 0};
            int[] dx = new int[]{0, -1, 0, 1};

            if (!this.turnOn || this.visited) return result;
            this.visited = true;

            ArrayList<Room> turnedOnRooms = turnOnSwitches();
            for (Room turnedOnRoom: turnedOnRooms) {
                if (turnedOnRoom.visited) continue;
                if (turnedOnRoom.canVisit(rooms)) {
                    result.add(turnedOnRoom);
                }
            }
            for (int i = 0; i < dy.length; i++) {
                int ny = this.y + dy[i];
                int nx = this.x + dx[i];
                if (ny < 1 || nx < 1 || ny >= rooms.length || nx >= rooms.length) {
                    continue;
                }
                Room next = rooms[ny][nx];
                if (next.visited || !next.turnOn) continue;
                result.add(next);
            }
            return result;
        }

        ArrayList<Room> turnOnSwitches() {
            ArrayList<Room> result = new ArrayList<>();
            for (Room room: switchList) {
                if (room.turnOn || room.visited) continue;
                room.turnOn = true;
                result.add(room);
            }
            turnOnCnt += result.size();
            return result;
        }
    }

    public static void main(String[] args) throws IOException {
        /*
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        int n, m;
        StringTokenizer st = new StringTokenizer(br.readLine());


        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        Room[][] rooms = new Room[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                rooms[i][j] = new Room(i, j);
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int sx = Integer.parseInt(st.nextToken());
            int sy = Integer.parseInt(st.nextToken());
            rooms[y][x].switchList.add(rooms[sy][sx]);
        }

        LinkedList<Room> q = new LinkedList<>();
        q.add(rooms[1][1]);
        rooms[1][1].turnOn = true;
        turnOnCnt = 1;
        int cnt = 0;
        while (!q.isEmpty()) {
            Room room = q.poll();
            q.addAll(room.visit(rooms));
        }

        System.out.println(turnOnCnt);
        br.close();
    }
}