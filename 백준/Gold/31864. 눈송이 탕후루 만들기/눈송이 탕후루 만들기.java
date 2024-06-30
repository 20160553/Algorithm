import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {

    static class Fruit {
        int x = 0;
        int y = 0;
        int greatestCommonDivisor;
        String key;

        Fruit(int x, int y) {
            this.x = x;
            this.y = y;
            greatestCommonDivisor = euclidianAlgorithm(x, y);
            if (greatestCommonDivisor == 0) {
                key = Integer.MAX_VALUE + " " + 0;
            } else if (greatestCommonDivisor == Integer.MAX_VALUE) {
                key = 0 + " " + Integer.MAX_VALUE;
            } else {
                key = x / greatestCommonDivisor + " " + y / greatestCommonDivisor;
            }
        }

        @Override
        public String toString() {
            return "Fruit{" +
                    "x=" + x +
                    ", y=" + y +
                    ", key=" + key +
                    '}';
        }
    }

    public static int euclidianAlgorithm(int x, int y) {
        if (y == 0) return 0;
        if (x == 0) return Integer.MAX_VALUE;
//        if (x < y) {
//            int temp = x;
//            x = y;
//            y = temp;
//        }
        while (y != 0) {
            int temp = x;
            x = y;
            y = temp % y;
        }
        return x;
    }

    public static void main(String[] args) throws IOException {

        /*
         * 2억 이하 연산
         *
         * n, m <= 30만
         *
         * y = 기울기 * x
         * 기울기 = x/y
         *
         * 기울기를 저장해놓는 Map 필요, 놓을 수 있는 최대 y
         * 해당 선분 지나는 점 개수 새는 Map 필요
         *
         * 1. n 입력 받을 시 기울기 계산해서 Map에 저장
         * 2. m 순회하면서 기울기가 이미 Map에 있는지 저장
         *
         * 그래프 우측, 좌측 나눠야하네...
         *
         * 22퍼 오류 : 뭘까
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        HashMap<String, int[]> slopes = new HashMap<>(), fruitsNum = new HashMap<>();

        int n, m, answer = 0;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        ArrayList<Fruit> fruits = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int fx = Integer.parseInt(st.nextToken());
            int fy = Integer.parseInt(st.nextToken());

            fruits.add(new Fruit(fx, fy));
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int ex = Integer.parseInt(st.nextToken());
            int ey = Integer.parseInt(st.nextToken());
            int greatestCommonDivisor = euclidianAlgorithm(ex, ey);
            String key;
            if (greatestCommonDivisor == 0) {
                key = Integer.MAX_VALUE + " " + 0;
            } else if (greatestCommonDivisor == Integer.MAX_VALUE) {
                key = 0 + " " + Integer.MAX_VALUE;
            } else {
                key = ex / greatestCommonDivisor + " " + ey / greatestCommonDivisor;
            }
            int[] availPoint = slopes.getOrDefault(key, new int[]{0, 0});
            if (key != "0 " + Integer.MAX_VALUE) {
                availPoint[0] = Math.min(availPoint[0], ex);
                availPoint[1] = Math.max(availPoint[1], ex);
            } else {
                availPoint[0] = Math.min(availPoint[0], ey);
                availPoint[1] = Math.max(availPoint[1], ey);
            }
            slopes.put(key, availPoint);
        }

        for (Fruit i : fruits) {
            if (!slopes.containsKey(i.key)) continue;
            int[] availPoint = slopes.get(i.key);
            int[] nums = fruitsNum.getOrDefault(i.key, new int[]{0, 0});
            if (i.key != "0 " + Integer.MAX_VALUE){
                if (i.x < 0) {
                    if (availPoint[0] <= i.x)
                        nums[0]++;
                } else {
                    if (availPoint[1] >= i.x)
                        nums[1]++;
                }
            } else {
                if (i.y < 0) {
                    if (availPoint[0] <= i.y)
                        nums[0]++;
                } else {
                    if (availPoint[1] >= i.y)
                        nums[1]++;
                }
            }
            fruitsNum.put(i.key, nums);
        }

        for (int[] i : fruitsNum.values()) {
            answer = Math.max(answer, i[0]);
            answer = Math.max(answer, i[1]);
        }

        System.out.println(answer);
    }
}