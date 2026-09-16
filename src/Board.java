public class Board {
    private char[][] board;
    private int moveCount;

    public Board() {
        board = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean makeMove(Move move) {

        int row = move.getTarget().getRow();
        int column = move.getTarget().getColumn();

        if (row < 0 || row >= 3 || column < 0 || column >= 3) {
            return false;
        }

        if (board[row][column] != ' ') {
            return false;
        }

        board[row][column] = move.getPlayer().getSymbol();

        this.moveCount++;
        return true;

    }

    public boolean winCondition() {

        //rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' &&
                    board [i][0] == board[i][1] &&
                    board[i][1] == board[i][2]) {
                return true;
            }
        }

        //columns
        for  (int j = 0; j < 3; j++) {
            if (board[0][j] != ' ' &&
                    board[0][j] == board[1][j] &&
                    board[1][j] == board[2][j]) {
                return true;
            }
        }

        //condition to win on main diagonal
        if (board[0][0] != ' ' &&
                board[0][0] == board[1][1] &&
                board[1][1] == board[2][2]) {
            return true;
        }

        //condition to win on secondary diagonal
        if (board [0][2] != ' ' &&
                board[0][2] == board[1][1] &&
                board[1][1] == board[2][0]) {
            return true;
        }
        return false;

        //tie condition
    }

    public boolean isFull() {
        /*for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != ' ') {
                    return false;
                }
            }
        }*/
        return this.moveCount == 9;
    }

    public void display() {
        System.out.println();
        System.out.println("  0   1   2  ");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < 2) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("  ---+---+---");
            }
        }
        System.out.println();
    }
}