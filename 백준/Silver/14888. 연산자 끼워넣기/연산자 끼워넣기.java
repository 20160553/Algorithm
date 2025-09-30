import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int min = Integer.MAX_VALUE;
    static int max = Integer.MIN_VALUE;

    public static void makeExpression(int totalCnt, int[] nums, int[] operationCnts, int index, String str) {
        if (index == nums.length || index == nums.length - 1) {
            if (totalCnt == 0)
                solve(nums, str);
            return;
        }

        char operation;
        for (int i = 0; i < 4; i++) {
            if (operationCnts[i] < 1) continue;
            if (i == 0) operation = '+';
            else if (i == 1) operation = '-';
            else if (i == 2) operation = '*';
            else operation = '/';

            operationCnts[i]--;
            makeExpression(totalCnt - 1, nums, operationCnts, index + 1, str + operation);
            operationCnts[i]++;
        }
    }

    public static void solve(int[] nums, String str) {
        Stack<Integer> numStack = new Stack<>();
        Stack<Character> operation = new Stack<>();

        int num = nums[0];
        int numIndex = 1;
        for (int i = 0; i < str.length(); i++) {
            char p = str.charAt(i);

            if (p == '*') {
                num *= nums[numIndex++];
            } else if (p == '/') {
                num /= nums[numIndex++];
            } else if (p == '+') {
                num += nums[numIndex++];
            } else if (p == '-') {
                num -= nums[numIndex++];
            }
        }
        max = Math.max(max, num);
        min = Math.min(min, num);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /*
         * 조합
         * 경우의 수 : 9! = 362880
         *
         * 1.
         * 곱셈, 나눗셈 먼저
         * 덧셈, 뺄셈 나중에
         *
         * 2. 순서 상관 없이 조합
         * 2 - 2) 계산
         *
         * stack 2개 사용
         * 1. 숫자 스택
         * 2. 연산자 스택
         *
         * 1. 숫자 먼저 스택에 적재
         * 2. 연선자 적재
         * 3. 연산자 곱셈 / 나눗셈인 경우 다시 뺌
         * 4. 가장 위 숫자 뺌
         * 5. 다음 숫자랑 계산 후 다시 적재
         *
         * 더 이상 적재할 게 없을 경우
         * 하나씩 빼면서 계산
         *
         * */

        int n, totalCnt = 0;
        n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());

        int[] nums = new int[n];
        int[] operationCnts = new int[4];

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 4; i++) {
            operationCnts[i] = Integer.parseInt(st.nextToken());
            totalCnt += operationCnts[i];
        }

        makeExpression(totalCnt, nums, operationCnts, 0, "");

        StringBuilder answer = new StringBuilder();
        answer.append(max + "\n");
        answer.append(min);

        System.out.println(answer);
        br.close();
    }
}