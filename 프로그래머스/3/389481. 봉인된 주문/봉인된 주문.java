import java.util.*;
import java.io.*;

class Solution {
    
    long[] dp = new long[12];
    String solve(int length, long n, long first, String str) {
        if (length == 1)
            return str + (char)('a' + (n - first));

        long left = first;
        int idx = 0;
        long nextN = n;
        while (n >= left + dp[length - 1]) {
            left += dp[length - 1];
            idx++;
        }

        str += (char)('a' + (idx));

        return solve(length - 1, nextN, left, str);
    }
    
    String getAfterString(String str) {
        boolean flag = true;
        
        String answer = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (flag) {
                if (c == 'z') {
                    flag = true;
                    c = 'a';
                }
                else {
                    flag = false;
                    c++;
                }
            }
            answer = c + answer;
            
        }
        if (flag) answer = 'a' + answer;
        return answer;
    }
    
    public String solution(long n, String[] bans) {
        /*
        n번째 문자열을 구하라...
        a ~ z => 26개
        a + a ~ z => 26
        b + a ~ z => 26..
        
        1. 빼지 않았을 때 문자열 경우의 수
         => 1: 26
            2: 26^2
            3: 26^3
            4: 26^4
            
        1. 길이구하기
        2. 아무것도 안 뺐을 경우 문자열 구하기
        3. 그 문자열보다 앞에 있는 뺄 문자열 개수 구하기
        4. 그만큼 뒤인 문자열 구하기
        */
        
        String answer = "";
        int length = 1;
        int ALPHABET_SIZE = 26;
        long _n = n;
        long first = 1;
        long caseCnt = ALPHABET_SIZE;
        
        dp[0] = 0;
        dp[1] = ALPHABET_SIZE;
        for (int i = 1; i < dp.length - 1; i++) {
            dp[i + 1] = dp[i] * ALPHABET_SIZE;
        }
        
        while (_n > caseCnt) {
            _n -= caseCnt;
            first += caseCnt;
            caseCnt *= ALPHABET_SIZE;
            length++;
        }
        
        String str = solve(length, n, first, "");
        Comparator<String> c = (s1, s2) -> {
            if (s1.length() == s2.length()) {
                int result = 1;
                for (int i = 0; i < s1.length(); i++) {
                    if (s1.charAt(i) == s2.charAt(i))
                        continue;
                    return s1.charAt(i) - s2.charAt(i);
                }
                return 0;
            }
            return s1.length() - s2.length();
        };
        Arrays.sort(bans, c);
        int cuttedSize = Arrays.binarySearch(bans, str, c);
        if (cuttedSize < 0) {
            cuttedSize = -cuttedSize - 1;
        } else {
            cuttedSize++;
        }
        int cnt = 0;

        while (cuttedSize - cnt > 0) {
            for (int i = 0; i < cuttedSize - cnt; i++) {
                str = getAfterString(str);
            }
            cnt += cuttedSize - cnt;
            cuttedSize = Arrays.binarySearch(bans, str, c);
            if (cuttedSize < 0) {
                cuttedSize = -cuttedSize - 1;
            } else cuttedSize++;
        }

        answer = str;
        
        return answer;
    }
}