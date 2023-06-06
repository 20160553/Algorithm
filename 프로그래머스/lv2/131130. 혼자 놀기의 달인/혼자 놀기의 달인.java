import java.util.*;

class Solution {
    
    
    boolean[] v;
    int[] cards;
    
    public int dfs(int count, int num) {
        if (v[num - 1]) return count;
        v[num - 1] = true;
        return dfs(count+1, cards[num - 1]);
    }
    
    public int solution(int[] cards) {
        /*
        카드더미는 1..100
        사용할 카드 수 2..100
        셔플 뒤 나열
        상자 따로 순차적으로 번호 붙임
        
        */
        
        int answer = 0;
        this.cards = cards;
        v = new boolean[cards.length];
        PriorityQueue<Integer> pQ = new PriorityQueue(Collections.reverseOrder());
        
        for (int i = 1; i <= cards.length; i++) {
            if (v[i - 1] == true) continue;
            pQ.add(dfs(0, i));
        }
        if (pQ.size() > 1) answer = pQ.poll() * pQ.poll();
        return answer;
    }
}