class Solution {
    static String IMPOSSIBLE = "impossible";
    static boolean[][] v;
    
    //세로
    static int[] dx = new int[] {
            1, 0, 0, -1
    };
    //가로
    static int[] dy = new int[] {
            0, -1, 1, 0
    };
    static String[] dc = new String[] {
            "d", "l", "r", "u"
    };
    
    static String permutation(int x, int y, int r, int c, int k, int count, String str) {
        //count > k인 경우
        if (count > k) return IMPOSSIBLE;
        //count == k이고 x != r || y !=c 인 경우
        if (count == k) {
            if (x != r || y != c) return IMPOSSIBLE;
            else return str;
        }
        //남은 횟수안에 도달 못하는 경우
        if (Math.abs(x - r) + Math.abs(y - c) > k - count) return IMPOSSIBLE;
        
        for (int i = 0; i < dx.length; i++) {
            int tx = x + dx[i];
            int ty = y + dy[i];
            
            //x 범위 밖인 경우
            if (tx < 1 || tx > v.length - 1) continue;
            //y 범위 밖인 경우
            if (ty < 1 || ty > v[0].length - 1) continue;
            //이미 들렀던 경우
//            if (v[tx][ty]) continue;
            
            //방문 처리
//            v[tx][ty] = true;
            String result = permutation(tx, ty, r, c, k, count+1, str+dc[i]);
            if (!result.equals(IMPOSSIBLE)) return result;
//            v[tx][ty] = false;
        }
        return IMPOSSIBLE;
    }
    
    static String solution(int n, int m, int x, int y, int r, int c, int k) {
        String answer = "";
        
         int distance = Math.abs(x - r) + Math.abs(y - c);
        //남은 횟수 안에 도달 못하는 경우
        if (distance > k) return IMPOSSIBLE;
        if ((distance - k) % 2 != 0 ) return IMPOSSIBLE;
        
        v = new boolean[n+1][m+1];
//        v[x][y] = true;
        
        answer = permutation(x, y, r, c, k, 0, "");
        
        return answer;
    }
    
}