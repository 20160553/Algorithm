import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int n, answer = 0;
    static String S;

    public static void main(String[] args) throws IOException {
        /*
        A와 B로만 이루어진 영어단어가 존재

        S, T 문자열 있을경우 S를 T로 만드는 게임
        연산
        1. 문자열 뒤에 A 추가
        2. 문자열 뒤에 B 추가하고 문자열 뒤집는다.

        출력 : 바꿀수 있으면 1, 없으면 0

        문제 풀이 방법
        연산 횟수 : len(B) - len(A)

        S -> T : 시간초과

        */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        S = br.readLine();
        String T = br.readLine();

        n = T.length() - S.length();

        dfs(0, T);
        System.out.println(answer);
    }

    static void dfs(int num, String str) {
        if (answer == 1) return;
        if (num == n) {
            if (str.equals(S))
                answer = 1;
            return;
        }
        if (str.charAt(str.length() - 1) == 'A')
            dfs(num + 1, str.substring(0, str.length() - 1));
        if (str.charAt(0) == 'B') {
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(1, str.length()));
            dfs(num + 1, sb.reverse().toString());
        }
    }

}