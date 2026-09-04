import java.util.Objects;

public class Move {
    private Player player;
    private Position target;

    public Move(Player player, Position target) {
        this.player = player;
        this.target = target;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Position getTarget() {
        return this.target;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Move other = (Move) obj;
        return Objects.equals(this.player, other.player) && Objects.equals(this.target, other.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.player, this.target);
    }

    @Override
    public String toString() {
        return this.player.getSymbol() + " -> " + this.target;
    }
}
