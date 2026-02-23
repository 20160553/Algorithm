import java.util.*;
import java.io.*;

class Status implements Comparable<Status> {
    int left = 0;
    int right = 0;
    int score = 0;

    public Status(int left, int right, int score) {
        if (left > right) {
            int temp = left;
            left = right;
            right = temp;
        }
        this.left = left;
        this.right = right;
        this.score = score;
    }

    private int nextScore(int prev, int next) {
        if (prev == next) return 1;
        if (prev == 0) return 2;
        if (Math.abs(next - prev) % 2 == 0) return 4;
        return 3;
    }

    public List<Status> next(int next) {
        ArrayList<Status> result = new ArrayList<>();

        result.add(new Status(next, right, this.score + nextScore(this.left, next)));
        result.add(new Status(left, next, this.score + nextScore(this.right, next)));

        return result;
    }

    public int getKey() {
        if (left == 0) {
            if (right == 0)
                return 0;
            if (right == 1)
                return 1;
            if (right == 2)
                return 2;
            if (right == 3)
                return 3;
            if (right == 4)
                return 4;
        }
        if (left == 1) {
            if (right == 2)
                return 5;
            if (right == 3)
                return 6;
            if (right == 4)
                return 7;
        }
        if (left == 2) {
            if (right == 3)
                return 8;
            if (right == 4)
                return 9;
        }
        if (left == 3) {
            if (right == 4)
                return 10;
        }
        return -1;
    }

    @Override
    public int compareTo(Status o) {
        return this.score - o.score;
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        /*
         * 조건
         * DDR
         *
         * 2초 => 연산 횟수 : 2억 미만
         *
         * 같은 위치: 1
         * 중앙 -> 다른 위치: 2
         * 현재 위치 : 반대 위치 : 4
         * 그 외 : 3
         *
         * 상하좌우 : [ 1, 3, 2, 4 ]
         * 상좌하우: [ 1, 2, 3, 4 ]
         *
         * 최소 힘은?
         *
         * 시간 복잡도
         *
         * 수열 길이 : N < 10 ^ 5
         * 시간복잡도 : 2 ^ 10 ^ 5 => 1024 ^ 10 ^ 4 => 10^7
         * 메모리 : 128MB
         *
         * 메모리 초과날 듯.
         *
         * 상태
         *
         * 한 쪽 중앙
         * 1 중중
         * 2. 중 상
         * 3. 중 오
         * 4. 중 위
         * 5. 중 오
         *
         * 한 쪽 왼
         * 6. 왼 오
         * 7. 왼 위
         * 8. 왼 아
         *
         * 한 쪽 오
         * 9. 오 위
         * 10. 오 아
         *
         * 한 쪽 위
         * 11. 위 아
         *
         * (11 * 2) * N
         *
         * */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        ArrayList<Integer> arr = new ArrayList<>();

        while (st.hasMoreTokens()) {
            int num = Integer.parseInt(st.nextToken());
            if (num == 0) break;
            arr.add(num);
        }

        ArrayList<HashMap<Integer, Status>> result = new ArrayList<>();
        HashMap<Integer, Status> first = new HashMap<>();
        first.put(0, new Status(0, 0, 0));
        result.add(first);

        for (int i = 1; i <= arr.size(); i++) {
            HashMap<Integer, Status> currents = new HashMap<>();
            HashMap<Integer, Status> prev = result.get(i - 1);
            int num = arr.get(i - 1);

            for (Map.Entry<Integer, Status> entry: prev.entrySet()) {
                List<Status> nexts = entry.getValue().next(num);
                for (Status status: nexts) {
                    if (status.getKey() < 0) continue;
                    if (!currents.containsKey(status.getKey())) {
                        currents.put(status.getKey(), status);
                    } else {
                        if (status.score < currents.get(status.getKey()).score) {
                            currents.put(status.getKey(), status);
                        }
                    }
                }
            }
            result.add(currents);
        }

        int answer = 400_004;
        for(Status status: result.get(result.size() - 1).values()) {
//            if (result.size() > 2 && status.getKey() < 5) continue;
            answer = Math.min(answer, status.score);
        }
        System.out.println(answer);

    }
}