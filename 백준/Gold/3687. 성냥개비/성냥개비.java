import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static HashMap<Integer, Integer> matchSticks = new HashMap<>();
    static String[] minDp;

    static void initMatchSticks() {
        matchSticks.put(1, 2);
        matchSticks.put(2, 5);
        matchSticks.put(3, 5);
        matchSticks.put(4, 4);
        matchSticks.put(5, 5);
        matchSticks.put(6, 6);
        matchSticks.put(7, 3);
        matchSticks.put(8, 7);
        matchSticks.put(9, 6);
        matchSticks.put(0, 6);

        minDp[2] = "1";
        minDp[3] = "7";
        minDp[4] = "4";
        minDp[5] = "2";
        minDp[6] = "6";
        minDp[7] = "8";
    }

    static void solveDp(int n) {
        /*
         *
         * */
        if (n < 8) return;
        int currentIdx = 8;
        while (currentIdx <= n) {
            for (int i = 2; i <= 7; i++) {
                if (currentIdx - i < 2) continue;
                String current = minDp[currentIdx];
                String temp = minDp[i];
                String temp2 = minDp[currentIdx - i];

                StringBuilder newNum = new StringBuilder();
                int cl = 0, ct = 0;
                while (cl < temp2.length() || ct < temp.length()) {
                    if (cl >= temp2.length()) {
                        int next = temp.charAt(ct++) - '0';
                        if (newNum.length() != 0 && next == 6) next = 0;
                        newNum.append(next);
                        continue;
                    }
                    if (ct >= temp.length()) {
                        int next = temp2.charAt(cl++) - '0';
                        if (newNum.length() != 0 && next == 6) next = 0;
                        newNum.append(next);
                        continue;
                    }
                    int next = 0;
                    if (temp2.charAt(cl) - '0' < temp.charAt(ct) - '0') {
                        next = temp2.charAt(cl) - '0';
                        cl++;
                    } else {
                        next = temp.charAt(ct) - '0';
                        ct++;
                    }
                    if (newNum.length() != 0 && next == 6) next = 0;
                    newNum.append(next);
                }
                if (Long.parseLong(current) > Long.parseLong(newNum.toString())) {
                    minDp[currentIdx] = newNum.toString();
                }
            }
            currentIdx++;
        }

    }

    static String getLargeNum(int n) {
        /*
         * 큰 숫자는 숫자가 많을 수록 좋음.
         * 그러므로 2, 3을 소모하는 1과 7로만 구성
         * */
        StringBuilder sb = new StringBuilder();

        while (n > 0) {
            if (n % 2 != 0) {
                n -= 3;
                sb.append(7);
                continue;
            }
            n -= 2;
            sb.append(1);
        }

        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();

        ArrayList<Integer> testcases = new ArrayList<>();

        int max = 0;
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            testcases.add(n);
            max = Math.max(max, n);
        }

        if (max < 7) minDp = new String[8];
        else minDp = new String[max + 1];

        initMatchSticks();

        for (int i = 8; i <= max; i++) {
            minDp[i] = Long.MAX_VALUE + "";
        }

        solveDp(max);

        for (int tc: testcases) {
            answer.append(minDp[tc] + " " +  getLargeNum(tc) + "\n");
        }

        System.out.println(answer);
        br.close();
    }
}