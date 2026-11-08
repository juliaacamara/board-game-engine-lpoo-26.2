package config;

import java.util.Collections;
import java.util.List;

/**
 * Representa a estrutura do arquivo de configuração JSON mapeada diretamente via Gson.
 */
public class GameConfig {
    private String game;
    private BoardConfig board;
    private PlayersConfig players;
    private RulesConfig rules;

    public static class BoardConfig {
        private Integer rows;
        private Integer columns;

        public Integer getRows() {
            return rows;
        }

        public Integer getColumns() {
            return columns;
        }
    }

    public static class PlayersConfig {
        private Integer count;
        private List<String> symbols;

        public Integer getCount() {
            return count;
        }

        public List<String> getSymbols() {
            return symbols != null ? Collections.unmodifiableList(symbols) : null;
        }
    }

    public static class RulesConfig {
        private String placement;
        private WinConditionConfig winCondition;

        public String getPlacement() {
            return placement;
        }

        public WinConditionConfig getWinCondition() {
            return winCondition;
        }
    }

    public static class WinConditionConfig {
        private String type;
        private Integer quantity;

        public String getType() {
            return type;
        }

        public Integer getQuantity() {
            return quantity;
        }
    }

    public String getGameName() {
        return game;
    }

    public BoardConfig getBoard() {
        return board;
    }

    public PlayersConfig getPlayers() {
        return players;
    }

    public RulesConfig getRules() {
        return rules;
    }

    public int getRows() {
        return board != null && board.rows != null ? board.rows : 0;
    }

    public int getColumns() {
        return board != null && board.columns != null ? board.columns : 0;
    }

    public int getPlayerCount() {
        return players != null && players.count != null ? players.count : 0;
    }

    public List<String> getPlayerSymbols() {
        return players != null ? players.getSymbols() : null;
    }

    public String getPlacementRuleType() {
        return rules != null ? rules.placement : null;
    }

    public String getWinConditionType() {
        return rules != null && rules.winCondition != null ? rules.winCondition.type : null;
    }

    public Integer getWinConditionQuantity() {
        return rules != null && rules.winCondition != null ? rules.winCondition.quantity : null;
    }
}
