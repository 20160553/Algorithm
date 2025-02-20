import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;

    public class Main {
        public static void main(String[] args) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String str = br.readLine();
            String[] codes = new String[]{"100~1~", "01"};

            boolean answerF = true;
            boolean flag = false;
            boolean flag2 = false;
            int p = 0;
            loop: while (p < str.length()) {
                if (str.charAt(p) == '1') {
                    p++;
                    flag = true;
                    if (str.length() - p < 3) {
                        answerF = false;
                        break;
                    }
                }
                else {
                    if (str.length() - p < 2) {
                        answerF = false;
                        break;
                    }
                    int pre = p - 1;
                    p++;
                    //01 판단
                    if (!flag && str.charAt(p) == '1') {
                        p++;
                        continue;
                    }
                    int zeroCnt = 1;
                    while (p < str.length() && str.charAt(p) == '0') {
                        p++;
                        zeroCnt++;
                    }
                    if (zeroCnt < 2) {
                        answerF = false;
                        break;
                    }
                    int oneCnt = 0;
                    while (p < str.length() && str.charAt(p) == '1') {
                        p++;
                        oneCnt++;
                    }
                    if (oneCnt < 1) {
                        answerF = false;
                        break;
                    }
                    if (flag) {
                        flag = false;
                    } else {
                        if (!(pre > 0 && (str.charAt(pre) == str.charAt(pre - 1) && str.charAt(pre) == '1'))) {
                            answerF = false;
                            break;
                        }
                    }
                }
            }
            String answer = "NOISE";
            if (answerF) answer = "SUBMARINE";
            System.out.println(answer);
        }
    }