import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int m = Integer.parseInt(br.readLine());
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> all = new HashSet<>();

        for (int i = 1; i <= 20; i++ ) {
            all.add(i);
        }

        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            String cmd = st.nextToken();
            int inputNum = 0;
            if (st.hasMoreTokens()) {
                inputNum = Integer.parseInt(st.nextToken());
            }

            switch (cmd) {
                case "add":
                    set.add(inputNum);
                    break;
                case "remove":
                    set.remove(inputNum);
                    break;
                case "check":
                    if (set.contains(inputNum))
                        answer.append(1 + "\n");
                    else
                        answer.append(0 + "\n");
                    break;
                case "toggle":
                    if (set.contains(inputNum)) {
                        set.remove(inputNum);
                    } else
                        set.add(inputNum);
                    break;
                case "all":
                    set.addAll(all);
                    break;
                case "empty":
                    set.clear();
                    break;

            }
        }
        System.out.print(answer);
    }
}