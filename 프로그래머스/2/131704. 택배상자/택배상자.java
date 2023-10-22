import java.util.*;

class Solution {
    public int solution(int[] order) {
        /*
        상자 1 -> n 순서
        order 대로 넣어야함
        보조 컨테이너 -> 스택
        
        1. 빡통으로 풀기(완탐)
        */
        
        Stack<Integer> stack = new Stack();
        
        int answer = 0, n = order.length, current = 1;
        
        for (int i = 0; i < n; i++) {
            while (current < order[i])
                stack.push(current++);
            if (current == order[i]) {
                current++;
                answer++;
            } else if (!stack.isEmpty() && stack.peek() == order[i]) {
                stack.pop();
                answer++;
            } else return answer;
        }
        
        return answer;
    }
}