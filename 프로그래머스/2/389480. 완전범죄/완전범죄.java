import java.util.*;

class Solution {
    public int solution(int[][] info, int n, int m) {
        /*
        A, B 도둑
        흔적이 누적되면 잡힘.
        
        DP or 완탐이죠?
        
        백팩 문제
        
        (무게, 가치)만 고려, 
        
        info.length < 40
        
        각 물건 당 경우의 수 : A, 0, B
        
        완탐 시 시간복잡도 => O(3 ^ 40) => 시간초과
        
        DP 배열 => DP[info.length + 1][m + 1] (i = info.length, j = info.length)
        행 : 훔칠 물건 idx + 1
        열 : B의 흔적
        
        현재 아이템 흔적 a, b
        dp[i + 1][y] = Math.min(dp[i][y] + a, dp[i][y-b])
        
        고려안해도 되는 포인트 : 물건 선택 수 => '모든' 물건을 훔쳐야 함
        */
        
        StringBuilder sb = new StringBuilder(); // 디버그용
        int[][] dp = new int[info.length + 1][m];
        
        int rowSize = dp.length;
        int columnSize = dp[0].length;
        
        // 배열 초기화
        int MAX_VALUE = n;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                dp[i][j] = MAX_VALUE;
            }
        }
        
        dp[0][0] = 0;
        sb.append(Arrays.toString(dp[0]) + "\n");
        
        // dp 연산
        for (int i = 1; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                int[] current = info[i - 1];
                int a = current[0];
                int b = current[1];
                
                int selectedA = MAX_VALUE;
                int selectedB = MAX_VALUE;
                
                selectedA = dp[i - 1][j] + a;
                if (j >= b)
                    selectedB = dp[i - 1][j - b];
                
                dp[i][j] = Math.min(selectedA, selectedB);
            }
            sb.append(Arrays.toString(dp[i]) + "\n");
        }
        
        // 해답
        int answer = MAX_VALUE;
        
        for (int i = 0; i < columnSize; i++)
            answer = Math.min(answer, dp[rowSize - 1][i]);
        
        if (answer >= MAX_VALUE) answer = -1;
        
        System.out.println(sb);
        return answer;
    }
}