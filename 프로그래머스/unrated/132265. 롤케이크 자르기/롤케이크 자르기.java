import java.util.*;
class Solution {
    public int solution(int[] topping) {
        /*
        topping 길이 1..백만
        topping 종류 1..만
        
        완탐 => 시간초과
        그러면 DP일려나?
        
        1. 종류 count 배열
        */
        
        int answer = 0;
        HashMap<Integer, Integer> lMap = new HashMap();
        HashMap<Integer, Integer> rMap = new HashMap();
        
        //초기화
        for (int i = 0; i < topping.length; i++) {
            int current = topping[i];
            if (!lMap.containsKey(current)) {
                lMap.put(current, 1);
            } else {
                lMap.put(current, lMap.get(current) + 1);
            }
        }
        
        for (int i = topping.length - 1; i > 0; i--) {
            int current = topping[i];
            //케이크 자르기
            if (!rMap.containsKey(current)) {
                rMap.put(current, 1);
            }
            if (lMap.get(current) == 1) {
                lMap.remove(current);
            } else {
                lMap.put(current, lMap.get(current) - 1);
            }
            
            // 제대로 잘렸나 확인
            if (lMap.size() == rMap.size())
                answer++;
            else if (lMap.size() < rMap.size())
                break;
            //System.out.println("------------------");
            //System.out.println(lMap);
            //System.out.println(rMap);
        }
        
        return answer;
    }
}