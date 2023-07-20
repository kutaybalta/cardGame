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
