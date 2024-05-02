import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    private static int result = 0;
    private static int[] parents = null;

    public static boolean union(int v1, int v2) {
        //find 실행 parent 다른 경우 union 실행
        int p1 = find(v1);
        int p2 = find(v2);
        //먼저 다른지 비교 참 => p1 == 0 조건 무시.
        //같다면 p1 == 0 실행 0이면 둘다 부모가 없는 상태
        if (p1 != p2) {
            //union 로직 v2의 부모는 v1.
            parents[v2] = p1;
            parents[p2] = p1;
            return true;
        }
        //부모가 둘이 같을 경우
        return false;
    }

    // 부모 찾기
    public static int find(int v) {
        // 부모가 비었을 경우 현재 위치 리턴
        if (parents[v] == 0)
            return v;
        // 부모 찾아서 리턴
        return find(parents[v]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        ArrayList<int[]> edges = new ArrayList();
        parents = new int[v + 1];
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            edges.add(new int[] {
                    // v1
                    Integer.parseInt(st.nextToken()),
                    // v2
                    Integer.parseInt(st.nextToken()),
                    // weight
                    Integer.parseInt(st.nextToken()) });
        }

        // 다익스트라 쓰기 위해 가중치 오름차순으로 정렬
        edges.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });

        for (int[] edge : edges) {
//            System.out.println(Arrays.toString(edge));
            // union 성공 시 가중치 더해줌
            if (union(edge[0], edge[1]))
                result += edge[2];
        }
        // 결과 출력
        System.out.println(result);
    }

}
