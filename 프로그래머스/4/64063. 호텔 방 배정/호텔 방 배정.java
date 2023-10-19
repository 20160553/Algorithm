import java.util.*;
class Solution {
    
    private long assign(long room_number, HashMap<Long, Long> rooms) {
        if (!rooms.containsKey(room_number)) {
            rooms.put(room_number, room_number);
            return room_number;
        }
        rooms.put(room_number, assign(rooms.get(room_number) + 1, rooms));
        return rooms.get(room_number);
    }
    
    public long[] solution(long k, long[] room_number) {
        long[] answer = new long[room_number.length];
        HashMap<Long, Long> rooms = new HashMap();
        
        for (int i = 0; i < room_number.length; i++) {
            answer[i] = assign(room_number[i], rooms);
        }
        
        return answer;
    }
}