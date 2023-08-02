import java.util.*;

class Node {
    //0은 양, 1은 늑대
    int idx = 0;
    int animal = 0;
    int childCount = 0;
    int[] childs = new int[2];
    
    public Node(int idx, int animal) {
        this.idx = idx;
        this.animal = animal;
    }
    
    public void addChild(int child) {
        childs[childCount++] = child;
    }
}

class Solution {
    
    static Node[] nodes;
    static int maxSheepCount = 0;
    
    public int solution(int[] info, int[][] edges) {
        
        //트리 노드 저장용 해쉬
        nodes = new Node[info.length];
        
        //해쉬맵에 트리용 노드 저장
        for (int i = 0; i < info.length; i++) {
            nodes[i] = new Node(i, info[i]);
        }
        
        //트리 구성
        for (int i = 0; i < edges.length; i++) {
            Node parent = nodes[edges[i][0]];
            Node child = nodes[edges[i][1]];
            
            parent.addChild(child.idx);
        }
        
        int answer = 1;
        
        //첫 탐색 초기화
        LinkedList<Integer> nextNodes = new LinkedList<Integer>();
        for (int i = 0; i < nodes[0].childCount; i++) {
            nextNodes.add(nodes[0].childs[i]);
        }
        dfs(1, 1, nextNodes);
        
        return maxSheepCount;
    }
    
    public void dfs(int sheepCount, int availCount, LinkedList<Integer> nextNodes) {
        maxSheepCount = Math.max(maxSheepCount, sheepCount);
        //System.out.println(sheepCount + " " + availCount + " " + nextNodes);
        for (int i = 0; i < nextNodes.size(); i++) {
            Node nextNode = nodes[nextNodes.get(i)];
            
            LinkedList tempList = new LinkedList<Integer>();
            tempList.addAll(nextNodes);
            tempList.remove(i);
            
            for (int j = 0; j < nextNode.childCount; j++)
                tempList.add(nextNode.childs[j]);
            
            if (availCount - (nextNode.animal ^ 0) > 0)
                dfs(
                    sheepCount + (nextNode.animal ^ 1),
                    availCount - (nextNode.animal ^ 0) * 2 + 1,
                    tempList
                );
        }
    }
}