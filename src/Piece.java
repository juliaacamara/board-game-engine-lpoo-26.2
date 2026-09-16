public class Piece {
    private Player owner;
    private String symbol;

    public Piece(Player owner, String symbol) {
        this.owner = owner;
        this.symbol = symbol;
    }

    public Player getOwner() {
        return this.owner;
    }

    public String getSymbol() {
        return this.symbol;
    }

}
