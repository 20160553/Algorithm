import java.util.*;

class Solution {
    public String solution(String m, String[] musicinfos) {
        /*
        음악을 반복 재생하는 경우도 존재.
        각 음은 1분에 1개씩 재생
        
        우선 순위
        1. 멜로디가 포함된 경우
        2. 재생 시간이 긴 경우
        3. 먼저 입력된 음악 제목
        
        문제 해결 방법
        0. 문자열 분할
        1. 재생된 멜로디 만들기
        2. 그 안에서 기억하는 멜로디 찾기
        3. 우선순위 비교
        
        사용할 수 있는 알고리즘
        1. 트라이
        2. KMP
        3. 깡 contains? => 시간 초과 날 것 같은데..?
        
        주의할 점
        1. # 처리 해야함
        */
        
        String answer = "(None)";
        int answerPlayTime = 0;
        
        for(String musicinfo: musicinfos) {
            String[] info = musicinfo.split(",");
            
            String[] startTime = info[0].split(":");
            String[] endTime = info[1].split(":");
            String musicName = info[2];
            String musicMelody = info[3];
            
            //지울 거
            String playedMelody = "";
            
            int playTime = (Integer.valueOf(endTime[0]) * 60 + Integer.valueOf(endTime[1]))
                - (Integer.valueOf(startTime[0]) * 60 + Integer.valueOf(startTime[1]));
            int tempPlayTime = playTime;
            
            int melodyEqualsNum = 0;
            for (int i = 0; i < tempPlayTime; i++) {
                int idx = i % musicMelody.length();
                char currentMelody = musicMelody.charAt(idx);
                char next = musicMelody.charAt((i + 1) % musicMelody.length());
                playedMelody += currentMelody;
                
                if (next == '#') tempPlayTime++;
                if (currentMelody == m.charAt(melodyEqualsNum)) {
                    melodyEqualsNum++;
                } else if (currentMelody == m.charAt(0)) melodyEqualsNum = 1;
                else melodyEqualsNum = 0;
                
                //기억하는 멜로디와 일치하는 경우
                if (melodyEqualsNum == m.length()) {
                    //다음 문자가 #인지 검사
                    if (next == '#')  {
                        if (m.length() != 1 && currentMelody == m.charAt(0)) {
                            melodyEqualsNum = 1;
                        }
                        else 
                            melodyEqualsNum = 0;
                    }
                    else {
                        if (answerPlayTime < playTime) {
                            answer = musicName;
                            answerPlayTime = playTime;
                        } 
                        // else if (answerPlayTime == playTime && answer.compareTo(musicName) < 0) {
                        //     answer = musicName;
                        // }
                        break;
                    }
                }
            }
            
            //System.out.println(playedMelody + " " + playTime + " " + playedMelody.length());
        }
        
        return answer;
    }
}