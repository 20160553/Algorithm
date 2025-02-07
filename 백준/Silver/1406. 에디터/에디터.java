import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Stack<Character> lEditor = new Stack<>();
        Stack<Character> rEditor = new Stack<>();

        String str = br.readLine();

        for (int i = 0; i < str.length(); i++) {
            lEditor.push(str.charAt(i));
        }

        int m = Integer.parseInt(br.readLine());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            String cmd = st.nextToken();
            char context = ' ';
            switch (cmd) {
                case "L":
                    if (lEditor.isEmpty()) continue;
                    rEditor.push(lEditor.pop());
                    break;
                case "D":
                    if (rEditor.isEmpty()) continue;
                    lEditor.push(rEditor.pop());
                    break;
                case "B":
                    if (lEditor.isEmpty()) continue;
                    lEditor.pop();
                    break;
                case "P":
                    context = st.nextToken().charAt(0);
                    lEditor.push(context);
                    break;
            }
        }

        StringBuilder lSb = new StringBuilder();
        StringBuilder rSb = new StringBuilder();

        while (!lEditor.isEmpty()) {
            lSb.append(lEditor.pop());
        }
        while (!rEditor.isEmpty())
            rSb.append(rEditor.pop());

        System.out.println(lSb.reverse() + rSb.toString());
    }
}