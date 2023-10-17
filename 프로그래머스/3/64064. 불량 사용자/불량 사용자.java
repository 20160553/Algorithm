import java.util.*;

class Solution {
    
    HashSet<String> name_set = new HashSet();
    boolean[] v;
    
    private boolean checkId(String user_id, String banned_id) {
        //user_id가 banned_id 될 수 있는 경우 true, 아닐 경우 false 반환
        
        int id_length = user_id.length();
        if (id_length != banned_id.length()) return false;
        for (int i = 0; i < id_length; i++) {
            if (banned_id.charAt(i) == '*' || user_id.charAt(i) == banned_id.charAt(i)) continue;
            return false;
        }
        return true;
    }
    
    private void detectBannedId(String[] user_id, String[] banned_id, int banned_idx) {
        if (banned_idx >= banned_id.length) {
            List<String> id_list = new LinkedList();
            for (int i = 0; i < v.length; i++) {
                if (v[i]) id_list.add(user_id[i]);
            }
            if (id_list.size() == banned_id.length) {
                Collections.sort(id_list);
                name_set.add(id_list.toString());
            }
            return;
        }
        
        for (int i = 0; i < user_id.length; i++) {
            if (v[i]) continue;
            if (checkId(user_id[i], banned_id[banned_idx])) {
                v[i] = true;
                detectBannedId(user_id, banned_id, banned_idx + 1);
                v[i] = false;
            }
        }
    }
    
    public int solution(String[] user_id, String[] banned_id) {
        /*
        불량 사용자 (완탐?)
        1. 길이비교
        2. 내용비교
        
        
        */
        
        int answer = 0;
        v = new boolean[user_id.length];
        LinkedList<String> id_list = new LinkedList<String>();
        detectBannedId(user_id, banned_id, 0);
        
        System.out.println(name_set);
        answer = name_set.size();
        return answer;
    }
}