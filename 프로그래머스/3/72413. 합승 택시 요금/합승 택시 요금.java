import java.util.*;
class Solution {
    
    int COST_MAX = 20_000_001;
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = COST_MAX;
        
        /*
        그래프
        최대 요금 : 2000만
        가중치 존재 양방향 간선
        다익스트라 써서 모든 경우 구한 뒤 조합? 아닐 것 같은뎅..
        */
        
        int[][] d = new int[n + 1][n + 1];
        
        for (int i = 1 ;i <= n; i++)
            for (int j = 1; j <= n; j++) {
                if (i == j) d[i][j] = 0;
                else d[i][j] = COST_MAX;
            }
                
        
        for (int[] fare: fares) {
            int v1, v2, cost;
            v1 = fare[0];
            v2 = fare[1];
            cost = fare[2];
            d[v1][v2] = cost;
            d[v2][v1] = cost;
        }
        
        //플로이드 - 워샬
        for (int k = 1; k <= n; k++) {
            //k가 경유점
            for (int i = 1; i <= n; i++) {
                //i가 출발
                for (int j = 1; j <= n; j++) {
                    //j가 목적
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }
        
        for (int i = 1; i <= n; i++) {
            answer = Math.min(answer, d[s][i] + d[i][a] + d[i][b]);
        }
        
        
        return answer;
    }
}