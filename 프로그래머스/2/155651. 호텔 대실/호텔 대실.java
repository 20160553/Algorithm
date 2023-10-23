import java.util.*;

class Reserve {
    int startTime = 0, endTime = 0;
    public Reserve(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
        
    @Override
    public String toString() {
        return this.startTime + " " + this.endTime;
    }
}

class Solution {
    
    private int timeConvert(String strTime) {
        int minuteTime = 0;
        
        String[] timeArr = strTime.split(":");
        minuteTime += Integer.parseInt(timeArr[0]) * 60;
        minuteTime += Integer.parseInt(timeArr[1]);
        
        return minuteTime;
    }
    
    public int solution(String[][] bookTime) {
        /*
        호텔방 대실
        퇴실 시간 기준으로 10분간 청소
        필요한 최소 객실의 수
        
        "퇴실 시간이 제일 중요함"
        
        */
        Reserve[] reserves = new Reserve[bookTime.length];
        
        for (int i = 0; i < bookTime.length; i++) {
            int startTime = timeConvert(bookTime[i][0]);
            int endTime = timeConvert(bookTime[i][1]);
            reserves[i] = new Reserve(startTime, endTime + 10);
        }
        
        Arrays.sort(reserves, (Reserve x, Reserve y) -> x.startTime - y.startTime);
        PriorityQueue<Reserve> pq = new PriorityQueue<Reserve>((Reserve x, Reserve y) -> x.endTime - y.endTime);
        
        // System.out.println(Arrays.toString(reserves));
        
        for (Reserve reserve: reserves) {
            if (pq.isEmpty()) {
                pq.add(reserve);
                continue;
            }
            if (reserve.startTime >= pq.peek().endTime) {
                pq.poll();
            }
            pq.add(reserve);
        }
        
        return pq.size();
    }
}