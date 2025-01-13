import java.util.*;
import java.io.*;

class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
        
        /*
        n개 퍼즐
        난이도 : diff,
        현재 소요 시간 : time_cur
        이전 소요 시간 : time_prev
        전체 제한 시간 : limit
        
        구하려고자 하는 값 : 숙련도 `level`의 최솟값
        
        diff < level => time_cur 사용
        diff > level => diff - level 만큼 틀림
        
        최솟값을 구한다
        
        1. 이분탐색
        diffs.length <= 30만
        diff <= 10만
        
        2. 이분탐색 + 분할정복?
        
        */
        
        int answer = 0;
        int left = 1, right = 100_000;
        
        while (left <= right) {
            int mid = (left + right) / 2;
            long currentLimit = limit;
            long prevTime = 0;
            
            for (int i = 0; i < diffs.length; i++) {
                int diff = diffs[i];
                int time = times[i];
                
                if (diff <= mid) {
                    prevTime = time;
                    currentLimit -= time;
                } else {
                    int failedCnt = diff - mid;
                    currentLimit -= time * (failedCnt + 1);
                    currentLimit -= prevTime * failedCnt;
                    prevTime = time;
                }
                if (currentLimit < 0) {
                    break;
                }
            }
            
            if (currentLimit < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
                answer = mid;
            }
        }
        
        return answer;
    }
}