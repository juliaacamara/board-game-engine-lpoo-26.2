package exceptions;

/**
 * Exceção lançada quando uma coordenada (linha ou coluna) está fora dos limites do tabuleiro.
 */
public class InvalidPositionException extends GameException {

    public InvalidPositionException(String message) {
        super(message);
    }
}

