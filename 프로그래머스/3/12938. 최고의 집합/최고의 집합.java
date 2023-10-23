class Solution {
    public int[] solution(int n, int s) {
        int[] answer = {};
        
        /*
        a + b + ... + n = S
        a * b가 최대
        */
        
        int oneInN = s/n;
        if (s < n) answer = new int[] {-1};
        else {
            answer = new int[n];
            if (s % n == 0)
                for (int i = 0; i < n; i++) 
                    answer[i] = oneInN;
            else {
                for (int i = 0; i < n - s % n; i++)
                    answer[i] = oneInN;
                for (int i = n - s % n; i < n; i++) 
                    answer[i] = oneInN + 1;
            }
        }
        return answer;
    }
}