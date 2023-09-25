import java.util.*;

class Node {
    int idx;
    int price;
    
    public Node(int idx, int price) {
        this.idx = idx;
        this.price = price;
    }
}

class Solution {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        
        Stack<Node> stack = new Stack();
        
        for (int i = 0; i < prices.length; i++) {
            while (!stack.isEmpty() && stack.peek().price > prices[i]) {
                answer[stack.peek().idx] = i - stack.pop().idx;
            }
            stack.push(new Node(i, prices[i]));
        }
        for (Node node: stack) {
            answer[node.idx] = prices.length - 1 - node.idx;
        }
        return answer;
    }
}