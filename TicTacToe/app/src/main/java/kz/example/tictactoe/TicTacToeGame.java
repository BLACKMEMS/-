package kz.example.tictactoe;

/**
 * Ойын логикасы (Android-ға тәуелсіз, сондықтан JUnit арқылы тексеруге болады).
 * Тақта 0..8 индекстерімен нөмірленген:
 *  0 | 1 | 2
 *  3 | 4 | 5
 *  6 | 7 | 8
 */
public class TicTacToeGame {

    public static final char EMPTY = ' ';
    public static final char X = 'X';
    public static final char O = 'O';

    /** Жеңіске апаратын 8 сызық. */
    public static final int[][] LINES = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},   // жолдар
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},   // бағандар
            {0, 4, 8}, {2, 4, 6}               // диагональдар
    };

    private final char[] board = new char[9];
    private char currentPlayer;
    private char winner;
    private int[] winningLine;
    private int movesCount;

    public TicTacToeGame() {
        reset();
    }

    /** Жаңа ойынды бастау. */
    public void reset() {
        for (int i = 0; i < 9; i++) board[i] = EMPTY;
        currentPlayer = X;
        winner = EMPTY;
        winningLine = null;
        movesCount = 0;
    }

    /**
     * Ағымдағы ойыншының жүрісі.
     * @return жүріс орындалса true, ұяшық бос емес немесе ойын біткен болса false
     */
    public boolean makeMove(int index) {
        if (index < 0 || index > 8) return false;
        if (isGameOver() || board[index] != EMPTY) return false;

        board[index] = currentPlayer;
        movesCount++;
        checkWinner();
        if (!isGameOver()) {
            currentPlayer = (currentPlayer == X) ? O : X;
        }
        return true;
    }

    private void checkWinner() {
        for (int[] line : LINES) {
            char a = board[line[0]];
            if (a != EMPTY && a == board[line[1]] && a == board[line[2]]) {
                winner = a;
                winningLine = line;
                return;
            }
        }
    }

    public boolean isDraw() {
        return winner == EMPTY && movesCount == 9;
    }

    public boolean isGameOver() {
        return winner != EMPTY || movesCount == 9;
    }

    public char getCell(int index) { return board[index]; }
    public char getCurrentPlayer() { return currentPlayer; }
    public char getWinner() { return winner; }
    public int[] getWinningLine() { return winningLine; }
}
