import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Piece other = (Piece) obj;
        return Objects.equals(this.owner, other.owner) && Objects.equals(this.symbol, other.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.owner, this.symbol);
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
