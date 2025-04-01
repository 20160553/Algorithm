import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

class Box {
    private int from;
    private int to;
    private int amount;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Box(int from, int to, int amount) {
        super();
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}

public class Main {

    static int n, c, m, ans;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ans = 0;
        n = sc.nextInt();
        c = sc.nextInt();
        m = sc.nextInt();

        ArrayList<Box> arr = new ArrayList<>();
        
        for (int i = 0; i < m; i++) {
            arr.add(new Box(sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }
        arr.sort(new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                if (o1.getTo() != o2.getTo()) {
                    return o1.getTo() - o2.getTo();
                } else 
                    return o1.getFrom() - o2.getFrom();
            }
        });
        
        int[] w = new int[n + 1];
        
        for (int i = 1; i <= n; i++)
            w[i] = c;
        
        for (int i = 1; i <= m; i++) {
            Box d = arr.get(i - 1);
            int maxBox = Integer.MAX_VALUE;
            
            for (int j = d.getFrom(); j < d.getTo(); j++) {
                maxBox = Math.min(maxBox, w[j]);
            }
            
            if (maxBox >= d.getAmount()) {
                for (int j = d.getFrom(); j < d.getTo(); j++) {
                    w[j] -= d.getAmount();
                }
                ans += d.getAmount();
            } else {
                for (int j = d.getFrom(); j < d.getTo(); j++) {
                    w[j] -= maxBox;
                }
                ans += maxBox;
            }
            
            
        }
        
        System.out.println(ans);
    }

}