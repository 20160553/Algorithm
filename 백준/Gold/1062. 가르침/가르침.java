import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int MAX = 26;
    static int wordCnt = 0;
    static int totalCnt = 5;
    static boolean[] totalV = new boolean[26];

    Main() {
        totalV['a' - 'a'] = true;
        totalV['n' - 'a'] = true;
        totalV['t' - 'a'] = true;
        totalV['i' - 'a'] = true;
        totalV['c' - 'a'] = true;
    }

    private void getCharSet(String word, boolean[] v) {
        //a, n, t, i c 필수 포함.
        v['a' - 'a'] = true;
        v['n' - 'a'] = true;
        v['t' - 'a'] = true;
        v['i' - 'a'] = true;
        v['c' - 'a'] = true;

        for (int i = 4; i < word.length() - 4; i++) {
            if (!totalV[word.charAt(i) - 'a']) {
                totalCnt++;
                totalV[word.charAt(i) - 'a'] = true;
            }
            v[word.charAt(i) - 'a'] = true;
        }
    }

    private int getReadableWordCnt(boolean[][] words, boolean[] v) {
        int cnt = 0;

        for (boolean[] word: words) {
            boolean flag = true;
            for (int i = 0; i < v.length; i++) {
                if (word[i] && !v[i]) {
                    flag = false;
                    break;
                }
            }
            if (flag) cnt++;
        }

        return cnt;
    }

    private void solve(boolean[][] words, boolean[] v, int num, int idx) {
        if (num == 0) {
            int cnt = getReadableWordCnt(words, v);
            wordCnt = Math.max(cnt, wordCnt);
            return;
        }
        if (MAX - idx < num) {
            return;
        }
        if (idx >= 26) return;

        if (!v[idx]) {
            v[idx] = true;
            solve(words, v, num - 1, idx + 1);
            v[idx] = false;
        }

        solve(words, v, num, idx + 1);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /*
        * a, n, t, i c 필수 포함.
        * k < 5인 경우, 읽을 수 있는 단어는 없음
        * k >= 사용된 글자 종류인 경우 읽을 수 있는 단어 : n
        *
        * 0 <= k <= 26
        * N <= 50
        *
        * 알고리즘
        * 1. 완탐
        * 2.
        *
        * */

        int n, k;
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        boolean[][] words = new boolean[n][26];

        ArrayList<HashSet<Character>> sets = new ArrayList<>();

        Main main = new Main();

        for (int i = 0; i < n; i++) {
            main.getCharSet(br.readLine(), words[i]);
        }

        if (k < 5) {
            System.out.println(0);
            return;
        }
        if (k >= totalCnt) {
            System.out.println(n);
            return;
        }

        boolean[] v = new boolean[26];

        v['a' - 'a'] = true;
        v['n' - 'a'] = true;
        v['t' - 'a'] = true;
        v['i' - 'a'] = true;
        v['c' - 'a'] = true;

        main.solve(words, v, k - 5, 0);

        System.out.println(wordCnt);
        br.close();
    }
}