import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int n, c, answer = 0;
    static int[] w;
    static void combination(int idx, int e, ArrayList combinationList, long sum) {
        if (idx > e) {
            if (sum <= c) combinationList.add(sum);
            return;
        }

        combination(idx + 1, e, combinationList, sum);
        combination(idx + 1, e, combinationList, sum + w[idx]);

    }

    static int binarySearchUpperBounds(ArrayList<Long> list, long target) {
        int start = 0, end = list.size() - 1, mid;

        while (start <= end) {
            mid = (start + end) / 2;

            if (list.get(mid) <= target) {
                start = mid + 1;
            }
            else end = mid - 1;
        }
        return start;
    }

    public static void main(String[] args) throws IOException {
        /*
        * N개의 물건
        * 최대 C만큼의 무게
        * "N개"의 물건을 넣는 "경우의 수"?
        *
        * 그냥 조합 => 2^32
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.valueOf(st.nextToken());
        c = Integer.valueOf(st.nextToken());

        w = new int[n];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            w[i] = Integer.valueOf(st.nextToken());
        }

        ArrayList<Long> combinationList1 = new ArrayList(), combinationList2 = new ArrayList();

        int half = w.length / 2;
        combination(0, half - 1, combinationList1, 0); // 0 ~ half
        combination(half, w.length - 1, combinationList2, 0);

        Collections.sort(combinationList2);

        for (Long weight: combinationList1) {
            answer += binarySearchUpperBounds(combinationList2, c - weight);
        }

        System.out.println(answer);
    }
}