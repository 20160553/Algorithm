import java.util.*;
import java.io.*;

class QueueNode {
    int r = -1, c = -1;
    LinkedList<Integer> route = new LinkedList<>();
    
    QueueNode(int[] route) {
        for (int point: route) {
            this.route.add(point);
        }
    }
    
    public int[] move(int[][] points) {
        if (route.isEmpty()) return null;
        
        if (this.r == -1 || this.c == -1) {
            int[] startPoint = points[route.poll() - 1];
            this.r = startPoint[0];
            this.c = startPoint[1];
            return new int[] {this.r, this.c};
        }
        
        int next = route.peek();
        int nR = points[next - 1][0];
        int nC = points[next - 1][1];
        
        if (nR != this.r) {
            if (this.r > nR) 
                this.r--;
            else 
                this.r++;
        } else if (nC != this.c) {
            if (this.c > nC) 
                this.c--;
            else
                this.c++;
        }
        
        if (this.r == nR && this.c == nC) {
            route.poll();
        }
        if (route.isEmpty()) return null;
        
        return new int[] {this.r, this.c};
    }
    
    public String toString() {
        return "[" + this.r + " " + this.c + "]";
    }
}

class Solution {
    public int solution(int[][] points, int[][] routes) {
        /*
        1. BFS 이용
         -> Queue에 현재 위치, 다음으로 들려야하는 노드들 추가
         -> day 종료 후 충돌 검사
        */
        
        int answer = 0;
        
        LinkedList<QueueNode> q = new LinkedList<>();
        
        for (int[] route: routes) {
            q.offer(new QueueNode(route));
        }
        
        //bfs
        while (!q.isEmpty()) {
            int[][] map = new int[101][101];
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                QueueNode currentNode = q.poll();
                int[] result = currentNode.move(points);
                if (result != null) {
                    q.offer(currentNode);
                }
                map[currentNode.r][currentNode.c]++;
            }
            // 하루 종료
            for (int i = 0; i < 101; i++)
                for (int j = 0; j < 101; j++){
                    if (map[i][j] > 1) {
                        answer++;
                    }
                }
        }
        
        return answer;
    }
}