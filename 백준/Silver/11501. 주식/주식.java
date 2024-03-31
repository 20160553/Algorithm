import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        /*
        * 5억 연산 이하
        *
        * 흠...
        *
        *
        * DP ??
        *
        * 1. 사고
        * 2. 팔고
        * 3. 아무것도 안하고
        *
        * 스택도 가능한 듯
        *
        * 1. 스택에 있으면 빼고
        *
        * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(in.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        for (int t = 0; t < T; t++) {
            int n = Integer.parseInt(in.readLine());
            st = new StringTokenizer(in.readLine());
            LinkedList<Integer> stack = new LinkedList<>();

            for (int i = 0; i < n; i++)
                stack.push(Integer.parseInt(st.nextToken()));

            int max = 0;
            long answer = 0;
            while (!stack.isEmpty()) {
                int current = stack.pop();
                if (current < max) {
                    answer += max - current;
                } else {
                    max = current;
                }
            }
            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }
}