import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static class Building {
        int height;
        int position;
        int visibleBuildingCnt = 0;

        public Building(int height, int position) {
            this.height = height;
            this.position = position;
        }
    }

    public static void main(String[] args) throws IOException {

        /*
         * 1.5초, 1024MB,
         *
         * N, L < 10만 = 10^5
         *
         * 1. 완탐 => N^2 즉 10^10... 시간초과
         * 2. 이분탐색 => 정렬을 못하는 문제이므로 사용 불가
         * 3. 투포인터?
         * 4. 가장 긴 증가하는 부분수열 응용?
         *   가장 긴 감소하는 부분수열
         *   i) Stack이 비었거나 peek이 i번 째 빌딩보다 클 경우, i번째 건물을 집어넣음
         *   ii) Stack의 상단이 현재 빌딩보다 높을 때까지 pop()
         *       1) i번째 건물이 pop 위치가 본 가장 가까운 높은 건물일 경우 이를 갱신
         *   iii) i번째 빌딩이 볼 수 있는 크기 += stack 개수
         *   iiii) Stack이 비었거나 peek이 i번 째 빌딩보다 클 경우, i번째 건물을 집어넣음 그후 다시 i)로
         *
         *   그후 뒤에서 부터 가장 긴 감소하는 부분 수열 다시 한 번.
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        int n;
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());

        Building[] buildings = new Building[n + 1];
        int[] nearestVisibleBuilding = new int[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            buildings[i] = new Building(Integer.parseInt(st.nextToken()), i);
            nearestVisibleBuilding[i] = 0;
        }

        Stack<Building> stack = new Stack<>();
        for (int i = 1; i <= n; i++) {
            Building building = buildings[i];
            while (!stack.isEmpty() && stack.peek().height <= building.height) {
                stack.pop();
            }
            building.visibleBuildingCnt += stack.size();
            if (!stack.isEmpty()) nearestVisibleBuilding[i] = stack.peek().position;
            stack.push(building);
        }

        stack.clear();
        for (int i = n; i >= 1; i--) {
            Building building = buildings[i];
            while (!stack.isEmpty() && stack.peek().height <= building.height) {
                stack.pop();
            }
            building.visibleBuildingCnt += stack.size();
            if (!stack.isEmpty()) {
                int previousNearest = nearestVisibleBuilding[i];
                if (nearestVisibleBuilding[i] == 0 || Math.abs(previousNearest - i) > Math.abs(stack.peek().position - i))
                    nearestVisibleBuilding[i] = stack.peek().position;
            }
            stack.push(building);
        }

        for (int i = 1; i <= n; i++) {
            int cnt = buildings[i].visibleBuildingCnt;
            answer.append(cnt);
            if (cnt != 0)
                answer.append(" " + nearestVisibleBuilding[i]);
            answer.append("\n");

        }

        System.out.println(answer);

        br.close();
    }
}