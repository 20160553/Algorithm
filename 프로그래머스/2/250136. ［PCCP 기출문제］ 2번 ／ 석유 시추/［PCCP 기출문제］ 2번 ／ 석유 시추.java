import java.util.*;

class Solution {
    
    int[] dy = {0,-1, 0, 1};
    int[] dx = {-1, 0, 1, 0};
        
    public int bfs(int y, int x, int cnt, int[][] land) {
        int oilAmount = 1;
        land[y][x] = -cnt;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] {y, x});
        while (!q.isEmpty()) {
            int[] current  = q.poll();
            for (int i = 0; i < dy.length; i++) {
                int ny = current[0] + dy[i];
                int nx = current[1] + dx[i];
                if (ny < 0 || nx < 0 || ny >= land.length || nx >= land[0].length) continue;
                if (land[ny][nx] <= 0) continue;
                land[ny][nx] = -cnt;
                oilAmount++;
                q.offer(new int[] {ny, nx});
            }
        }
        return oilAmount;
    }
    
    public int dfs(int y, int x, int cnt, int[][] land) {
        int oilAmount = 1;
        land[y][x] = -cnt;
        
        for (int i = 0; i < dy.length; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            if (ny < 0 || nx < 0 || ny >= land.length || nx >= land[0].length) continue;
            if (land[ny][nx] <= 0) continue;
            oilAmount += dfs(ny, nx, cnt, land);
        }
        return oilAmount;
    }
    
    public int solution(int[][] land) {
        int answer = 0;
        
        HashMap<Integer, Integer> oils =new HashMap<>();
        
        int cnt = 1;
        for (int j = 0; j < land[0].length; j++) {
            int foundOilAmount = 0;
            HashSet<Integer> set = new HashSet<>();
            for (int i = 0; i < land.length; i++) {
                if (land[i][j] == 0) continue;
                if (land[i][j] < 0){
                    set.add(-land[i][j]);
                    continue;
                }
                set.add(cnt);
                // int amount = dfs(i, j, cnt, land);
                int amount = bfs(i, j, cnt, land);
                oils.put(cnt++, amount);
            }
            
            for (int oil: set) {
                foundOilAmount += oils.get(oil);
            }
            answer = Math.max(foundOilAmount, answer);
        }
        
        return answer;
    }
}