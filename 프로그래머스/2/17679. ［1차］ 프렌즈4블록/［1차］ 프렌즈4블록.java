import java.util.*;

class Solution {
    
    char[][] gameboard;
    LinkedList<int[]> deletedBlockList = new LinkedList<int[]>();
    private int popBlock(int m, int n) {
        int count = 0;
        deletedBlockList.clear();
        for (int i = 0; i < m - 1; i++) {
            for (int j = 0; j < n - 1; j++){
                if (
                    gameboard[i][j] != '.' &&
                    gameboard[i][j] == gameboard[i][j + 1] &&
                    gameboard[i][j] == gameboard[i + 1][j] &&
                    gameboard[i][j] == gameboard[i + 1][j + 1]
                )  
                {
                    deletedBlockList.add(new int[] {i,j}); 
               }
            }
        }
        for (int[] pos: deletedBlockList) {
            if (gameboard[pos[0]][pos[1]] != '.')
                count++;
            if (gameboard[pos[0] + 1][pos[1]] != '.')
                count++;
            if (gameboard[pos[0]][pos[1] + 1] != '.')
                count++;
            if (gameboard[pos[0] + 1][pos[1] + 1] != '.')
                count++;
            
            gameboard[pos[0]][pos[1]] = '.';
            gameboard[pos[0]+1][pos[1]] = '.';
            gameboard[pos[0]][pos[1]+1] = '.';
            gameboard[pos[0]+1][pos[1]+1] = '.';
        } 
        return count;
    }
    
    private void dropBlock(int m, int n) {
        roop1: for (int j = 0; j < n; j++) {
            roop2: for (int i = m - 1; i > -1; i--) {
                if (gameboard[i][j] != '.') continue;
                for (int k = i - 1; k > -1; k--) {
                    if (gameboard[k][j] != '.') {
                        gameboard[i][j] = gameboard[k][j];
                        gameboard[k][j] = '.';
                        break;
                    }
                    if (k == 0)
                        break roop2;
                }
            }
        }
    }
    
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        /*
        1. 지워지는 거 체크해 리스트에 저장
        2. 블록 삭제
        3. 블록 하강
        '.'은 지워진 블럭
        */
        
        gameboard = new char[m][n]; // java는 문자열 직접 수정 불가
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                gameboard[i][j] = board[i].charAt(j);
        int result = 0;
        while((result = popBlock(m, n)) > 0)  {
            answer += result;
            dropBlock(m, n);
        }
        
        return answer;
    }
}