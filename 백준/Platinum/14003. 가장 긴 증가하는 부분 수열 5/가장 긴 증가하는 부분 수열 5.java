import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        * 3억 이하
        * 512MB -> 512 / 4 => 10 ^ 6 * 128 => 인트형 128만개
        *
        * 수열 크기 -> 100만개 이하
        *
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        ArrayList<Integer> list = new ArrayList<>();

        int[] p = new int[n];
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            int current = Integer.parseInt(st.nextToken());
            arr[i] = current;
            if (list.isEmpty()) {
                list.add(current);
                p[i] = list.size();
                continue;
            }

            if (current > list.get(list.size() - 1)) {
                list.add(current);
                p[i] = list.size();
            } else {
                int position = Collections.binarySearch(list, current);
                if (position < 0) {
                    position++;
                    position *= -1;
                }
                list.set(position, current);
                p[i] = position + 1;
            }

        }
        StringBuilder answer = new StringBuilder();
        answer.append(list.size() + "\n");
        Stack<Integer> stack = new Stack<>();
        int idx = list.size();
        for (int i = p.length - 1; i >= 0; i--) {
            if (p[i] == idx) {
                stack.push(arr[i]);
                if (--idx == 0) break;
            }
        }
        while (!stack.isEmpty()) {
            answer.append(stack.pop() + " ");
        }
        
        System.out.println(answer);
    }
}