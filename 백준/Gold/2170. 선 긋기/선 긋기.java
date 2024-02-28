import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        * 시간제한 : 1초
        * 메모리 : 192MB => 192 * 2^20 BYTE
        * n < 100만
        * -10억 <= x, y <= 10억
        *
        * 1. 끝나는 순서가 빠른 순서대로 정렬
        * 2. 가장 빠른 거 꺼냄.
        * 3. 다른 선분의 시작점이 현재 꺼낸 놈의 끝나는 위치보다 앞일 경우 갱신
        * 4. 벗어날 경우 다시 시작
        * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(in.readLine());
        int answer = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] == o2[0])
                return o1[1] - o2[1];
            return o1[0] - o2[0];
        });

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            int x, y;
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            pq.add(new int[] {x, y});
        }

        int[] line = pq.poll();
        while (!pq.isEmpty()) {
            int[] currentLine = pq.poll();

            if (currentLine[0] <= line[1]) {
                line[1] = Math.max(line[1], currentLine[1]);
            } else {
                answer += line[1] - line[0];
                line = currentLine;
            }
        }
        answer += line[1] - line[0];
        System.out.println(answer);

    }
}