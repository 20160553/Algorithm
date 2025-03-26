import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static class Building implements Comparable<Building>{
        int index;
        int buildTime;
        int startTime = 0;
        int requriedCnt = 0;
        ArrayList<Integer> postBuldings = new ArrayList<>();
        ArrayList<Integer> requiredBuildings;


        Building(int index, int buildTime, ArrayList<Integer> requiredBuildings) {
            this.index = index;
            this.buildTime = buildTime;
            this.requiredBuildings = requiredBuildings;
            this.requriedCnt = requiredBuildings.size();
        }

        public int getEndTIme() {
            return startTime + buildTime;
        }

        @Override
        public int compareTo(Building o) {
            return Integer.compare(this.requriedCnt, o.requriedCnt);
        }
    }

    public static void main(String[] args) throws IOException {

        /**/

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        int n;
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        Building[] buildings = new Building[n + 1];
        boolean[] v = new boolean[n + 1];

        LinkedList<Building> queue = new LinkedList<>();

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            ArrayList<Integer> requiredBuildings = new ArrayList<>();
            int buildingIdx = Integer.parseInt(st.nextToken());
            while (buildingIdx != -1) {
                requiredBuildings.add(buildingIdx);
                buildingIdx = Integer.parseInt(st.nextToken());
            }
            Building building = new Building(i, time, requiredBuildings);
            buildings[i] = building;
        }

        for (int i = 1; i <= n; i++) {
            Building building = buildings[i];
            for (int required: building.requiredBuildings) {
                buildings[required].postBuldings.add(i);
            }
            if (building.requriedCnt == 0)
                queue.add(building);
        }

        while(!queue.isEmpty()) {
            Building building = queue.poll();
            v[building.index] = true;

            for(int buildingIdx: building.postBuldings) {
                Building postBuilding = buildings[buildingIdx];
                postBuilding.requriedCnt--;
                postBuilding.startTime = Math.max(postBuilding.startTime, building.startTime + building.buildTime);
                if (!v[postBuilding.index] && postBuilding.requriedCnt == 0)
                    queue.add(postBuilding);
            }
        }

        for (int i = 1; i <= n; i++) {
            Building building = buildings[i];
            answer.append(building.getEndTIme() + "\n");
        }

        System.out.println(answer);

        br.close();
    }
}