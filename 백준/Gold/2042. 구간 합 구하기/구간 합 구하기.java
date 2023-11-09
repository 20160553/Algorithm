import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static class SegmentTree {
        long value;
        int startIdx, endIdx;
        SegmentTree left, right;

        public SegmentTree(int startIdx, int endIdx, long[] arr) {
            this.startIdx = startIdx;
            this.endIdx = endIdx;

            if (startIdx == endIdx) {
                this.value = arr[startIdx];
                this.left = null;
                this.right = null;
            } else {
                int middleIdx = (startIdx + endIdx) / 2;
                this.left = new SegmentTree(startIdx, middleIdx, arr);
                this.right = new SegmentTree(middleIdx + 1, endIdx, arr);
                this.value = this.left.value + this.right.value;
            }
        }

        public void replace(long idx, long newValue) {
            if (idx < this.startIdx || this.endIdx < idx)
                //범위에 없을 경우
                return;

            if (startIdx == idx && idx == endIdx) {
                //단말 노드이고 찾던 노드인 경우 교체
                this.value = newValue;
            } else {
                this.left.replace(idx, newValue);
                this.right.replace(idx, newValue);
                this.value = this.left.value + this.right.value;
            }
        }

        public long getSum(long start, long end) {
            if (end < this.startIdx || this.endIdx < start)
                //범위에 없을 경우
                return 0;
            if (this.startIdx == start && this.endIdx == end) {
                return this.value;
            }
            int middleIdx = (this.startIdx + this.endIdx) / 2;
            if (end <= middleIdx) {
                return this.left.getSum(start, end);
            }
            if (start > middleIdx) {
                return this.right.getSum(start, end);
            }
            return this.left.getSum(start, middleIdx) + this.right.getSum(middleIdx + 1, end);
        }

    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int n, m, k;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        long[] arr = new long[n+1];

        for (int i = 1; i <= n; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        SegmentTree segmentTree = new SegmentTree(1, n, arr);

        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(br.readLine());

            long a, b, c;

            a = Long.parseLong(st.nextToken());
            b = Long.parseLong(st.nextToken());
            c = Long.parseLong(st.nextToken());

            if (a == 1) {
                segmentTree.replace(b, c);
            } else {
                sb.append(segmentTree.getSum(b, c)+"\n");
            }
        }

        System.out.println(sb);
    }
}