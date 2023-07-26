import java.util.*;

class Solution {
    public int solution(int[][] triangle) {
        /*
        삼각형 높이 : triangle.length
        
        
        
        */
        int h = triangle.length;
        int answer = 0;
        int[][] dp = new int[h][h];
        
        dp[0][0] = triangle[0][0];
        
        for (int i = 0; i <  h - 1; i++) {
            for (int j = 0; j < triangle[i].length; j++) {
                dp[i + 1][j] = Math.max(dp[i+1][j], dp[i][j] + triangle[i+1][j]);
                dp[i + 1][j+1] = Math.max(dp[i+1][j+1], dp[i][j] + triangle[i+1][j+1]);
            }
        }
    
        /*StringBuilder sb = new StringBuilder();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < triangle[i].length; j++) {
                sb.append(dp[i][j] + " ");
            }
            sb.append("\n");
        }
        
        System.out.println(sb);*/
        
        for (int i = 0; i < h; i++) {
            answer = Math.max(answer, dp[h-1][i]);
        }
        
        return answer;
    }
}