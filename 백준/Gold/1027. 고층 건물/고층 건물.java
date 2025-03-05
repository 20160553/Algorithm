import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        * 시간제한 : 2초
        *
        * N <= 50
        * H <= 10억
        *
        * 완탐 : N ^ 2?
        *
        * 흠... 완탐 되나?
        *
        * */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] buildings = new int[n];
        ArrayList<HashSet<Integer>> visibleSets = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            buildings[i] = Integer.parseInt(st.nextToken());
            visibleSets.add(new HashSet<>());
        }

        final int MAX_VALUE = 1_000_000_000;
        final int MIN_VALUE = 1;

        final double MIN_INCLINATION = (MIN_VALUE - MAX_VALUE) - 1.0;

        for (int i = 0; i < n; i++) {
            HashSet<Integer> visibleSet = visibleSets.get(i);
            double maxInclination = MIN_INCLINATION;

            int h = buildings[i];

            for (int j = i + 1; j < n; j++) {
                int ch = buildings[j];
                int dx = j - i;
                double dh = ch - h;

                double currentInclination = dh / dx;
                HashSet<Integer> set = visibleSets.get(j);

                if (maxInclination < currentInclination) {
                    maxInclination = currentInclination;
                    visibleSet.add(j);
                    set.add(i);
                }
            }
        }

        int answer = 0;

        for (HashSet<Integer> set: visibleSets) {
            answer = Math.max(answer, set.size());
        }
        System.out.println(answer);

        br.close();
    }
}