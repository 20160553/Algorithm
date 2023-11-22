import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static class SegmentTree {
        int minValue, start, end;
        SegmentTree left = null, right = null;

        public SegmentTree(int start, int end, int[] arr) {
            this.start = start;
            this.end = end;

            if (start == end)
                minValue = arr[start - 1];
            else {
                int middleNum = (start + end) / 2;
                left = new SegmentTree(start, middleNum, arr);
                right = new SegmentTree(middleNum + 1, end, arr);
                minValue = Math.min(left.minValue, right.minValue);
            }
        }

        public int replace(int position, int newNum) {
            if (this.end < position || this.start > position)
                return this.minValue;

            if (this.start == this.end)
                return this.minValue = newNum;
            this.minValue = Math.min(this.left.replace(position, newNum), this.right.replace(position, newNum));
            return this.minValue;
        }

        public int getMin(int start, int end) {
            if (this.end < start || this.start > end)
                return Integer.MAX_VALUE;
            if (start <= this.start && this.end <= end)
                return this.minValue;
            return Math.min(left.getMin(start, end), right.getMin(start, end));
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] arr = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int m = Integer.parseInt(br.readLine());

        SegmentTree root = new SegmentTree(1, n, arr);
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int command, a, b;

            command = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            if (command == 1) {
                root.replace(a, b);
            } else
                sb.append(root.getMin(a, b) + "\n");
        }

        System.out.println(sb);

    }
}