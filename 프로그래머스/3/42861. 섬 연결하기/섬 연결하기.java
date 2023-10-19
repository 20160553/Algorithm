import java.util.*;

class Solution {
    
    int[] islands;
    
    private int find(int a) {
        int parent = islands[a];
        if (islands[a] == a) return a;
        return find(parent);
    }
    
    private boolean union(int a, int b) {
        int pa = find(a);
        int pb = find(b);
        if (pa == pb) return false;
        islands[b] = pa;
        islands[pb] = pa;
        return true;
    }
    
    public int solution(int n, int[][] costs) {
        /*
        최소 신장 트리?
        Union - Find
        */
        int answer = 0;
        islands = new int[n + 1];
        
        for (int i = 0; i <= n; i++) {
            islands[i] = i;
        }
        
        Arrays.sort(costs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });
        
        for (int i = 0; i < costs.length; i++) {
            if(union(costs[i][0], costs[i][1])) answer += costs[i][2];
        }
        
        return answer;
    }
}