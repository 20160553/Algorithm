import java.util.*;

class Solution {
    
    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
    
    private int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }
        
    public int solution(int[][] signals) {
        /*
        1. 최대 공약수 찾기
        2. 최소 공배수 찾기
        
        3. 최소 공배수까지 완탐
        
        최대공약수 => 유클리드 호제법
        while (b % a != 0) {
            int t = b % a;
            a = b;
            b = t;
        }
        */
        int n = signals.length;
        
        int maxLimitTime = 1; // 모든 신호의 최소공배수
        for (int i = 0; i < n; i++) {
            int cycle = signals[i][0] + signals[i][1] + signals[i][2];
            maxLimitTime = lcm(maxLimitTime, cycle);
        }
        
        for (int t = 1; t <= maxLimitTime; t++) {
            boolean allYellow = true;
            
            for (int i = 0; i < n; i++) {
                int g = signals[i][0];
                int y = signals[i][1];
                int r = signals[i][2];
                int cycle = g + y + r;
                
                int timeInCycle = t % cycle;
                if (timeInCycle == 0) {
                    timeInCycle = cycle;
                }
                
                if (!(g < timeInCycle && timeInCycle <= g + y)) {
                    allYellow = false;
                    break;
                }
            }
            if (allYellow) {
                return t;
            }
        }
        return -1;
    }
}