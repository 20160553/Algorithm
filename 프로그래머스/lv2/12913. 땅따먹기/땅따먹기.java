import java.util.*;

class Solution {
    
    int COLUMN_ROW_NUM = 4;  
    
    int solution(int[][] land) {
        int answer = 0;

        // 1. 제일 간단한 방법 dfs 갈기기
        // 2. Dp로 풀릴 것 같음
        
        //백준 RGB
        
        /*
        0 1 2 3 5
        land[n][4 + 1];
        dp[n + 1][4 + 1];
        dp[i][j] = Max(dp[i-1][j -1], dp[i-1][j+1]) + arr[i][j];
        */
        
        int[][] dp = new int[land.length + 1][COLUMN_ROW_NUM + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < COLUMN_ROW_NUM + 1; j++) {
                int prevMax = 0;
                for (int k = 1; k <= COLUMN_ROW_NUM; k++) {
                    if (j == k) continue;
                    prevMax = Math.max(prevMax, dp[i-1][k]);
                }
                dp[i][j] = prevMax + land[i-1][j-1];
            }
        }
        answer = Math.max(answer, dp[dp.length - 1][1]);
        answer = Math.max(answer, dp[dp.length - 1][2]);
        answer = Math.max(answer, dp[dp.length - 1][3]);
        answer = Math.max(answer, dp[dp.length - 1][4]);
        // [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.

        
        return answer;
    }
}