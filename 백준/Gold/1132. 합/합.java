import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    static class Alphabet implements Comparable<Alphabet> {
        long weight = 0;
        long value = -1;
        boolean zeroFlag = true;

        @Override
        public int compareTo(Alphabet o) {
            return Long.compare(this.weight, o.weight);
        }
    }

    static String[] nums;
    static Alphabet[] alphabets = new Alphabet[10];

    public static void main(String[] args) throws IOException {
        /*
         * 2억 이하
         * 정수형 400만개
         *
         *
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        nums = new String[n];
        for (int i = 0; i < n; i++) nums[i] = br.readLine();

        for (int i = 0; i < 10; i++) {
            alphabets[i] = new Alphabet();
        }

        //가중치 추가
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length(); j++) {

                Alphabet alphabet = alphabets[nums[i].charAt(j) - 'A'];
                if (j == 0) {
                    alphabet.zeroFlag = false;
                }
                long w = 1;
                for (int k = 0; k < nums[i].length() - 1 - j; k++) {
                    w *= 10;
                }
                alphabet.weight += w;
            }
        }

        HashMap<Character, Alphabet> alphabetHashMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            alphabetHashMap.put((char) ('A' + i), alphabets[i]);
        }
        //0 선택
        Arrays.sort(alphabets);
        for (Alphabet alphabet: alphabets) {
            if (alphabet.zeroFlag) {
                alphabet.value = 0;
                break;
            }
        }

        //나머지 숫자 선택
        int current = 1;
        for (Alphabet alphabet: alphabets) {
            if (alphabet.value != -1) continue;
            alphabet.value = current++;
        }

        //숫자 변환 및 합 연산
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            long num = 0;
            for (int j = 0; j < nums[i].length(); j++) {
                Alphabet alphabet = alphabetHashMap.get(nums[i].charAt(j));
                long w = 1;
                for (int k = 0; k < nums[i].length() - 1 - j; k++) {
                    w *= 10;
                }
                num += alphabet.value * w;
            }
            sum += num;
        }

        System.out.println(sum);
    }
}