import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();
        String input = null;
        while ((input = br.readLine()) != null) {
            int cnt = 1;
            int n = Integer.parseInt(input.trim());
            int[] arr = new int[n];
            int currentSize = 0;

            st = new StringTokenizer(br.readLine());
            arr[0] = Integer.parseInt(st.nextToken());
            currentSize++;

            for (int i = 1; i < n; i++) {
                int next = Integer.parseInt(st.nextToken());

                if (arr[currentSize - 1] < next) { // 만약 최장부분수열 마지막이 더 작은 경우 맨 뒤에 추가해주고 이어나감
                    arr[currentSize++] = next;
                } else {
                    int left = 0;
                    int right = currentSize - 1;
                    int middle = 0;
                    //이진탐색을 통해 next보다 작은 것 중 제일 큰 것 찾음
                    while (left <= right) {
                        middle = (left + right) / 2;
                        if (arr[middle] < next) {
                            left = middle + 1;
                        } else if (arr[middle] == next) {
                            break;
                        } else {
                            right = middle - 1;
                        }
                    }
                    arr[left] = next;
                }
            }

            answer.append(currentSize + "\n");
        }

        System.out.println(answer);

    }
}