import java.util.*;
class Solution {
    public int[] solution(String[] name, int[] yearning, String[][] photo) {
        int[] answer = new int[photo.length];
        
        /*
        
        */
        Map peopleScore = new HashMap<String, Integer>();
        for (int i = 0; i < name.length; i++) {
            peopleScore.put(name[i], yearning[i]);
        }
        
        for (int i = 0; i < photo.length; i++) {
            int score = 0;
            for (int j = 0; j < photo[i].length; j++) {
                String now =photo[i][j];
                if (peopleScore.containsKey(now))
                    score = score + (int)peopleScore.get(now);
            }
            answer[i] = score;
        }
        
        
        
        return answer;
    }
}