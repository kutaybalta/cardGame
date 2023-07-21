import java.sql.SQLOutput;
import java.util.*;

public class Game {
    private int playerCount;
    public boolean isClockwise = true;
    private int currentPlayerIndex = 0;
    private int drawCount = 0;
    private String currentColor;
    private List<Player> players = new ArrayList<>();
    private List<Card> deck = new ArrayList<>();
    private List<Card> cardsInMiddle = new ArrayList<>();

    public Game(int playerCount) {
        this.playerCount = playerCount;
        this.deck = Card.getStandartDeck();
        for (int i = 0; i < playerCount; i++) {
            players.add(Player.createPlayer());
            for (int j = 0; j < 7; j++) {
                players.get(i).getCards().add(Card.getRandomCardFromDeck(this.deck));
            }
        }
        Card firstCard;
        do {
            firstCard = Card.getRandomCardFromDeck(this.deck);
            cardsInMiddle.add(firstCard);
        }while (firstCard.type() != Card.Ctype.NORMAL);
        this.currentColor = getCardInMiddle().getColor();
    }

    public static void startGame() {
        Game game = new Game(Player.askCount());
        do {
            game.printGameStats();
            game.printPlayerStats();
            boolean isPlayed = false;
            do {
                isPlayed = game.playCard(game.askUserCardIndex());
            }while (!isPlayed);
        }while (!game.hasSomeoneWon());
    }

    public int askUserCardIndex() {
        Scanner scanner = new Scanner(System.in);
        int index = 0;
        boolean endLoop = false;
        do {
            System.out.println("Please give card index between 0-" + (players.get(currentPlayerIndex).getCards().size()-1) );
            try {
                index = scanner.nextInt();
            }catch (InputMismatchException ime) {
                System.out.println("Pleas give number");
                scanner.nextLine();
                continue;
            }

            if (index < 0 || index >= players.get(currentPlayerIndex).getCards().size()) {
                System.out.println("Invalid card index");
            }
            else {
                endLoop = true;
            }
        }while (!endLoop);
        return index;
    }

    public Card getCardInMiddle() {
        return cardsInMiddle.get(cardsInMiddle.size()-1);
    }
    public void updateTurn() {
        if (isClockwise) {
            currentPlayerIndex++;
            currentPlayerIndex = currentPlayerIndex == playerCount ? 0 : currentPlayerIndex;
        }
        else {
            currentPlayerIndex--;
            currentPlayerIndex = currentPlayerIndex < 0 ? playerCount-1 : currentPlayerIndex;
        }
    }
    public boolean hasSomeoneWon() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getCards().size() == 0) {
                System.out.printf("Player %d has won", i+1);
                return true;
            }
        }
        return false;
    }
    public void playCardToMiddle(Card card) {
        cardsInMiddle.add(card);
        players.get(currentPlayerIndex).getCards().remove(card);
    }

    public boolean playCard(int index) {
        Card card = players.get(currentPlayerIndex).getCards().get(index);
        if (doCardsMatchs(getCardInMiddle(), card)) {
            if (card.type() == Card.Ctype.PLUSFOUR) {
                drawCount += 4;
                playCardToMiddle(card);
                currentColor = Card.askColor();
            }
            else if (card.type() == Card.Ctype.COLORCHANGE) {
                playCardToMiddle(card);
                currentColor = Card.askColor();
            }
            else if (card.type() == Card.Ctype.PLUSTWO) {
                drawCount += 2;
                playCardToMiddle(card);
                currentColor = card.getColor();
            }
            else if (card.type() == Card.Ctype.REVERSE) {
                playCardToMiddle(card);
                isClockwise = !isClockwise;
                currentColor = card.getColor();
            }
            else if (card.type() == Card.Ctype.BLOCK) {
                playCardToMiddle(card);
                currentColor = card.getColor();
                updateTurn();
            }
            else {
                playCardToMiddle(card);
                currentColor = card.getColor();
            }
            updateTurn();
            return true;
        }
        else {
            if (drawCount != 0) {
                System.out.println("You cant play this card on plus cards");
                return false;
            }
            System.out.println("You cant play this card");
            return false;
        }
    }

    public void printGameStats() {
        System.out.println("----------------------------------------");
        System.out.printf("Current color is %s and current card is %s\n", currentColor, getCardInMiddle());
        for (int i = 0; i < playerCount; i++) {
            if (i == currentPlayerIndex){continue;}
            System.out.printf("Player-%d : %d cards || ", i+1, players.get(i).getCards().size());
        }
        System.out.println();
    }
    public void printPlayerStats() {
        System.out.printf("Player %d has %d cards and has to draw %d cards\n",
                currentPlayerIndex+1, players.get(currentPlayerIndex).getCards().size()
                    , drawCount);
        System.out.printf("Player %d's cards are : \n", currentPlayerIndex+1);
        printPlayersDeck();
        System.out.println("Playable cards are : ");
        System.out.println(getPlayableCards());
    }
    public void printPlayersDeck() {
        var playersDeck = players.get(currentPlayerIndex).getCards();
        for (int i = 0; i < playersDeck.size(); i ++) {
            System.out.printf("%d)%s  ",i, playersDeck.get(i));
        }
        System.out.println();
    }
    public List<Card> getPlayableCards() {
        List<Card> playableCards = new ArrayList<>();
        for (Card card : players.get(currentPlayerIndex).getCards()) {
            if (doCardsMatchs(getCardInMiddle(), card)) {
                playableCards.add(card);
            }
        }
        return playableCards;
    }
    public boolean doCardsMatchs(Card card1, Card card2) {
        if (card1.type() == Card.Ctype.PLUSFOUR) {
            if (card2.type() == Card.Ctype.PLUSFOUR ) {
                return true;
            }
            else if (card2.type() == Card.Ctype.PLUSTWO && card2.getColor().equals(currentColor)) {
                return true;
            }
            else if (drawCount == 0) {
                return card2.getColor().equals(currentColor) || card2.type() == Card.Ctype.COLORCHANGE;
            }
            return false;
        }
        else if (card1.type() == Card.Ctype.COLORCHANGE) {
            return card2.getColor().equals(currentColor) || card2.type() == Card.Ctype.PLUSFOUR ||
                    card2.type() == Card.Ctype.COLORCHANGE;
        }
        else if (card1.type() == Card.Ctype.PLUSTWO) {
            if (card2.type() == Card.Ctype.PLUSTWO || card2.type() == Card.Ctype.PLUSFOUR) {
                return true;
            }
            else if (drawCount == 0) {
                return card2.getColor().equals(currentColor) || card2.type() == Card.Ctype.COLORCHANGE;
            }
            return false;
        }
        else if (card1.type() == Card.Ctype.REVERSE || card1.type() == Card.Ctype.BLOCK) {
            return card2.getColor().equals(currentColor) || card2.type() == card1.type()|| card2.type() == Card.Ctype.PLUSFOUR ||
                    card2.type() == Card.Ctype.COLORCHANGE;
        }
        else {
            return card1.getColor().equals(card2.getColor()) || card1.number() == card2.number() ||
                                    card2.type() == Card.Ctype.COLORCHANGE || card2.type() == Card.Ctype.PLUSFOUR;
        }
    }

}
