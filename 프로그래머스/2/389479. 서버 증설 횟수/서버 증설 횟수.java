import java.util.*;
import java.io.*;

class Solution {
    
    int getRequiredServerNum(int userNum, int m) {
        return userNum / m;
    }
    
    int withdrawServer(int addedServerCnt, int time, Queue<int[]> q) {
            //만료된 서버 회수
            while (!q.isEmpty()) {
                int[] addedServer = q.peek();
                int expiredTime = addedServer[0];
                int serverNum = addedServer[1];
                
                if (expiredTime <= time) {
                    q.poll();
                    addedServerCnt -= serverNum;
                } else {
                    break;
                }
            }
        return addedServerCnt;
    }
    
    public int solution(int[] players, int m, int k) {
        /*
        m명마다 서버 증가
        특정 시간대 이용자 n * m 이상, (n + 1) * m 미만 -> 최소  n대
        k시간동안 운영하고 반납
        
        총 증설 횟수 -> answer
        추가 증설 서버 카운팅 -> addedServerCnt
        추가 증설 서버 큐
        
        1. 시각 반복문 
            2. 큐 반복문 돌면서 만료된 서버 회수 -> addedServerCnt 감소, Queue.poll
            3. 서버 증설해야할 경우 증설 -> answer, addedServerCnt 증가, Queue.offer
        
        */
        int answer = 0;
        int addedServerCnt = 0;
        // StringBuilder sb = new StringBuilder();
        
        Queue<int[]> q = new LinkedList<>(); // 0: 만료시간, 1: cnt
        for (int time = 0; time < players.length; time++) {
            int playerNum = players[time];
            int requiredServerNum = getRequiredServerNum(playerNum, m);
            
            addedServerCnt = withdrawServer(addedServerCnt, time, q);
            requiredServerNum -= addedServerCnt;
            
            if (requiredServerNum > 0)
            {
                // sb.append((time + k) + " " + requiredServerNum + "\n");
                q.offer(new int[] {time + k, requiredServerNum});
                addedServerCnt += requiredServerNum;
                answer += requiredServerNum;
            }
        }
        // System.out.println(sb);
        return answer;
    }
}