import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

    private static ArrayList<LinkedList<Integer>> getSequence(int[][] arr) {

        int n = arr.length;
        int m = arr[0].length;
        int i = 0;
        ArrayList<LinkedList<Integer>> sequences = new ArrayList<>();
        while (i < n / 2 && i < m / 2) {
            LinkedList<Integer> sequence = new LinkedList<>();
            sequences.add(sequence);

            if (i < n / 2) {
                for (int j = i; j < m - i - 1; j++) {
                    sequence.add(arr[i][j]);
                }
            }

            if (i < m / 2) {
                for (int j = i; j < n - i - 1; j++) {
                    sequence.add(arr[j][m - i - 1]);
                }
            }

            if (i < n / 2) {
                for (int j = m - i - 1; j > i; j--) {
                    sequence.add(arr[n - i - 1][j]);
                }
            }

            if (i < m / 2) {
                for (int j = n - i - 1; j > i; j--) {
                    sequence.add(arr[j][i]);
                }
            }
            i++;
        }

        return sequences;
    }

    private static void turn(int r, ArrayList<LinkedList<Integer>> sequences) {
        for (int i = 0; i < sequences.size(); i++) {
            LinkedList<Integer> sequence = sequences.get(i);
            int tempR = r % sequence.size();

            for (int j = 0; j < tempR; j++) {
                sequence.addLast(sequence.pollFirst());
            }
        }
    }

    private static int[][] getResult(int[][]  arr, ArrayList<LinkedList<Integer>> sequences) {
        int[][] result = new int[arr.length][arr[0].length];

        int n = arr.length;
        int m = arr[0].length;
        for (int i = 0; i < sequences.size(); i++) {
            LinkedList<Integer> sequence = sequences.get(i);

            if (i < n / 2) {
                for (int j = i; j < m - i - 1; j++) {
                    result[i][j] = sequence.poll();
                }
            }
            if (i < m / 2) {
                for (int j = i; j < n - i - 1; j++) {
                    result[j][m - i - 1] = sequence.poll();
                }
            }

            if (i < n / 2) {
                for (int j = m - i - 1; j > i; j--) {
                    result[n - i - 1][j] = sequence.poll();
                }
            }

            if (i < m / 2) {
                for (int j = n - i - 1; j > i; j--) {
                    result[j][i] = sequence.poll();
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n, m, r;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        int[][] arr = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        ArrayList<LinkedList<Integer>> sequences = getSequence(arr);
        turn(r, sequences);
        arr = getResult(arr, sequences);

        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                answer.append(arr[i][j] + " ");
            }
            answer.append("\n");
        }

        System.out.println(answer);
        br.close();
    }
}