package rules;

import exceptions.InvalidPositionException;
import model.Board;
import model.Piece;
import model.Player;
import model.Position;

/**
 * Condição de vitória "ALIGNMENT":
 * Verifica se um jogador alinhou uma quantidade (quantity) de peças consecutivas
 * na horizontal, vertical, diagonal principal ou diagonal secundária, em qualquer dimensão de tabuleiro.
 */
public class AlignmentWinCondition implements WinCondition {

    private final int quantity;

    public AlignmentWinCondition(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public boolean winCondition(Board board, Position lastPlacedPosition, Player currentPlayer) {
        if (board == null || currentPlayer == null) {
            return false;
        }

        // Direções de busca: Horizontal (0,1), Vertical (1,0), Diagonal Principal (1,1), Diagonal Secundária (1,-1)
        int[][] directions = {
                {0, 1},
                {1, 0},
                {1, 1},
                {1, -1}
        };

        int rows = board.getRows();
        int cols = board.getColumns();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!belongsToPlayer(board, r, c, currentPlayer)) {
                    continue;
                }
                for (int[] dir : directions) {
                    if (hasAlignmentFrom(board, r, c, dir[0], dir[1], currentPlayer)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hasAlignmentFrom(Board board, int startRow, int startCol, int dRow, int dCol, Player player) {
        for (int step = 1; step < quantity; step++) {
            int nextRow = startRow + step * dRow;
            int nextCol = startCol + step * dCol;
            if (!belongsToPlayer(board, nextRow, nextCol, player)) {
                return false;
            }
        }
        return true;
    }

    private boolean belongsToPlayer(Board board, int row, int col, Player player) {
        if (!board.isValidPosition(row, col)) {
            return false;
        }
        try {
            Piece piece = board.getPiece(row, col);
            return piece != null && player.equals(piece.getOwner());
        } catch (InvalidPositionException e) {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "Alinhar " + quantity + " peças consecutivas (horizontal, vertical ou diagonal)";
    }
}
