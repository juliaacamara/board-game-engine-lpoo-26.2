package model;

/**
 * Representa a intenção de movimento ou posicionamento de um jogador em uma posição alvo.
 */
public class Move {
    private final Player player;
    private final Position target;

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

    public Piece createPiece() {
        return new Piece(player);
    }
}
