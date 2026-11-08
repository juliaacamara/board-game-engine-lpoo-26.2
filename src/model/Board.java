package model;

import exceptions.InvalidMoveException;
import exceptions.InvalidPositionException;

/**
 * Representa o tabuleiro bidimensional de forma genérica e encapsulada.
 * Responsável por manter o estado das peças na matriz (board) e validar limites.
 */
public class Board {
    private final int rows;
    private final int columns;
    private final Piece[][] board;
    private int moveCount;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new Piece[rows][columns];
        this.moveCount = 0;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public int getMoveCount() {
        return this.moveCount;
    }

    public boolean isValidPosition(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean isValidPosition(Position position) {
        return position != null && isValidPosition(position.getRow(), position.getColumn());
    }

    public void validatePosition(Position position) throws InvalidPositionException {
        if (position == null) {
            throw new InvalidPositionException("Posição não pode ser nula.");
        }
        if (!isValidPosition(position)) {
            throw new InvalidPositionException(
                    String.format("Posição (%d, %d) fora dos limites do tabuleiro (%dx%d).",
                            position.getRow() + 1, position.getColumn() + 1, rows, columns)
            );
        }
    }

    public void validateColumn(int column) throws InvalidPositionException {
        if (column < 0 || column >= columns) {
            throw new InvalidPositionException(
                    String.format("Coluna %d fora dos limites permitidos (1 a %d).", column + 1, columns)
            );
        }
    }

    public boolean isEmpty(Position position) throws InvalidPositionException {
        validatePosition(position);
        return board[position.getRow()][position.getColumn()] == null;
    }

    public boolean isEmpty(int row, int column) throws InvalidPositionException {
        return isEmpty(new Position(row, column));
    }

    public Piece getPiece(Position position) throws InvalidPositionException {
        validatePosition(position);
        return board[position.getRow()][position.getColumn()];
    }

    public Piece getPiece(int row, int column) throws InvalidPositionException {
        return getPiece(new Position(row, column));
    }

    public boolean makeMove(Move move) throws InvalidPositionException, InvalidMoveException {
        if (move == null) {
            throw new InvalidMoveException("Jogada inválida.");
        }
        placePiece(move.getTarget(), move.createPiece());
        return true;
    }

    public void placePiece(Position position, Piece piece) throws InvalidPositionException, InvalidMoveException {
        validatePosition(position);
        if (piece == null) {
            throw new InvalidMoveException("Peça inválida para posicionamento.");
        }
        int r = position.getRow();
        int c = position.getColumn();
        if (board[r][c] != null) {
            throw new InvalidMoveException(
                    String.format("A posição (%d, %d) já está ocupada.", r + 1, c + 1)
            );
        }
        board[r][c] = piece;
        this.moveCount++;
    }

    public boolean isFull() {
        return this.moveCount >= (this.rows * this.columns);
    }
}
