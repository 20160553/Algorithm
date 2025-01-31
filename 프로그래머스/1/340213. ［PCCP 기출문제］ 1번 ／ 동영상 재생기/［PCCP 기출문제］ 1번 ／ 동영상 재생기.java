import java.util.*;

class Solution {
    
    private static int timeToSecond(String time) {
        String[] t = time.split(":");
        
        int second = 0;
        
        second += Integer.parseInt(t[0]) * 60;
        second += Integer.parseInt(t[1]);
        
        return second;
    }
    
    private static String secondToTime(int second) {
        String time = "";
        int m = second / 60;
        int s = second % 60;
        
        time += String.format("%02d", m);
        time += ":";
        time += String.format("%02d", s);
        return time;
    }
    
    
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        String answer = "";
        
        int video_len_s = timeToSecond(video_len);
        int op_start_s = timeToSecond(op_start);
        int op_end_s = timeToSecond(op_end);
        int pos_s = timeToSecond(pos);
        int now_s = pos_s;
        
        for (String command: commands) {
            
          if (op_start_s <= now_s && now_s < op_end_s) 
                now_s = op_end_s;
            
            switch(command) {
                case "next":
                    now_s += 10;
                    if (now_s > video_len_s) 
                        now_s = video_len_s;
                    break;
                case "prev":
                    now_s -= 10;
                    if (now_s < 0)
                        now_s = 0;
                    break;
            }
        }
        
        if (op_start_s <= now_s && now_s < op_end_s) 
            now_s = op_end_s;
        
        answer = secondToTime(now_s);
        return answer;
    }
}