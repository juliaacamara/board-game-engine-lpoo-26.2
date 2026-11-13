package engine;

import exceptions.GameException;
import java.util.Scanner;
import model.Board;
import model.Move;
import model.Player;
import model.Position;
import rules.PlacementRule;
import rules.WinCondition;
import ui.ConsoleRenderer;

/**
 * Motor principal de execução de uma partida (Game Engine).
 * Opera de maneira totalmente genérica sobre abstrações (Board, TurnManager, PlacementRule, WinCondition),
 * sem conhecer qual jogo específico está sendo executado.
 */
public class Game {
    private final String gameName;
    private final Board board;
    private final TurnManager turnManager;
    private Player currentPlayer;
    private final PlacementRule placementRule;
    private final WinCondition winCondition;
    private final ConsoleRenderer renderer;
    private GameState state;
    private Scanner scanner;

    public Game(
            String gameName,
            Board board,
            TurnManager turnManager,
            PlacementRule placementRule,
            WinCondition winCondition,
            ConsoleRenderer renderer
    ) {
        this.gameName = gameName;
        this.board = board;
        this.turnManager = turnManager;
        this.currentPlayer = turnManager.getCurrentPlayer();
        this.placementRule = placementRule;
        this.winCondition = winCondition;
        this.renderer = renderer;
        this.state = GameState.IN_PROGRESS;
        this.scanner = new Scanner(System.in);
    }

    public String getGameName() {
        return this.gameName;
    }

    public Board getBoard() {
        return this.board;
    }

    public TurnManager getTurnManager() {
        return this.turnManager;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public PlacementRule getPlacementRule() {
        return this.placementRule;
    }

    public WinCondition getWinCondition() {
        return this.winCondition;
    }

    public GameState getState() {
        return this.state;
    }

    /**
     * Executa uma jogada programaticamente, atualizando o estado da partida
     * e alternando o turno quando aplicável.
     */
    public Position playTurn(Position targetPosition) throws GameException {
        currentPlayer = turnManager.getCurrentPlayer();
        Move move = new Move(currentPlayer, targetPosition);

        Position placedPosition = placementRule.makeMove(board, move);

        if (winCondition.winCondition(board, placedPosition, currentPlayer)) {
            state = GameState.WIN;
        } else if (board.isFull()) {
            state = GameState.DRAW;
        } else {
            changePlayer();
        }

        return placedPosition;
    }

    public void start() {
        start(this.scanner);
    }

    /**
     * Inicia o loop interativo da partida via terminal.
     */
    public void start(Scanner inputScanner) {
        this.scanner = inputScanner;
        renderer.renderHeader(gameName, placementRule, winCondition);

        while (state == GameState.IN_PROGRESS) {
            renderer.display(board);
            currentPlayer = turnManager.getCurrentPlayer();
            renderer.renderTurnPrompt(currentPlayer);

            try {
                int row = 0;
                if (placementRule.requiresRowInput()) {
                    row = readCoordinate(scanner, "Informe a linha (1-" + board.getRows() + "): ") - 1;
                }
                int column = readCoordinate(scanner, "Informe a coluna (1-" + board.getColumns() + "): ") - 1;

                Position position = new Position(row, column);
                playTurn(position);

                if (state == GameState.WIN) {
                    renderer.display(board);
                    renderer.renderWinner(currentPlayer);
                } else if (state == GameState.DRAW) {
                    renderer.display(board);
                    renderer.renderDraw();
                }
            } catch (GameException e) {
                renderer.renderError(e.getMessage());
            }
        }
    }

    private void changePlayer() {
        turnManager.changePlayer();
        currentPlayer = turnManager.getCurrentPlayer();
    }

    private int readCoordinate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("[!] Entrada inválida: digite um número inteiro.");
            }
        }
    }
}
