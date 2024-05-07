import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * 1억 이하
         * 64
         *
         * 1. 끝나는 시간, 시작하는 시간 순서대로 정렬 (오름차)
         * 2. 정렬된 수업시간 순회하며 끝나는 시간 새로운 리스트에 저장
         *   3 - 1. 저장된 끝나는 시간 <= 현재 시간표 시간 시작인 경우 리스트 갱신
         *   3 - 2. 그 외 새로운 리스트 생성 및 여기에 현재 끝나는 시간 저장
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int[][] arr = new int[n][2];
        PriorityQueue<Integer> rooms = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr, (o1, o2) -> {
            if (o1[0] == o2[0])
                return o1[1] - o2[1];
            return o1[0] - o2[0];
        });

        rooms.add(arr[0][1]);

        for (int i = 1; i < arr.length; i++) {
            if (rooms.peek() <= arr[i][0]) {
                rooms.poll();
            }
            rooms.add(arr[i][1]);
        }

        System.out.println(rooms.size());
    }
}