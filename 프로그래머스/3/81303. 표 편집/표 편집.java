import java.util.*;
class Solution {
   
    public String solution(int n, int k, String[] cmd) {
        String answer = "";

        int[] pre = new int[n];
        int[] next = new int[n];
        for (int i = 0; i < n; i++) {
            pre[i] = i - 1;
            next[i] = i + 1;
        }
        next[n - 1] = -1;
        
        Stack<Node> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("O");
        }
        for (int i = 0; i < cmd.length; i++) {
            char c = cmd[i].charAt(0);
            if (c == 'U') {
                int num = Integer.valueOf(cmd[i].substring(2));
                while(num-- > 0) {
                    k = pre[k];
                }
            }
            else if(c == 'D') {
                int num = Integer.valueOf(cmd[i].substring(2));
                while(num-- > 0) {
                    k = next[k];
                }
            }
            else if(c == 'C') {
                stack.push(new Node(pre[k], k, next[k]));
                if (pre[k] != -1) next[pre[k]] = next[k];
                if (next[k] != -1) pre[next[k]] = pre[k];
                sb.setCharAt(k, 'X');
                
                if (next[k] != -1) k = next[k];
                else k = pre[k];
            } else {
                Node node = stack.pop();
                if (node.pre != -1) next[node.pre] = node.cur;
                if (node.nxt != -1) pre[node.nxt] = node.cur;
                sb.setCharAt(node.cur, 'O');
            }
        }
        return sb.toString();
    }

    public class Node {
        int pre, cur, nxt;

        public Node(int pre, int cur, int nxt) {
            this.pre = pre;
            this.cur = cur;
            this.nxt = nxt;
        }
    }
}