import java.util.ArrayList;
import java.util.List;

public record Card(Color color, Ctype type, int number) {
    private enum Color {
        RED, BLUE, GREEN, YELLOW, NOCOLOR
    }
    private enum Ctype {
        BLOCK, REVERSE, PLUSTWO, PLUSFOUR, COLORCHANGE, NORMAL
    }

    @Override
    public Color color() {
        return color;
    }

    @Override
    public Ctype type() {
        return type;
    }

    public Card getNumericCard(int number, Color color) {
        if (number < 0 || number > 9) {
            System.out.println("Invalid number");
            return null;
        }

        return new Card(color, Ctype.NORMAL, number);
    }

    public Card getSpecialCard(Color color, Ctype type) {
        if (type == Ctype.BLOCK || type == Ctype.REVERSE || type == Ctype.PLUSTWO) {
            return new Card(color, type, -1);
        }
        System.out.println("Invalid card type");
        return null;
    }

    public Card getSpecialCard(Ctype type) {
        if (type == Ctype.PLUSFOUR || type == Ctype.COLORCHANGE) {
            return getSpecialCard(Color.NOCOLOR, type);
        }
        System.out.println("Invalid card type");
        return null;
    }

    public List<Card> getStandartDeck() {
        List<Card> deck = new ArrayList<>();

        for (Color color : Color.values()) {
            if (color == Color.NOCOLOR) {continue;}

            deck.add(getNumericCard(0, color));
            for (int i = 1; i < 10; i++) {
                deck.add(getNumericCard(i, color));
                deck.add(getNumericCard(i, color));
            }

            for (int i = 0; i < 3; i++) {
                deck.add(getSpecialCard(color, Ctype.values()[i]));
                deck.add(getSpecialCard(color, Ctype.values()[i]));
            }
        }

        for (int i = 0; i < 4; i++) {
            deck.add(getSpecialCard(Ctype.PLUSFOUR));
            deck.add(getSpecialCard(Ctype.COLORCHANGE));
        }

        return deck;
    }


}
