package exceptions;

/**
 * Exceção lançada quando o arquivo de configuração do jogo é inválido ou referencia regras inexistentes.
 */
public class InvalidConfigurationException extends GameException {

    public InvalidConfigurationException(String message) {
        super(message);
    }

    public InvalidConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}

