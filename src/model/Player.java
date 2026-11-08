package model;

import java.util.Objects;

/**
 * Representa um jogador participante da partida.
 */
public class Player {
    private final String id;
    private final String symbol;

    public Player(String id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public String getId() {
        return this.id;
    }

    public String getSymbol() {
        return this.symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(symbol, player.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symbol);
    }

    @Override
    public String toString() {
        return id + " (" + symbol + ")";
    }
}
