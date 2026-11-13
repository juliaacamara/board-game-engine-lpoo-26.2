package ui;

import exceptions.InvalidPositionException;
import model.Board;
import model.Piece;
import model.Player;
import rules.PlacementRule;
import rules.WinCondition;

/**
 * Responsável pela apresentação visual da partida e do tabuleiro no terminal.
 * Desacopla a lógica de domínio (Board/Game) da formatação de saída (Console).
 */
public class ConsoleRenderer {

    public void renderHeader(String gameName, PlacementRule placementRule, WinCondition winCondition) {
        System.out.println("\n================================");
        System.out.println("          BOARD ENGINE          ");
        System.out.println("================================");
        System.out.println("Jogo: " + gameName);
        System.out.println("Posicionamento: " + placementRule.getDescription());
        System.out.println("Objetivo: " + winCondition.getDescription());
    }

    public void display(Board board) {
        int rows = board.getRows();
        int cols = board.getColumns();

        System.out.println();

        // Cabeçalho com índices das colunas (1 a cols)
        System.out.print("   ");
        for (int c = 1; c <= cols; c++) {
            System.out.printf(" %2d ", c);
        }
        System.out.println();

        String horizontalBorder = buildHorizontalBorder(cols);
        System.out.println(horizontalBorder);

        for (int r = 0; r < rows; r++) {
            System.out.printf("%2d |", r + 1);
            for (int c = 0; c < cols; c++) {
                String symbol = " ";
                try {
                    Piece piece = board.getPiece(r, c);
                    if (piece != null) {
                        symbol = piece.getSymbol();
                    }
                } catch (InvalidPositionException ignored) {
                }
                System.out.printf(" %s |", formatSymbol(symbol));
            }
            System.out.println();
            System.out.println(horizontalBorder);
        }
        System.out.println();
    }

    public void renderTurnPrompt(Player currentPlayer) {
        System.out.println(currentPlayer.getId() + " (" + currentPlayer.getSymbol() + "), sua vez.");
    }

    public void renderWinner(Player winner) {
        System.out.println("========================================");
        System.out.println("Parabéns! " + winner.getId() + " (" + winner.getSymbol() + ") venceu!");
        System.out.println("========================================");
    }

    public void renderDraw() {
        System.out.println("========================================");
        System.out.println("Empate! O tabuleiro está cheio.");
        System.out.println("========================================");
    }

    public void renderError(String message) {
        System.out.println("[!] Jogada inválida! " + message);
    }

    private String buildHorizontalBorder(int cols) {
        StringBuilder sb = new StringBuilder("   +");
        for (int c = 0; c < cols; c++) {
            sb.append("---+");
        }
        return sb.toString();
    }

    private String formatSymbol(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            return " ";
        }
        return symbol.substring(0, 1);
    }
}

