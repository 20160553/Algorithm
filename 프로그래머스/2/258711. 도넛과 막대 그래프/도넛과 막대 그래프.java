import java.util.*;

class Vertex {
    public int idx;
    public int inCnt = 0;
    public ArrayList<Vertex> d = new ArrayList<>();
    
    public Vertex(int idx) {
        this.idx = idx;
    }
    
}

class Solution {
    
    private static int dfs(Vertex vertex, HashSet<Integer> v) {
        //더 진행 불가시 막대그래프
        if (vertex.d.size() > 1) return 3;
        if (vertex.d.size() == 0) return 2;
        if (v.contains(vertex.idx)) {
            if (vertex.d.size() == 1) return 1;
            return 3;
        }
                
        Vertex next = vertex.d.get(0);
        v.add(vertex.idx);
        return dfs(next, v);
    }
    
    public int[] solution(int[][] edges) {
        /*
        간선 : 나가는 간선, - 들어오는 간선
        새로 생성한 임의의 정점 : 나가는 간선의 수가 2 이상 (그래프 수 2 이상)
        막대 : 1, -1
        도넛 모양 : 1, -1, 0, -1
        8자 모양 : 2, -2, 1, -1
        */
        int[] answer = {0, 0, 0, 0};
        
        HashMap<Integer, Vertex> vertexes = new HashMap<>();
        
        int startVertex = -1;
        
        for (int[] edge: edges) {
            
            if (vertexes.get(edge[0]) == null)
                vertexes.put(edge[0], new Vertex(edge[0]));
            if (vertexes.get(edge[1]) == null)
                vertexes.put(edge[1],  new Vertex(edge[1]));
            
            Vertex start = vertexes.get(edge[0]);
            Vertex end = vertexes.get(edge[1]);
            
            
            start.d.add(end);
            end.inCnt++;
        }
        
        for (Vertex vertex: vertexes.values()) {
            if (vertex.d.size() - vertex.inCnt >= 2){
                startVertex = vertex.idx;
                break;
            }
        }
        
        answer[0] = startVertex;
        for (Vertex vertex: vertexes.get(startVertex).d) {
            int result = dfs(vertex, new HashSet<>());
            answer[result]++;
        }
        
        return answer;
    }
}