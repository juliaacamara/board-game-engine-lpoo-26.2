public class Player {
    private String id;
    private char symbol;

    public Player(String id, char symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public String getId() {
        return this.id;
    }

    public char getSymbol() {
        return this.symbol;
    }

}
