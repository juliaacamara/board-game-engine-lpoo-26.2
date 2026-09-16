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

}
