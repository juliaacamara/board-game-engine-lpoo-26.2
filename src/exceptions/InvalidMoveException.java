package exceptions;

/**
 * Exceção lançada quando uma jogada viola as regras de posicionamento ou movimentação do jogo.
 */
public class InvalidMoveException extends GameException {

    public InvalidMoveException(String message) {
        super(message);
    }
}

