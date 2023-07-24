
public class Autoplay {
    private Game game;

    public Autoplay(Game game) {
        this.game = game;
    }

    public int calculateMoveValue(Card card) {
        int value = 0;

        if (card.type() == Card.Ctype.COLORCHANGE || card.type() == Card.Ctype.PLUSFOUR) {
            value+=5;
        }
        else if (card.type() == Card.Ctype.PLUSTWO || card.type() == Card.Ctype.REVERSE ||
                        card.type() == Card.Ctype.BLOCK ) {
            value+=3;
        }
        else {
            value+=1;
        }

        int nextPlayerIndex = 0;
        int previousPlayerIndex = 0;
        if (game.isClockwise) {
            nextPlayerIndex = game.currentPlayerIndex + 1 == game.playerCount ? 0 : game.currentPlayerIndex + 1;
            previousPlayerIndex = game.currentPlayerIndex - 1< 0 ? game.playerCount - 1 : game.currentPlayerIndex -1;
        }
        else {
            nextPlayerIndex = game.currentPlayerIndex - 1 < 0 ? game.playerCount-1 : game.currentPlayerIndex - 1;
            previousPlayerIndex = game.currentPlayerIndex + 1 == game.playerCount ? 0 : game.currentPlayerIndex + 1;
        }

        if (game.players.get(nextPlayerIndex).getCards().size() < 4) {
            switch (card.type()) {
                case PLUSFOUR -> value+=3;
                case PLUSTWO -> value+=2;
                case REVERSE, BLOCK, COLORCHANGE -> value+=1;
                case NORMAL -> value -=1;
            }
        }
        else {
            switch (card.type()) {
                case PLUSFOUR -> value-=2;
                case PLUSTWO ->  value -=1;
            }
        }

        if (game.players.get(previousPlayerIndex).getCards().size() < 4) {
            switch (card.type()) {
                case REVERSE -> value-=2;
            }
        }

        if (game.drawCount != 0) {
            switch (card.type()) {
                case PLUSFOUR -> value+=2;
                case PLUSTWO -> value+=1;
            }
        }

        return value;
    }

    //deckHighestValueCard
}
