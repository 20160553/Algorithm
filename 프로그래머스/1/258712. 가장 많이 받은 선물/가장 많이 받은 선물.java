import java.util.*;

class Solution {
    public int solution(String[] friends, String[] gifts) {
        /*
        가장 많이 받은 선물
        
        map 사용해서 선물 기록
        
        */
        
        
        int answer = 0;
        HashMap<String, HashMap<String, Integer>> giftMap = new HashMap<>();
        HashMap<String, Integer> giftIndexes = new HashMap<>();
        for (String friend: friends) {
            giftMap.put(friend, new HashMap<>());
            giftIndexes.put(friend, 0);
        }
        
        for (String gift: gifts) {
            String[] persons = gift.split(" ");
            HashMap<String, Integer> sendMap = giftMap.get(persons[0]);
            HashMap<String, Integer> receiveMap = giftMap.get(persons[1]);
            
            giftIndexes.put(persons[0], giftIndexes.get(persons[0]) + 1);
            giftIndexes.put(persons[1], giftIndexes.get(persons[1]) - 1);
            
            int giftIndex = sendMap.getOrDefault(persons[1], 0);
            sendMap.put(persons[1], ++giftIndex);
            receiveMap.put(persons[0], -giftIndex);
        }
        
        for (String sender: friends) {
            HashMap<String, Integer> map = giftMap.get(sender);
            int senderIndex = giftIndexes.get(sender);
            
            int cnt = 0;
            for (String receiver: friends) {
                if (sender.equals(receiver)) continue;
                int receiverIndex = giftIndexes.get(receiver);
                int interIndex = map.getOrDefault(receiver, 0);
                
                if (interIndex > 0) cnt++;
                else if (interIndex == 0 && senderIndex > receiverIndex) cnt++;
            }
            
            answer = Math.max(cnt, answer);
        }
        
        return answer;
    }
}