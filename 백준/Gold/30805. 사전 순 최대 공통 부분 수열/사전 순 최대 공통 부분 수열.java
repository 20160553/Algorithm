import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        LinkedList<LinkedList<Integer>> a = new LinkedList<>();
        LinkedList<LinkedList<Integer>> b = new LinkedList<>();
        LinkedList<Integer> commonNumbers = new LinkedList<>();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int n, m, a_pointer = 0, b_pointer = 0;
        n = Integer.parseInt(in.readLine());
        int[] arr = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        for (int i = 1; i <= 100; i++) {
            a.add(new LinkedList<>());
            b.add(new LinkedList<>());
        }

        for (int i = 0; i < arr.length; i++) {
            a.get(arr[i] - 1).push(i + 1);
        }

        m = Integer.parseInt(in.readLine());
        arr = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        for (int i = 0; i < arr.length; i++) {
            b.get(arr[i] - 1).push(i + 1);
        }

        for (int i = 0; i < 100; i++) {
            LinkedList<Integer> aSub, bSub;
            aSub = a.get(i);
            bSub = b.get(i);
            int min = Math.min(aSub.size(), bSub.size());
            for (int j = 0; j < min; j++) {
                commonNumbers.add(i + 1);
            }
        }

        commonNumbers.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        ArrayList<Integer> answer = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int commonNumber: commonNumbers) {
            LinkedList<Integer> aSub, bSub;
            aSub = a.get(commonNumber - 1);
            bSub = b.get(commonNumber - 1);

            int pa = 0, pb = 0;
            while (!aSub.isEmpty()) {
                int last = aSub.removeLast();
                if (a_pointer < last) {
                    pa = last;
                    break;
                }
            }

            while (!bSub.isEmpty()) {
                int last = bSub.removeLast();
                if (b_pointer < last) {
                    pb = last;
                    break;
                }
            }

            if (pa != 0 && pb != 0) {
                a_pointer = pa;
                b_pointer = pb;
                answer.add(commonNumber);
            }
        }
        sb.append(answer.size() + "\n");
        for (int i: answer) {
            sb.append(i + " ");
        }

        System.out.println(sb);

    }
}