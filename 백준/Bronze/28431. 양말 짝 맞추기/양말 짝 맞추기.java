import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashSet<Integer> set = new HashSet();

        Scanner sc = new Scanner(System.in);
        int a;
        for (int i = 0; i < 5; i++) {
            a = sc.nextInt();
            if (set.contains(a))
                set.remove(a);
            else
                set.add(a);
        }

        for (Integer i: set) {
            System.out.println(i);
        }
    }
}
