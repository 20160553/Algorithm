import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n, m, k;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // 빌딩 관계 리스트
        List<ArrayList<Integer>> relations = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            relations.add(new ArrayList<>());
        }

        // 빌딩 개수 배열
        int[] buildingCnts = new int[n + 1];

        // 필요 빌딩 수 개수 배열 (음수 가능)
        int[] requiredBuildingNums = new int[n + 1];

        // 관계 초기화
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            relations.get(a).add(b);
            requiredBuildingNums[b]++;
        }
        boolean isLier = false;
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            int buildNum = Integer.parseInt(st.nextToken());

            if (cmd == 1) {
                // 빌딩 짓는 경우
                if (requiredBuildingNums[buildNum] == 0) {
                    if (buildingCnts[buildNum]++ == 0)
                        for (int building : relations.get(buildNum)) {
                            requiredBuildingNums[building]--;
                        }
                } else {
                    isLier = true;
                    break;
                }
            } else {
                // 부시는 경우
                if (buildingCnts[buildNum] < 1) {
                    isLier = true;
                    break;
                }

                if (--buildingCnts[buildNum] < 1) {
                    for (int building : relations.get(buildNum)) {
                        requiredBuildingNums[building]++;
                    }
                }
            }
        }

        if (!isLier) {
            System.out.println("King-God-Emperor");
        } else {
            System.out.println("Lier!");
        }

        br.close();
    }
}