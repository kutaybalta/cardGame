import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> playerCards = new ArrayList<>();
    private boolean hadDrawOneCard = false;

    public static Player createPlayer() {
        return new Player();
    }

    public List<Card> getCards() {
        return playerCards;
    }

}
