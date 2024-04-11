import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static class FastIn {
        BufferedReader br = null;
        StringTokenizer st = null;

        public FastIn() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            if (st == null || !st.hasMoreElements()) {
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

    public static void main(String[] args) throws IOException {
        FastIn sc = new FastIn();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        PriorityQueue<Integer> leftPQ = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> rightPQ = new PriorityQueue<>();
        int n = sc.nextInt();
        int middleNum = 10_001;
        for (int i = 1; i <= n; i++) {
            int newNum = sc.nextInt();
            if (i == 1) {
                leftPQ.add(newNum);
            } else {
                if (middleNum > newNum) {
                    leftPQ.add(newNum);
                } else
                    rightPQ.add(newNum);
                int diff = leftPQ.size() - rightPQ.size();
                if (diff > 1) {
                    rightPQ.add(leftPQ.poll());
                } else if (diff == -1) {
                    leftPQ.add(rightPQ.poll());
                }
            }
            middleNum = leftPQ.peek();
            bw.write(middleNum + "\n");
        }
        bw.flush();
        bw.close();
    }

}