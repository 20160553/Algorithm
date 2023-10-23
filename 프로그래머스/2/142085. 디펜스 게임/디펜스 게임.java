import java.util.*;

class Solution {
    public int solution(int n, int k, int[] enemy) {
        int answer = 0;
        PriorityQueue<Integer> pQ = new PriorityQueue();
        for (int i = 0; i < enemy.length; i++) {
            pQ.add(enemy[i]);
            int count = 0;
            if (i >= k) {
                n -= pQ.poll();
                if (n < 0) break;
            }
            answer++;
        }        
        
        return answer;
    }
}