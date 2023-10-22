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
        
        while (current < n + 1) {
            if (!stack.isEmpty() && stack.peek() == order[answer]) {
                stack.pop();
                answer++;
                continue;
            }
            if (order[answer] != current) stack.push(current);
            else answer++;
            current++;
        }
        
        while (!stack.isEmpty()) {
            if (stack.peek() != order[answer]) 
                break;
            stack.pop();
            answer++;
        }
        return answer;
    }
}