import java.util.*;

class Solution {
    public int solution(int[] elements) {
        int answer = 0;
        HashSet<Integer> set = new HashSet();
        
        /* 
        원형 수열
        for (int i = 0; i < elements.length; i++) { // 시작위치
            for (int j = 0; j < elements.length; j++) { // 원소 개수
                int count = 0;
                if (i > 0 && j == elements.length - 1) 
                    continue;
                for (int k = 0; k <= j; k++) {
                    count += elements[(i + k) % elements.length];
                }
                set.add(count);
            }
        }
        */
        
        int[][] dp = new int[elements.length+1][elements.length+1];
        for (int i = 1; i <= elements.length; i++) { // 원소 개수
            for (int j = 1; j <= elements.length; j++) { // 시작 위치
                dp[i][j] = dp[i - 1][j] + elements[(i + j) % elements.length];
                set.add(dp[i][j]);
            }
        }
        
        answer = set.size();
        return answer;
    }
}