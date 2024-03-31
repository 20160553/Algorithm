import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
            ArrayList<Integer> stocks = new ArrayList<>();

            for (int i = 0; i < n; i++)
                stocks.add(Integer.parseInt(st.nextToken()));

            int max = stocks.get(n - 1);
            long answer = 0;
            for (int i = stocks.size() - 1; i >= 0; i--) {
                int current = stocks.get(i);
                if (current <= max) {
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