package rules;

import exceptions.InvalidMoveException;
import exceptions.InvalidPositionException;
import model.Board;
import model.Move;
import model.Position;

/**
 * Regra de posicionamento "EMPTY_POSITION":
 * Permite posicionar uma peça em qualquer coordenada (linha, coluna) válida que esteja vazia.
 */
public class EmptyPositionPlacementRule implements PlacementRule {

    @Override
    public Position makeMove(Board board, Move move) throws InvalidPositionException, InvalidMoveException {
        Position target = move.getTarget();
        board.validatePosition(target);

        if (!board.isEmpty(target)) {
            throw new InvalidMoveException(
                    String.format("A posição (%d, %d) já está ocupada! Escolha uma casa vazia.",
                            target.getRow() + 1, target.getColumn() + 1)
            );
        }

        board.makeMove(move);
        return target;
    }

    @Override
    public boolean requiresRowInput() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Posição livre (qualquer casa vazia do tabuleiro)";
    }
}
