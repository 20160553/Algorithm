import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int check(int c, int target, ArrayList<Integer> cutPoints) {

        int from = cutPoints.get(cutPoints.size() - 1);

        for (int i = cutPoints.size() - 2; i >= 0 && c > 0; i--) {
            if (from - cutPoints.get(i) > target) {
                if (cutPoints.get(i + 1) - cutPoints.get(i)> target)
                    return -1;
                from = cutPoints.get(i + 1);
                c--;
            }
        }

        if (c > 0) {
            from = cutPoints.get(0);
        }

        if (from > target) return -1;
        return from;
    }

    public static void main(String[] args) throws IOException {
        /*
         *
         * k 중 c개 선택하는 문제
         *
         * 조건 : 같은 위치에서도 여러번 커팅이 가능함
         *
         * 1. 이분탐색으로 가장 짧게 자르는 경우 S를 찾아야 함
         * 2. 그 과정에서 모든 조각이 M보다 작은지를 확인해야함
         * 3. 조건에 맞다면 조각을 더 작게, 아니라면 더 크게 시도해봐야 함
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        //l : 통나무 길이, k: 자를 수 있는 위치, c: 자르는 횟수
        int l, k, c;

        l = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        HashSet<Integer> cutPointsSet = new HashSet<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            cutPointsSet.add(Integer.parseInt(st.nextToken()));
        }
        cutPointsSet.add(l);

        ArrayList<Integer> cutPoints = new ArrayList<>();
        cutPoints.addAll(cutPointsSet);
        Collections.sort(cutPoints);

        int left = l / (c + 1), right = l;
        while (left < right) {
            int middle = (left + right) / 2;
            int result = check(c, middle, cutPoints);
            if (result >= 1) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }   

        answer.append(left + " " + check(c, left, cutPoints));
        System.out.println(answer);
        br.close();
    }
}