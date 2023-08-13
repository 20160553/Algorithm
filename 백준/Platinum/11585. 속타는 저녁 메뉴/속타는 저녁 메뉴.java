import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        char[] pattern = new char[n];
        char[] str = new char[n * 2 - 1];
        int numerator = 0, denominator = n;
        String s = br.readLine();
        int i = 0;
        int sIdx = 0;
        while (i < n) {
            if (s.charAt(sIdx) != ' ') {
                pattern[i] = s.charAt(sIdx);
                i++;
            }
            sIdx++;
        }
        s = br.readLine();
        i = 0;
        sIdx= 0;
        while (i < n) {
            if (s.charAt(sIdx) != ' ') {
                str[i] = s.charAt(sIdx);
                i++;
            }
            sIdx++;
        }
        for (int j = 0; j < n - 1; j++) {
            str[n + j] = str[j];
        }

        numerator = KMP(str, pattern);

        int j = 2;
        while (j <= numerator) {
            if (numerator % j == 0 && denominator % j == 0) {
                numerator /= j;
                denominator /= j;
                j = 2;
            } else
                j++;
        }
        System.out.println(numerator + "/" + denominator);
    }

    public static int KMP(char[] str, char[] pattern) {

        int l1 = str.length;
        int l2 = pattern.length;
        int[] table = createTable(pattern);

        int j = 0, count = 0;
        for (int idx = 0; idx < l1; idx++) {
            while (j > 0 && str[idx] != pattern[j]) {
                j = table[j - 1];
            }
            if (str[idx] == pattern[j]) {
                if (j == l2 - 1) {
                    j = table[j];
                    count++;
                } else {
                    j++;
                }
            }
        }
        return count;
    }

    public static int[] createTable(char[] pattern) {

        int l = pattern.length;
        int j = 0;
        int[] table = new int[l];
        for (int idx = 1; idx < l; idx++) {
            while (j > 0 && pattern[j] != pattern[idx]) {
                j = table[j - 1];
            }
            if (pattern[j] == pattern[idx]) {
                table[idx] = ++j;
            }
        }
        return table;
    }
}
