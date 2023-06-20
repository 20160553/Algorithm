import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static class FastIn {
        BufferedReader br;
        StringTokenizer st;

        FastIn() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
    
    static int n, ans;

    public static void main(String[] args) {

        FastIn sc = new FastIn();

        n = sc.nextInt();
        ans = 0;
 
        PriorityQueue<int []> dayQ = new PriorityQueue<int[]>(new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });
        PriorityQueue<int []> scoreQ = new PriorityQueue<>(new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });
        
        for (int i = 1; i <= n; i++) {
            dayQ.add(new int[] {sc.nextInt(), sc.nextInt()});
        }
        
        for (int j = n; j >= 1; j--) {
            while (!dayQ.isEmpty()) {
                if (dayQ.peek()[0] >= j) {
                    scoreQ.add(dayQ.poll());
                } else {
                    break;
                }
            }
            if (!scoreQ.isEmpty())
                ans += scoreQ.poll()[1];
        }
        
        System.out.println(ans);
    }

}