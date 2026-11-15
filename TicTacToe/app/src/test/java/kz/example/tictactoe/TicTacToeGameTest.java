package kz.example.tictactoe;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TicTacToeGameTest {

    private TicTacToeGame game;

    @Before
    public void setUp() {
        game = new TicTacToeGame();
    }

    @Test
    public void firstPlayerIsX() {
        assertEquals(TicTacToeGame.X, game.getCurrentPlayer());
    }

    @Test
    public void playersAlternate() {
        game.makeMove(0);
        assertEquals(TicTacToeGame.O, game.getCurrentPlayer());
        game.makeMove(1);
        assertEquals(TicTacToeGame.X, game.getCurrentPlayer());
    }

    @Test
    public void cannotMoveToOccupiedCell() {
        assertTrue(game.makeMove(4));
        assertFalse(game.makeMove(4));
        assertEquals(TicTacToeGame.X, game.getCell(4));
    }

    @Test
    public void rowWin() {
        // X: 0,1,2   O: 3,4
        game.makeMove(0); game.makeMove(3);
        game.makeMove(1); game.makeMove(4);
        game.makeMove(2);
        assertEquals(TicTacToeGame.X, game.getWinner());
        assertTrue(game.isGameOver());
        assertArrayEquals(new int[]{0, 1, 2}, game.getWinningLine());
    }

    @Test
    public void diagonalWinForO() {
        // X: 1,2,7   O: 0,4,8
        game.makeMove(1); game.makeMove(0);
        game.makeMove(2); game.makeMove(4);
        game.makeMove(7); game.makeMove(8);
        assertEquals(TicTacToeGame.O, game.getWinner());
    }

    @Test
    public void noMovesAfterGameOver() {
        game.makeMove(0); game.makeMove(3);
        game.makeMove(1); game.makeMove(4);
        game.makeMove(2);
        assertFalse(game.makeMove(8));
    }

    @Test
    public void draw() {
        // X O X
        // X O O
        // O X X
        int[] moves = {0, 1, 2, 4, 3, 5, 7, 6, 8};
        for (int m : moves) assertTrue(game.makeMove(m));
        assertTrue(game.isDraw());
        assertEquals(TicTacToeGame.EMPTY, game.getWinner());
    }

    @Test
    public void resetClearsBoard() {
        game.makeMove(0);
        game.reset();
        assertEquals(TicTacToeGame.EMPTY, game.getCell(0));
        assertEquals(TicTacToeGame.X, game.getCurrentPlayer());
        assertFalse(game.isGameOver());
    }
}
