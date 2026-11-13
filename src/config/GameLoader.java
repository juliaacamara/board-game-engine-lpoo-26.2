package config;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import engine.Game;
import engine.TurnManager;
import exceptions.InvalidConfigurationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import model.Board;
import model.Player;
import rules.AlignmentWinCondition;
import rules.EmptyPositionPlacementRule;
import rules.GravityPlacementRule;
import rules.PlacementRule;
import rules.WinCondition;
import ui.ConsoleRenderer;

/**
 * Responsável por carregar (usando a biblioteca Gson), validar e transformar um arquivo
 * de configuração JSON nos objetos polimórficos que compõem uma partida da Game Engine.
 */
public class GameLoader {

    @FunctionalInterface
    public interface WinConditionFactory {
        WinCondition create(GameConfig config) throws InvalidConfigurationException;
    }

    private final Gson gson;
    private final Map<String, Supplier<PlacementRule>> placementRuleRegistry;
    private final Map<String, WinConditionFactory> winConditionRegistry;

    public GameLoader() {
        this.gson = new Gson();
        this.placementRuleRegistry = new HashMap<>();
        this.winConditionRegistry = new HashMap<>();
        registerDefaultRules();
    }

    private void registerDefaultRules() {
        // Registro extensível de regras de posicionamento (evita cadeias de if/else)
        registerPlacementRule("EMPTY_POSITION", EmptyPositionPlacementRule::new);
        registerPlacementRule("GRAVITY", GravityPlacementRule::new);

        // Registro extensível de condições de vitória
        registerWinCondition("ALIGNMENT", this::createAlignmentWinCondition);
    }

    public void registerPlacementRule(String key, Supplier<PlacementRule> supplier) {
        placementRuleRegistry.put(key.toUpperCase(), supplier);
    }

    public void registerWinCondition(String key, WinConditionFactory factory) {
        winConditionRegistry.put(key.toUpperCase(), factory);
    }

    /**
     * Carrega um arquivo JSON de configuração e constrói uma instância completa de Game.
     */
    public Game loadFromFile(Path filePath) throws InvalidConfigurationException {
        if (filePath == null || !Files.exists(filePath)) {
            throw new InvalidConfigurationException("Arquivo de configuração não encontrado: " + filePath);
        }
        try {
            String jsonContent = Files.readString(filePath, StandardCharsets.UTF_8);
            GameConfig config = parseAndValidateConfig(jsonContent);
            return buildGame(config);
        } catch (IOException e) {
            throw new InvalidConfigurationException("Erro de leitura no arquivo: " + e.getMessage(), e);
        }
    }

    /**
     * Desserializa o texto JSON via Gson e valida todos os campos obrigatórios e restrições de domínio.
     */
    public GameConfig parseAndValidateConfig(String jsonContent) throws InvalidConfigurationException {
        if (jsonContent == null || jsonContent.trim().isEmpty()) {
            throw new InvalidConfigurationException("Arquivo de configuração está vazio.");
        }

        GameConfig config;
        try {
            config = gson.fromJson(jsonContent, GameConfig.class);
        } catch (JsonSyntaxException e) {
            throw new InvalidConfigurationException("Formato JSON inválido: " + e.getMessage(), e);
        }

        if (config == null) {
            throw new InvalidConfigurationException("Configuração JSON vazia ou inválida.");
        }

        if (config.getGameName() == null || config.getGameName().trim().isEmpty()) {
            throw new InvalidConfigurationException("Nome do jogo ('game') não informado.");
        }

        if (config.getBoard() == null || config.getBoard().getRows() == null || config.getBoard().getColumns() == null) {
            throw new InvalidConfigurationException("Seção 'board' (rows e columns) ausente ou incompleta na configuração.");
        }

        int rows = config.getRows();
        int columns = config.getColumns();
        if (rows <= 0 || columns <= 0) {
            throw new InvalidConfigurationException(
                    String.format("Dimensões do tabuleiro inválidas (%dx%d). Linhas e colunas devem ser maiores que zero.", rows, columns)
            );
        }

        if (config.getPlayers() == null || config.getPlayers().getCount() == null) {
            throw new InvalidConfigurationException("Quantidade de jogadores ('players.count') ausente na configuração.");
        }

        int playerCount = config.getPlayerCount();
        if (playerCount < 2) {
            throw new InvalidConfigurationException("O jogo deve possuir pelo menos 2 jogadores (recebido: " + playerCount + ").");
        }

        List<String> symbols = config.getPlayerSymbols();
        if (symbols == null || symbols.size() != playerCount) {
            throw new InvalidConfigurationException(
                    String.format("A quantidade de símbolos (%d) não corresponde à quantidade de jogadores (%d).",
                            symbols == null ? 0 : symbols.size(), playerCount)
            );
        }

        Set<String> uniqueSymbols = new HashSet<>();
        for (String symbol : symbols) {
            if (symbol == null || symbol.trim().isEmpty()) {
                throw new InvalidConfigurationException("Os símbolos dos jogadores não podem ser vazios.");
            }
            if (!uniqueSymbols.add(symbol.trim())) {
                throw new InvalidConfigurationException("Símbolo duplicado encontrado para jogadores: '" + symbol + "'.");
            }
        }

        if (config.getRules() == null || config.getPlacementRuleType() == null || config.getPlacementRuleType().trim().isEmpty()) {
            throw new InvalidConfigurationException("Regra de posicionamento ('rules.placement') não informada.");
        }

        if (config.getRules().getWinCondition() == null || config.getWinConditionType() == null || config.getWinConditionType().trim().isEmpty()) {
            throw new InvalidConfigurationException("Tipo de condição de vitória ('rules.winCondition.type') não informado.");
        }

        return config;
    }

    /**
     * Constrói o objeto Game e seus componentes polimórficos a partir de um GameConfig validado.
     */
    public Game buildGame(GameConfig config) throws InvalidConfigurationException {
        String placementKey = config.getPlacementRuleType().trim().toUpperCase();
        Supplier<PlacementRule> placementSupplier = placementRuleRegistry.get(placementKey);
        if (placementSupplier == null) {
            throw new InvalidConfigurationException(
                    "Regra de posicionamento '" + config.getPlacementRuleType() + "' não reconhecida."
            );
        }
        PlacementRule placementRule = placementSupplier.get();

        String winKey = config.getWinConditionType().trim().toUpperCase();
        WinConditionFactory winFactory = winConditionRegistry.get(winKey);
        if (winFactory == null) {
            throw new InvalidConfigurationException(
                    "Regra de vitória '" + config.getWinConditionType() + "' não reconhecida."
            );
        }
        WinCondition winCondition = winFactory.create(config);

        Board board = new Board(config.getRows(), config.getColumns());

        List<Player> players = new ArrayList<>();
        List<String> symbols = config.getPlayerSymbols();
        for (int i = 0; i < config.getPlayerCount(); i++) {
            players.add(new Player("Jogador " + (i + 1), symbols.get(i).trim()));
        }

        TurnManager turnManager = new TurnManager(players);
        ConsoleRenderer renderer = new ConsoleRenderer();

        return new Game(
                config.getGameName().trim(),
                board,
                turnManager,
                placementRule,
                winCondition,
                renderer
        );
    }

    private WinCondition createAlignmentWinCondition(GameConfig config) throws InvalidConfigurationException {
        Integer quantity = config.getWinConditionQuantity();
        if (quantity == null) {
            throw new InvalidConfigurationException("Parâmetro 'quantity' obrigatório para a condição de vitória 'ALIGNMENT'.");
        }

        int maxDimension = Math.max(config.getRows(), config.getColumns());
        if (quantity < 2 || quantity > maxDimension) {
            throw new InvalidConfigurationException(
                    String.format("Quantidade de alinhamento inválida (%d) para um tabuleiro %dx%d.",
                            quantity, config.getRows(), config.getColumns())
            );
        }
        return new AlignmentWinCondition(quantity);
    }
}
