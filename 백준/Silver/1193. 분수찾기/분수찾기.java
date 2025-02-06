import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
         * index 3 => 1 : 분
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int x = Integer.parseInt(br.readLine());

        int prevCnt = 0;
        int t = 1;

        while (true) {
            if (x <= prevCnt + t) {
                if (t % 2 == 0) {
                    int a = 1;
                    int b = t;
                    for (int i = 1; i <= t; i++) {
                        if (prevCnt + i == x) {
                            System.out.println(a+"/"+b);
                            return;
                        }
                        a++;
                        b--;
                    }
                } else {
                    int a = t;
                    int b = 1;
                    for (int i = 1; i <= t; i++) {
                        if (prevCnt + i == x) {
                            System.out.println(a+"/"+b);
                            return;
                        }
                        a--;
                        b++;
                    }
                }
            } else {
                prevCnt += t;
                t++;
            }
        }

    }
}