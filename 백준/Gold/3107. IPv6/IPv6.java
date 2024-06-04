import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         * ip 풀어씀 : 갯수 : 7개
         * 부분 배열 : 8개
         *
         * :: => "", ""
         *
         * 한 개 지우고
         *
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        String ip = br.readLine();
        ip = ip.replaceFirst("::", ":-:");
        String[] splitedIp = ip.split(":");

        for (String part : splitedIp) {
            if (part.equals("-")) {
                for (int i = splitedIp.length - 1; i < 8; i++) {
                    answer.append("0000:");
                }
                continue;
            }

            for (int i = part.length(); i < 4; i++) {
                answer.append("0");
            }
            answer.append(part).append(":");
        }
//        if (answer.length() == 0) answer.append("0000:0000:0000:0000:0000:0000:0000:0000:");
        answer.deleteCharAt(answer.length() - 1);
        System.out.println(answer);
    }
}