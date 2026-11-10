package rules;

import exceptions.InvalidMoveException;
import exceptions.InvalidPositionException;
import model.Board;
import model.Move;
import model.Position;

/**
 * Contrato para regras de posicionamento de peças no tabuleiro.
 */
public interface PlacementRule {

    /**
     * Valida e executa a jogada (makeMove) no tabuleiro de acordo com a regra.
     *
     * @param board O tabuleiro da partida
     * @param move  A intenção de jogada contendo jogador e posição alvo
     * @return A posição final em que a peça foi efetivamente colocada
     * @throws InvalidPositionException Se a posição informada estiver fora dos
     *                                  limites do tabuleiro
     * @throws InvalidMoveException     Se a jogada violar a regra de posicionamento
     */
    Position makeMove(Board board, Move move) throws InvalidPositionException, InvalidMoveException;

    /**
     * Indica se a regra exige que o jogador informe a linha além da coluna.
     */
    boolean requiresRowInput();

    /**
     * Retorna uma descrição legível da regra de posicionamento.
     */
    String getDescription();
}
