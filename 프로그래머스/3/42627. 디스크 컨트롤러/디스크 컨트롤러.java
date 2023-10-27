import java.util.*;

class Solution {
    public int solution(int[][] jobs) {
        /*
        하드디스크 작업 스케쥴링
        현재 작업할 수 있는 것 중 작업시간이 짧은 것 부터 처리
        */
        Arrays.sort(jobs, (x, y) -> {
            return x[0] - y[0];
        });
        int idx = 0, time = -1, waitingTimeSum = 0;
        int answer = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<int []>((x, y) -> {
           return x[1] - y[1];
        });
        while (idx < jobs.length) {
            if (jobs[idx][0] <= time) {
                pq.add(new int[] {jobs[idx][0], jobs[idx][1]});
                idx++;
                continue;
            }
            if (!pq.isEmpty()) {
                int[] execJob = pq.poll();
                waitingTimeSum += (time - execJob[0]) + execJob[1];
                time += execJob[1];
            } else {
                time = jobs[idx][0];
            }
        }
        while (!pq.isEmpty()) {
            int[] execJob = pq.poll();
            waitingTimeSum += (time - execJob[0]) + execJob[1];
            time += execJob[1];
        }
        answer = waitingTimeSum / jobs.length;
        return answer;
    }
}