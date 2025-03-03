import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        /*
         *
         * 회의실 K, 회의 N
         *
         * K < 3, N <= 20만
         *
         * si, ei,
         *
         * 1. 시작, 끝 오름차순 정렬 시
         *   => 무조건 시작이 빠른 경우가 우선 선택되므로, 예외 발생 (끝이 더 빠르지만 시작이 느릴 경우가 생략 될 수 있음)
         * 2. 끝, 시작 오름차순 정렬.
         *   => 끝이 빠르고 시작이 빠른 순으로 예외 발생이 x
         *
         * 회의실에 넣을 때
         * 1. 회이실 내림차순.
         * 2. 넣을 수 있으면 넣음
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            pq.add(new int[]{s, e});
        }

        int answer = 0;

        Integer[] rooms = new Integer[k];
        for (int i = 0; i < k; i++) {
            rooms[i] = Integer.valueOf(0);
        }

        while (!pq.isEmpty()) {
            int[] c = pq.poll();
            int s = c[0];
            int e = c[1];

            for (int i = 0; i < k; i++) {
                if (rooms[i] < s) {
                    rooms[i] = e;
                    answer++;
                    break;
                }
            }

            Arrays.sort(rooms, Comparator.reverseOrder());
        }


        System.out.println(answer);
    }
}