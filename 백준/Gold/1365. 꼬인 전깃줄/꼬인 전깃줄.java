import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        ArrayList<Integer> list = new ArrayList<>();

        list.add(Integer.parseInt(st.nextToken()));
        for (int i = 1; i < n; i++) {
            int current = Integer.parseInt(st.nextToken());
            if (list.get(list.size() - 1) < current) {
                list.add(current);
                continue;
            }
            int idx = Collections.binarySearch(list, current);
            if (idx < 0) {
                idx++;
                list.set(-idx, current);
            } else {
                list.set(idx, current);
            }
        }
        System.out.println(n - list.size());

    }
}