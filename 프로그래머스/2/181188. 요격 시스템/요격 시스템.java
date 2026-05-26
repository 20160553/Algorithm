import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        int answer = 0;
        
        //끝 지점이 빠른 순으로 정렬
        Arrays.sort(targets, (o1, o2) -> o1[1] - o2[1]);
        
        int start = 0;
        for (int i = 0; i < targets.length; i++) {
            int s = targets[i][0];
            int e = targets[i][1];
            if (s < start) {
                continue;
            } else {
                start = e;
                answer++;
            }
        }
        
        return answer;
    }
}