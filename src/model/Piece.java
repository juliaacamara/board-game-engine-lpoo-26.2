package model;

/**
 * Representa uma peça posicionada no tabuleiro, associada a um jogador e seu símbolo.
 */
public class Piece {
    private final Player owner;
    private final String symbol;

    public Piece(Player owner) {
        this(owner, owner.getSymbol());
    }

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
