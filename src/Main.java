import config.GameConfig;
import config.GameLoader;
import engine.Game;
import exceptions.InvalidConfigurationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Ponto de entrada da aplicação Board Game Engine.
 * Permite selecionar jogos a partir da pasta 'games/' ou informar diretamente um arquivo JSON.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameLoader loader = new GameLoader();

        // Permite executar passando o caminho do arquivo JSON via argumento de linha de comando
        if (args.length > 0) {
            runGameFromPath(loader, Paths.get(args[0]), scanner);
            scanner.close();
            return;
        }

        boolean running = true;
        while (running) {
            List<Path> gameFiles = discoverGameFiles();

            System.out.println("\n================================");
            System.out.println("       BOARD GAME ENGINE        ");
            System.out.println("================================");
            System.out.println("Selecione o jogo:");

            for (int i = 0; i < gameFiles.size(); i++) {
                Path path = gameFiles.get(i);
                String label = readGameDisplayName(loader, path);
                System.out.printf("%d - %s (%s)%n", i + 1, label, path.getFileName());
            }

            int customFileOption = gameFiles.size() + 1;
            int exitOption = gameFiles.size() + 2;

            System.out.printf("%d - Carregar arquivo de configuração específico (.json)%n", customFileOption);
            System.out.printf("%d - Sair%n", exitOption);
            System.out.print("Escolha uma opção: ");

            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[!] Opção inválida. Digite o número correspondente.");
                continue;
            }

            if (choice >= 1 && choice <= gameFiles.size()) {
                Path selectedFile = gameFiles.get(choice - 1);
                runGameFromPath(loader, selectedFile, scanner);
            } else if (choice == customFileOption) {
                System.out.print("Informe o caminho do arquivo JSON: ");
                String customPathStr = scanner.nextLine().trim();
                if (!customPathStr.isEmpty()) {
                    runGameFromPath(loader, resolveCustomPath(customPathStr), scanner);
                }
            } else if (choice == exitOption) {
                System.out.println("Encerrando a Board Game Engine...");
                running = false;
            } else {
                System.out.println("[!] Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    private static void runGameFromPath(GameLoader loader, Path path, Scanner scanner) {
        try {
            Game game = loader.loadFromFile(path);
            game.start(scanner);
        } catch (InvalidConfigurationException e) {
            System.out.println("\nErro ao carregar o jogo:");
            System.out.println("  " + e.getMessage());
        }
    }

    private static List<Path> discoverGameFiles() {
        Path gamesDir = Paths.get("games");
        if (!Files.isDirectory(gamesDir)) {
            gamesDir = Paths.get("..", "games");
        }
        if (!Files.isDirectory(gamesDir)) {
            return new ArrayList<>();
        }
        try (Stream<Path> stream = Files.list(gamesDir)) {
            return stream
                    .filter(p -> Files.isRegularFile(p) && p.toString().toLowerCase().endsWith(".json"))
                    .sorted(Comparator.comparing(p -> p.getFileName().toString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static Path resolveCustomPath(String pathStr) {
        Path candidate = Paths.get(pathStr);
        if (Files.exists(candidate)) {
            return candidate;
        }
        Path parentCandidate = Paths.get("..").resolve(pathStr);
        if (Files.exists(parentCandidate)) {
            return parentCandidate;
        }
        return candidate;
    }

    private static String readGameDisplayName(GameLoader loader, Path file) {
        try {
            String content = Files.readString(file, StandardCharsets.UTF_8);
            GameConfig config = loader.parseAndValidateConfig(content);
            return config.getGameName();
        } catch (Exception e) {
            return file.getFileName().toString();
        }
    }
}
