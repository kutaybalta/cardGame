import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Player {
    private List<Card> playerCards = new ArrayList<>();
    private boolean hadDrawOneCard = false;

    public static int askCount() {
        Scanner scanner = new Scanner(System.in);
        int playerCount = 0;
        boolean endLoop = false;

        do {
            System.out.println("How many players are going to play 2-4 :");
            try {
                playerCount = scanner.nextInt();
            }catch (InputMismatchException ime) {
                System.out.println("Please give numbers");
                scanner.nextLine();
                continue;
            }

            if (playerCount < 2 || playerCount > 4) {
                System.out.println("Player count must be between 2 and 4");
            }
            else {
                endLoop = true;
            }
        }while (!endLoop);
        return playerCount;
    }

    public static Player createPlayer() {
        return new Player();
    }

    public List<Card> getCards() {
        return playerCards;
    }

}
