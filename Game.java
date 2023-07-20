import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class Game {
    private int playerCount;
    public boolean hasSomeoneWon = false;
    public boolean isClockwise = true;
    private int currentPlayerIndex = 0;
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

    public Card getCardInMiddle() {
        return cardsInMiddle.get(cardsInMiddle.size()-1);
    }

    public void printGameStats() {
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
                    , players.get(currentPlayerIndex).getDrawCount());
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
            else if (players.get(currentPlayerIndex).getDrawCount() == 0) {
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
            else if (players.get(currentPlayerIndex).getDrawCount() == 0) {
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
