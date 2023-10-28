import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {

    static class Road {
        int start, dest, cost;

        public Road(int start, int dest, int cost) {
            this.start = start;
            this.dest = dest;
            this.cost = cost;
        }
    }

    private static int find(int a, int[] parents) {
        if (parents[a] == a) return a;
        parents[a] = find(parents[a], parents);
        return parents[a];
    }

    private static boolean union(int a, int b, int[] parents) {
        //uniond
        int pa = find(a, parents), pb = find(b, parents);
        if (pa == pb) return false;
        parents[pb] = pa;
        return true;
    }

    public static void main(String[] args) throws IOException {
        /*
        * 집 : n, 도로 m
        * 양방향
        * 2. 최소신장 트리 사용 => 경로 기록 필요
        * 3. 최소 신장 트리 비용 중 경로 돌아가면서 끊어서 2개 마을 만듦
        * */
        int n, m, costSum = 0, maxCost = -1;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[] parents = new int[n + 1];
        ArrayList<Road> roads = new ArrayList<>();


        //집 부모 초기화
        for (int i = 1; i <= n; i++) {
            parents[i] = i;
        }

        for (int i = 0; i < m; i++) {
            int start, dest, cost;
            st = new StringTokenizer(br.readLine());
            start = Integer.parseInt(st.nextToken());
            dest = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());

            roads.add(new Road(start, dest, cost));
        }

        roads.sort(Comparator.comparingInt(o -> o.cost));
        for (Road road: roads) {
            if (union(road.start, road.dest, parents)) {
                costSum += road.cost;
                maxCost = Math.max(maxCost, road.cost);
            }
        }
        System.out.println(costSum - maxCost);
    }
}