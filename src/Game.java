import java.util.Scanner;

public class Game {

    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    private Scanner scanner;

    public Game() {

        board = new Board();

        player1 = new Player("Jogador 1", 'X');
        player2 = new Player("Jogador 2", 'O');

        currentPlayer = player1;

        scanner = new Scanner(System.in);
    }

    public void start() {

        System.out.println("=== JOGO DA VELHA ===");

        while (true) {

            board.display();

            System.out.println(
                    currentPlayer.getId() +
                            " (" + currentPlayer.getSymbol() + "), sua vez."
            );

            System.out.print("Digite a linha (0-2): ");
            int row = scanner.nextInt();

            System.out.print("Digite a coluna (0-2): ");
            int column = scanner.nextInt();

            Position position = new Position(row, column);

            Move move = new Move(currentPlayer, position);

            if (!board.makeMove(move)) {
                System.out.println("Jogada inválida! Tente novamente.");
                continue;
            }

            if (board.winCondition()) {

                board.display();

                System.out.println(
                        "Parabéns! " +
                                currentPlayer.getId() +
                                " venceu!"
                );

                break;
            }

            if (board.isFull()) {

                board.display();

                System.out.println("Empate!");

                break;
            }

            changePlayer();
        }

        scanner.close();
    }

    private void changePlayer() {

        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
}