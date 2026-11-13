package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Player;

/**
 * Gerencia a fila circular de turnos de N jogadores usando coleções Java (List).
 */
public class TurnManager {
    private final List<Player> players;
    private int currentPlayerIndex;
    private Player currentPlayer;

    public TurnManager(List<Player> players) {
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = 0;
        this.currentPlayer = this.players.get(0);
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void changePlayer() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
        this.currentPlayer = this.players.get(this.currentPlayerIndex);
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }
}

