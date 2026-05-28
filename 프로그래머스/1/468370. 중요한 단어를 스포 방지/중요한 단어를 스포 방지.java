import java.util.*;

class Solution {
    public int solution(String message, int[][] spoiler_ranges) {
        int answer = 0;
        int n = message.length();
        
        HashSet<String> unSignificants = new HashSet<>();
        HashSet<String> significants = new HashSet<>();
         
        boolean[] spoilerFlag = new boolean[n];
        
        for (int i = 0; i < spoiler_ranges.length; i++) {
            int start = spoiler_ranges[i][0];
            int end = spoiler_ranges[i][1];
            
            for (int j = start; j <= end; j++) {
                spoilerFlag[j] = true;
            }
        }
        
        int start = -1;
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            char c = message.charAt(i);
            if (c == ' ') {
                if (start == -1) continue;
                String str = message.substring(start, i);
                // System.out.println(str);
                if (flag) significants.add(str);
                else unSignificants.add(str);
                start = -1;
                flag = false;
                continue;
            }
            if (start == -1) start = i;
            flag |= spoilerFlag[i];
        }
        
        if (start != -1) {
            String str = message.substring(start, n);
            // System.out.println(str);
            if (flag) significants.add(str);
            else unSignificants.add(str);
            start = -1;
            flag = false;
        }
        
        significants.removeAll(unSignificants);
        
        return significants.size();
    }
}