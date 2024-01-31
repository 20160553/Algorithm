import java.util.*;

class Solution {
    public int[] solution(String[] operations) {
        
        /*
        opnerations 길이 <= 1백만
        
        1. 우선순위 큐 2개 사용 (최대, 최소)
        
        1. 큐 사이즈 변수
        2. 최대힙, 최소힙
        3. 명령어 따라 삽입 삭제 진행
        4. 명령어 실행 후 큐 사이즈가 0일 경우 힙을 비워 줌
        
        
        */
        
        int[] answer = {0, 0};
        int qSize = 0;
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
        
        for (String operation: operations) {
            String[] o = operation.split(" ");
            String cmd = o[0];
            int num = Integer.parseInt(o[1]);
            if (cmd.equals("I")) {
                qSize++;
                maxHeap.offer(num);
                minHeap.offer(num);
            } else {
                if (qSize == 0) continue;
                qSize--;
                if (num == 1) {
                    maxHeap.poll();
                } else {
                    minHeap.poll();
                }
                if (qSize == 0) {
                    maxHeap.clear();
                    minHeap.clear();
                }
            }
        }
    
        if (qSize > 0) {
            answer[0] = maxHeap.peek();
            if (qSize == 1) {
                answer[1] = answer[0];
            } else {
                answer[1] = minHeap.peek();
            }
        }
        return answer;
    }
}