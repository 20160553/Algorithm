import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        /*
         * 조건
         * 1. 구성 종류 일치
         * 2. plus cnt, minus cnt
         *   1). plus cnt와 minus cnt 둘 중 하나만 1인 경우
         *   2) 둘 돠 1인 경우
         * */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());

        HashMap<Character, Integer> map = new HashMap<>();

        String origin = br.readLine();
        HashSet<Character> keySet = new HashSet<>();

        for (int i = 0; i < origin.length(); i++) {
            char c = origin.charAt(i);
            keySet.add(c);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        HashMap<Character, Integer> map2 = new HashMap<>();
        int answer = 0;
        loop1:
        for (int i = 1; i < n; i++) {
            int plusCnt = 0, minusCnt = 0;
            map2.clear();
            String str = br.readLine();

            for (int j = 0; j < str.length(); j++) {
                char c = str.charAt(j);
                keySet.add(c);
                if (map2.containsKey(c)) {
                    map2.put(c, map2.get(c) + 1);
                } else {
                    map2.put(c, 1);
                }
            }


            for (char c : keySet) {
                int disc = map.getOrDefault(c, 0) - map2.getOrDefault(c, 0);
                if (disc > 1 || disc < -1) continue loop1;
                if (disc == 1)
                    plusCnt++;
                else if (disc == -1)
                    minusCnt++;
            }
            if (plusCnt + minusCnt == 1) {
                answer++;
            } else if (plusCnt == 1 && minusCnt == 1) {
                answer++;
            } else if (plusCnt + minusCnt == 0) {
                answer++;
            }
        }

        System.out.println(answer);
    }
}