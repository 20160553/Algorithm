import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    private static class SegmentTree {
        int start, end;
        long sum;
        SegmentTree left = null, right = null;

        public SegmentTree(int start, int end, long[] arr) {
            this.start = start;
            this.end = end;
            if (start == end) {
                this.sum = arr[start - 1];
            } else {
                int middle = (start + end) / 2;
                this.left = new SegmentTree(start, middle, arr);
                this.right = new SegmentTree(middle + 1, end, arr);
                this.sum = this.left.sum + this.right.sum;
            }
        }

        public long getSum(int start, int end) {
            if (this.start > end || this.end < start) {
                return 0;
            }
            if (start <= this.start && this.end <= end) {
                return this.sum;
            }
            return this.left.getSum(start, end) + this.right.getSum(start, end);
        }

        public long changeValue(int idx, long afterChangedNum) {
            if (this.end < idx || this.start > idx) {
                return this.sum;
            }
            if (this.start == idx && this.start == this.end) {
                this.sum = afterChangedNum;
                return this.sum;
            }
            return this.sum = this.left.changeValue(idx, afterChangedNum) + this.right.changeValue(idx, afterChangedNum);
        }

    }

    public static void main(String[] args) throws IOException {
        /*
        2초 : 2억 연산 이하

        n, q <= 10만 자연수

        한 턴에 매번 합을 구하는 경우 => O(N^2) => 10만 ^ 2 => 10^10 => 10_000_000_000 : 100억 연산

        순열의 각 수는 Inter 범위임으로 sum의 자료형은 long

        푸는 방법
        1. 세그먼트 트리
        2. 누적 히스토그램

        변경이 잦으므로 세그먼트 트리가 더 유리할 것 같다.

        세그먼트 트리의 시간복잡도 => NlogN => 50만 정도인가?


        * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n, q;
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        long[] arr = Arrays.stream(in.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        SegmentTree root = new SegmentTree(1, arr.length, arr);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            int x, y, a;
            long b;
            st = new StringTokenizer(in.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            if (x > y) {
                int temp = x;
                x = y;
                y = temp;
            }
            a = Integer.parseInt(st.nextToken());
            b = Long.parseLong(st.nextToken());

            sb.append(root.getSum(x, y) + "\n");
            root.changeValue(a, b);
        }
        System.out.println(sb);
    }

}