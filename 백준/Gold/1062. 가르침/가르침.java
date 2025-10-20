import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int MAX = 26;
    static int wordCnt = 0;

    static HashSet<Character> defaultSet = new HashSet<>();


    private HashSet<Character> getCharSet(String word) {
        HashSet<Character> charSet = new HashSet<>();
        charSet.addAll(defaultSet);

        for (int i = 4; i < word.length() - 4; i++) {
            char c = word.charAt(i);
            charSet.add(c);
        }

        return charSet;
    }

    private int getReadableWordCnt(List<HashSet<Character>> sets, HashSet<Character> set) {
        int cnt = 0;

        for (HashSet<Character> s: sets) {
            if (set.containsAll(s)) {
                cnt++;
            }
        }

        return cnt;
    }

    private void solve(List<HashSet<Character>> sets, HashSet<Character> set, int num, int idx) {
        if (num == 0) {
            int cnt = getReadableWordCnt(sets, set);
            wordCnt = Math.max(cnt, wordCnt);
            return;
        }
        if (MAX - idx < num) {
            return;
        }

        char c = (char)('a' + idx);
        if (set.contains(c)) {
            solve(sets, set, num, idx + 1);
        } else {
            set.add(c);
            solve(sets, set, num - 1, idx + 1);
            set.remove(c);
            solve(sets, set, num, idx + 1);
        }
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

        defaultSet.add('a');
        defaultSet.add('n');
        defaultSet.add('t');
        defaultSet.add('i');
        defaultSet.add('c');

        HashSet<Character> totalSet = new HashSet<>();
        ArrayList<HashSet<Character>> sets = new ArrayList<>();

        Main main = new Main();

        for (int i = 0; i < n; i++) {
            HashSet<Character> set = main.getCharSet(br.readLine());
            sets.add(set);
            totalSet.addAll(set);
        }

        if (k < 5) {
            System.out.println(0);
            return;
        }
        if (k >= totalSet.size()) {
            System.out.println(n);
            return;
        }

        HashSet<Character> chosenSet = new HashSet<>();
        chosenSet.addAll(defaultSet);
        main.solve(sets, chosenSet, k - defaultSet.size(), 0);

        System.out.println(wordCnt);
        br.close();
    }
}