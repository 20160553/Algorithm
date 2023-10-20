import java.util.*;

class Solution {
    public String solution(String s) {
        StringTokenizer st = new StringTokenizer(s);
        String answer = "";
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int temp = 0;
        while (st.hasMoreElements()) {
            temp = Integer.parseInt(st.nextToken());
            if (temp < min) min = temp;
            if (temp > max) max = temp;
        }
        answer = min + " " + max;
        return answer;
    }
}