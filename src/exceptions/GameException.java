package exceptions;

/**
 * Exceção base para todos os erros de domínio e configuração da Board Game Engine.
 */
public abstract class GameException extends Exception {

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }
}

