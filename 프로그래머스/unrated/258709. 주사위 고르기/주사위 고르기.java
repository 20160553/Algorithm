import java.util.*;

class Answer {
    double winRate = 0.0;
    int[] winDices;
    
    public Answer(int n) {
        winDices = new int[n / 2];
    }
}

class Solution {
    
    private int rollDice(int idx, int num, int score, int[][] dice, int[] rolledDice, int[] result) {
        if (num >= rolledDice.length) {
            result[idx++] = score;
            return idx;
        }
        for (int i = 0; i < 6; i++) {
            idx = rollDice(idx, num + 1, score + dice[rolledDice[num]][i], dice, rolledDice, result);
        }
        return idx;
    }
        
    private void selectMyDice(int currentIdx, int selectedNum, int[][] dice, boolean[] diceOwners, Answer answer) {
        int n = dice.length;
        if (selectedNum == n / 2) { // n개 다 선택
            
            int diceCaseNum = 1;
            
            for (int i = 0; i < n/2; i++)
                diceCaseNum *= 6;
            
            int[] myDices = new int[n / 2];
            int[] yourDices = new int[n / 2];
            int[] myScores = new int[diceCaseNum];
            int[] yourScores = new int[diceCaseNum];
            
            int myNum = 0, yourNum = 0;
            for (int i = 0; i < n; i++) {
                if (diceOwners[i])
                    myDices[myNum++] = i;
                else
                    yourDices[yourNum++] = i;
            }
            
            rollDice(0, 0, 0, dice, myDices, myScores);
            rollDice(0, 0, 0, dice, yourDices, yourScores);
            
//             int winCnt = 0;
//             for (int i = 0; i < myScores.length; i++) {
//                 for (int j = 0; j < yourScores.length; j++) {
//                     if (myScores[i] > yourScores[j])
//                         winCnt++;
//                 }
//             }
            
            Arrays.sort(myScores);
            Arrays.sort(yourScores);
            
            int winCnt = 0;
            int preWinCnt = 0;
            for (int i = 0; i < myScores.length; i++) {
                int left, mid, right;
                left = 0;
                right = yourScores.length - 1;
                mid = (left + right) / 2;
                
                if (i > 0 && myScores[i - 1] == myScores[i]) {
                    winCnt += preWinCnt;
                    continue;
                }
                
                while (left <= right) {
                    mid = (left + right) / 2;
                    if (myScores[i] <= yourScores[mid]) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
                
                winCnt += left;
                preWinCnt = left;
            }
            
            double winRate = (double)winCnt / myScores.length;
            if (answer.winRate < winRate) {
                answer.winRate = winRate;
                answer.winDices = myDices;
            }
            return;
        }
        if (dice.length - currentIdx + selectedNum < n / 2) {
            return;
        }
        
        //선택 x
        selectMyDice(currentIdx + 1, selectedNum, dice, diceOwners, answer);
        //현재 주사위 선택
        diceOwners[currentIdx] = true;
        selectMyDice(currentIdx + 1, selectedNum + 1, dice, diceOwners, answer);
        diceOwners[currentIdx] = false;
    }
    
    public int[] solution(int[][] dice) {
        
        /*
        주사위 변 수 : 6
        주사위 개수 : 2 <= n <= 10
        주사위 나누는 경우 : 2 ^ 10 = 1024 or 10 C 5 = 252
        주사위 눈 조합 경우의 수 : 6 ^ 10 = 60_466_176 (6천만)
        */
        
        int n = dice.length;
        
        boolean[] diceOwners = new boolean[n];
        Answer answer = new Answer(n);
        selectMyDice(0, 0, dice, diceOwners, answer);
        
        for (int i = 0; i < answer.winDices.length; i++) {
            answer.winDices[i]++;
        }
        
        return answer.winDices;
    }
}