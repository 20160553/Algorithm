import java.util.*;

class Solution {
    
    private int[][] rotateKey(int[][] key) {
        int n = key.length;
        int[][] rotatedKey = new int[key.length][key.length];
        // System.out.println("Rotated!!");
        /*
        0, 0 => 0, 2
        0, 1 => 1, 2
        0, 2 => 2, 2
        1, 0 => 0, 1
        1, 1 => 1, 1
        1, 2 => 2, 1
        2, 0 => 0, 0
        2, 1 => 1, 0
        2, 2 => 2, 0
        */
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotatedKey[j][n - i - 1] = key[i][j];
            }
        }
        return rotatedKey;
    }
    
    public boolean solution(int[][] key, int[][] lock) {
        /*
        자몰쇠 모든 홈 채워야함
        
        */
        int m = key.length, n = lock.length;
        
        boolean answer = false;
        int[][] usedKey = key;
        
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                if (lock[i][j] == 0) count++;
        }
        
        for (int i = 0; i < 4; i++) {
            //90도 씩 회전
            if (i != 0)
                usedKey = rotateKey(usedKey);
            
            //검사
            search1: for (int h = -m + 1; h < n; h++) {
                search2: for (int w = -m + 1; w < n; w++) {
                    int correctNum = 0;
                    search3: for (int dy = 0; dy < m; dy++) {
                        search4: for (int dx = 0; dx < m; dx++) {
                            int cy = h + dy, cx = w + dx;
                            if (cy < 0 || cx < 0 || cy >= n || cx >= n) continue;
                            if (usedKey[dy][dx] == lock[cy][cx]) continue search2;
                            if (usedKey[dy][dx] == 1) {
                                // System.out.println(dy + " " + dx + " " + cy + " " + cx);
                                    correctNum++;
                            }
                        }
                    }
                    // System.out.println(count + " " + correctNum);
                    // System.out.println();
                    if (correctNum == count) {
                        answer = true;
                        return answer;
                    }
                }
            }
        }
        return answer;
    }
}