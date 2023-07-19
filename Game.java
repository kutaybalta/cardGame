import java.util.ArrayList;
import java.util.List;

public class Game {
    private int playerCount;
    public boolean hasSomeoneWon = false;
    private int currentPlayerIndex = 0;
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
    }

}
