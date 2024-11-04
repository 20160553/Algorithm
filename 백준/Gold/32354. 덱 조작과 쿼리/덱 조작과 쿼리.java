import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    static class Deck {
        long sum = 0;
        int previousDeckStatus = 0;
        int currentDeckStatus = 0;

        public Deck(long sum, int previousDeckStatus, int currentDeckStatus) {
            this.sum = sum;
            this.previousDeckStatus = previousDeckStatus;
            this.currentDeckStatus = currentDeckStatus;
        }

        public Deck(Deck deck) {
            this.sum = deck.sum;
            this.previousDeckStatus = deck.previousDeckStatus;
            this.currentDeckStatus = deck.currentDeckStatus;
        }

        public void copy(Deck deck) {
            this.sum = deck.sum;
            this.previousDeckStatus = deck.previousDeckStatus;
            this.currentDeckStatus = deck.currentDeckStatus;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        long sum = 0;

        ArrayList<Deck> decks = new ArrayList<>();
        decks.add(new Deck(0, 0, 0));

        for (int i = 1; i <= n; i++) {
            String[] str = br.readLine().split(" ");
            String cmd = str[0];
            Deck currentDeck = new Deck(decks.get(i - 1));
            int num = 0;
            if (str.length > 1)
                num = Integer.parseInt(str[1]);

            switch (cmd) {
                case "print":
                    sb.append(currentDeck.sum + "\n");
                    break;
                case "push":
                    currentDeck.sum += num;
                    currentDeck.previousDeckStatus = currentDeck.currentDeckStatus;
                    currentDeck.currentDeckStatus = i;
                    break;
                case "pop":
                    currentDeck = decks.get(currentDeck.previousDeckStatus);
                    break;
                case "restore":
                    currentDeck = decks.get(num);
                    break;
            }
            decks.add(currentDeck);
        }


        System.out.println(sb);
    }
}