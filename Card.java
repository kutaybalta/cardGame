import java.util.*;

public record Card(Color color, Ctype type, int number) {
    private enum Color {
        RED, BLUE, GREEN, YELLOW, NOCOLOR
    }
    public enum Ctype {
        BLOCK, REVERSE, PLUSTWO, PLUSFOUR, COLORCHANGE, NORMAL
    }

    public String getColor() {
        return color.toString();
    }

    @Override
    public Ctype type() {
        return type;
    }

    public static Card getNumericCard(int number, Color color) {
        if (number < 0 || number > 9) {
            System.out.println("Invalid number");
            return null;
        }

        return new Card(color, Ctype.NORMAL, number);
    }

    public static Card getSpecialCard(Color color, Ctype type) {
        if (type == Ctype.BLOCK || type == Ctype.REVERSE || type == Ctype.PLUSTWO) {
            return new Card(color, type, -1);
        }
        System.out.println("Invalid card type");
        return null;
    }

    public static Card getSpecialCard(Ctype type) {
        if (type == Ctype.PLUSFOUR || type == Ctype.COLORCHANGE) {
            return new Card(Color.NOCOLOR, type, -1);
        }
        System.out.println("Invalid card type");
        return null;
    }
    public static String askColor() {
        Scanner scanner = new Scanner(System.in);
        int colorindex = 0;
        boolean endLoop = false;
        String color = "NOCOLOR";
        do {
            System.out.println("Please choose color:\n1)RED  2)BLUE  3)GREEN  4)YELLOW");
            try {
                colorindex = scanner.nextInt();
            }catch (InputMismatchException ime) {
                System.out.println("Please give number.");
                scanner.nextLine();
                continue;
            }

            if (colorindex >= 1 && colorindex <= 4) {
                color = Color.values()[colorindex-1].toString();
                endLoop = true;
            }
            else {
                System.out.println("Numbers should be between 1 and 4");
                endLoop = false;
            }
        }while (!endLoop);
        return color;
    }

    public static List<Card> getStandartDeck() {
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

        Collections.shuffle(deck);
        return deck;
    }

    public static Card getRandomCardFromDeck(List<Card> deck) {
        //deck.size == 0?
        int randomIndex = new Random().nextInt(deck.size());
        Card drawedCard = deck.get(randomIndex);
        deck.remove(randomIndex);
        return drawedCard;
    }



    @Override
    public String toString() {
        if (type == Ctype.NORMAL) {
            return color.toString() + "-" + number;
        }
        else if (type == Ctype.PLUSFOUR || type == Ctype.COLORCHANGE) {
            return type.toString();
        }

        return color.toString() + "-" + type.toString();
    }
}
