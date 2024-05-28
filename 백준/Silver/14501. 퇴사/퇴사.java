import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        FastInput fi = new FastInput();
        int n =  fi.nextInt();
        long[] arr = new long[n+1];
        long [] tArr = new long[n+1];
        long [] pArr = new long[n+1];
        
        for (int i = 1; i <= n; i++) {
            tArr[i] = fi.nextInt();
            pArr[i] = fi.nextInt();
            arr[i] = pArr[i];
        }
        
        //앞부터 탐색
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                if (i - j >= tArr[j]) {
                    arr[i] = Math.max(arr[i], pArr[i] + arr[j]);
                }
            }
        }
        long result = 0;
        for (int i = 1; i <= n; i++) {
            if (i + tArr[i] < n+2) {
                result = Math.max(result, arr[i]);
            }
        }
        System.out.println(result);
    }
    
    static class FastInput {
        BufferedReader br;
        StringTokenizer st;
        
        public FastInput() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        
        String next() {
            while(st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        
        public int nextInt() {
            return Integer.parseInt(next());
        }
        
    }
}
