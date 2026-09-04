import java.util.Objects;

public class Player {
    private int id;
    private String symbol;

    public Player(int id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public int getId() {
        return this.id;
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
        Player other = (Player) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "Player " + this.id + " (" + this.symbol + ")";
    }
}
