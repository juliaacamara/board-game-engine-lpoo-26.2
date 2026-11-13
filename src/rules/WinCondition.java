package rules;

import model.Board;
import model.Player;
import model.Position;

/**
 * Contrato para verificação de condições de vitória.
 */
public interface WinCondition {

    /**
     * Verifica se o jogador atual atingiu a condição de vitória (winCondition) após
     * a jogada.
     *
     * @param board              O tabuleiro atual
     * @param lastPlacedPosition A última posição onde uma peça foi colocada
     * @param currentPlayer      O jogador que realizou a jogada
     * @return true se o jogador venceu a partida, false caso contrário
     */
    boolean winCondition(Board board, Position lastPlacedPosition, Player currentPlayer);

    /**
     * Retorna uma descrição legível da condição de vitória.
     */
    String getDescription();
}
