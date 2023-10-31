import java.util.*;

class Node {
    Node parent;
    int money;
    
    public Node(Node parent) {
        this.parent = parent;
    }
    
    public void earn(int profit) {
        int tax = profit / 10;
        if (this.parent != null) {
            this.parent.earn(tax);
            profit -= tax;
        }
        this.money += profit;
    }
}

class Solution {
    int PRICE = 100;
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        /*
        자식 정보 기록 불필요
        부모 정보 기록 필요
        자기 자신의 이득 표시 필요
        */
        
        int[] answer = new int[enroll.length];
        
        Node root = new Node(null);
        HashMap<String, Node> map = new HashMap();
        
        //초기화
        for (int i = 0; i < enroll.length; i++) {
            if (map.get(enroll[i]) == null) {
                Node parent;
                if (referral[i].equals("-")) {
                    parent = root;
                } else parent = map.get(referral[i]);
                map.put(enroll[i], new Node(parent));
            }
        }
        
        for (int i = 0; i < seller.length; i++) {
            int profit = PRICE * amount[i];
            map.get(seller[i]).earn(profit);
        }
        
        for (int i = 0; i < enroll.length; i++) {
            answer[i] = map.get(enroll[i]).money;
        }
        
        return answer;
    }
}