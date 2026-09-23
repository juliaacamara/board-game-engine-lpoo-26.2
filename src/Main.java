import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Novo Jogo");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    Game game = new Game();
                    game.start();
                    break;
                case "2":
                    System.out.println("Saindo do jogo...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        
        scanner.close();
    }
}
