import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> playerCards = new ArrayList<>();
    private int drawCount = 0;
    private boolean hadDrawOneCard = false;

    public static Player createPlayer() {
        return new Player();
    }

    public List<Card> getCards() {
        return playerCards;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }
}
