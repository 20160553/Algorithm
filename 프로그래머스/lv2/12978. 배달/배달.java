import java.util.*;

class Solution {
    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        
        int[][] d = new int[N + 1][N + 1];
        PriorityQueue<int[]> pQ = new PriorityQueue<int[]>((o1, o2) -> o1[1] - o2[1]);
        
        StringBuilder sb = new StringBuilder(); //todo delete
        
        /*
        1. 다익스트라 쓰는 방법 => 우선순위 큐
        2. BFS 쓰는 방법
        */
        
        for (int i = 0; i < N + 1; i++)
            for (int j = 0; j < N+1; j++)
                d[i][j] = 5_000_001;
        d[1][1] = 0;
        
        //그래프 초기화
        for (int i = 0; i < road.length; i++) {
            d[road[i][0]][road[i][1]] = Math.min(road[i][2], d[road[i][0]][road[i][1]]);
            d[road[i][1]][road[i][0]] = Math.min(road[i][2], d[road[i][1]][road[i][0]]);
        }
        
        for (int i = 1; i < d.length; i++) {
            if (d[1][i] <= K)
                // e[0]는 검사한 위치, e[1]는 걸린 시간
                pQ.add(new int[] {i, d[1][i]});
        }
        
        while (!pQ.isEmpty()) {
            int[] current = pQ.poll();
            for (int i  = 2; i < d.length; i++) {
                int temp = current[1] + d[current[0]][i];
                if (temp < d[1][i]){
                    d[1][i] = temp;
                    pQ.add(new int[] {i, d[1][i]});
                }
            }
        }
        
        for (int i = 1; i < d.length; i++) {
            //sb.append(d[1][i] + " "); //todo delete
            if (d[1][i] <= K)
                answer++;
        }
        
        // [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
        //System.out.println(sb);

        
        return answer;
    }
}
