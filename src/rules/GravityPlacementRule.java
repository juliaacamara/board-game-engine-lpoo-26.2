package rules;

import exceptions.InvalidMoveException;
import exceptions.InvalidPositionException;
import model.Board;
import model.Move;
import model.Position;

/**
 * Regra de posicionamento "GRAVITY":
 * O jogador escolhe a coluna e a peça cai por gravidade até a linha vazia mais baixa disponível.
 */
public class GravityPlacementRule implements PlacementRule {

    @Override
    public Position makeMove(Board board, Move move) throws InvalidPositionException, InvalidMoveException {
        int column = move.getTarget().getColumn();
        board.validateColumn(column);

        for (int row = board.getRows() - 1; row >= 0; row--) {
            Position candidate = new Position(row, column);
            if (board.isEmpty(candidate)) {
                Move gravityMove = new Move(move.getPlayer(), candidate);
                board.makeMove(gravityMove);
                return candidate;
            }
        }

        throw new InvalidMoveException(
                String.format("A coluna %d já está totalmente cheia! Escolha outra coluna.", column + 1)
        );
    }

    @Override
    public boolean requiresRowInput() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Gravidade (a peça ocupa a linha livre mais baixa da coluna escolhida)";
    }
}
