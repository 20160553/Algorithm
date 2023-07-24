import java.util.*;

class Solution {
    public int[][] solution(int n) {
        
        /*
        1. 한 번에 하나의 원판만 옮길 수 있다.
        2. 큰 원판이 작은 원판 위에 있으면 안된다
        
        n개의 탑을 다른 한 곳으로 옮길 때 걸리는 횟수 : F(n)
        1층 옮기는 횟수 -> 1
        
        2개 층을 옮길 때
        1) 1층을 중간 층으로 옮기고 밑 판을 원하는 층에 옮김 -> F(2 - 1) + 1
        2) 1층을 원하는 층에 옮김 -> F(2 - 1)
        즉 1 + 1 + 1 => 3
        
        3개 층을 옮길 때
        1) 2개 층을 중간 층으로 옮기고 밑 판을 원하는 층에 옮긴다. -> F(2) + 1
        2) 2층을 원하는 층에 옮긴다 -> F(2)
        즉 2 * F(2) + 1 = 2*3 + 1 => 7
        
        =========================================
        점화식
        F(1) = 1,
        F(n) = 2 * F(n - 1) + 1
        =========================================
        
        경로 추출
        function hanoi(n, s, e)
        f(n, s, e) = f(n - 1, s, h)(s, e)f(n - 1, h, e) // h = 3 - (s + e) % 3
        */
        
        ArrayList<int[]> list = hanoi(n, 1, 3);
        int[][] answer = new int[list.size()][2];
         StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            int[] current = list.get(i);
             sb.append(current[0] + ", " + current[1] + "\n");
            answer[i][0] = current[0];
            answer[i][1] = current[1];
        }
        System.out.println(sb);
        
        return answer;
    }
    
    private ArrayList<int[]> hanoi(int n, int start, int end) {
        ArrayList<int[]> list;
        int halfwayPoint = 3 - (start + end) % 3;
        if (n == 0)
            return new ArrayList<int[]>();
        list = hanoi(n - 1, start, halfwayPoint);
        list.add(new int[] {start, end});
        list.addAll(hanoi(n - 1, halfwayPoint, end));
        
        return list;
    }
    
}