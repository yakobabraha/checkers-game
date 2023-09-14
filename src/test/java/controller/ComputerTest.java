package controller;

import org.junit.jupiter.api.Test;
import view.Board;

import static org.junit.jupiter.api.Assertions.*;

class ComputerTest {

    @Test
    public void testCanLegalMoveAtPos() {
        Board board = Board.buildBoard(
                new int[][]{
                        {2, 0, 0, 0, 0, 3, 0, 4},
                        {0, 1, 0, 2, 0, 0, 1, 0},
                        {1, 0, 2, 0, 1, 0, 0, 0},
                        {0, 1, 0, 1, 0, 1, 0, 1},
                        {0, 0, 0, 2, 1, 0, 1, 0},
                        {2, 0, 3, 0, 0, 3, 0, 0},
                        {0, 4, 0, 0, 2, 0, 2, 0},
                        {1, 0, 1, 1, 0, 0, 4, 2}
                }
        );
        assertTrue(Computer.canLegalMoveAtPos(board, 1, 1));
        assertTrue(Computer.canLegalMoveAtPos(board, 2, 2));
        assertTrue(Computer.canLegalMoveAtPos(board, 5, 2));
        assertTrue(Computer.canLegalMoveAtPos(board, 7, 6));
        assertFalse(Computer.canLegalMoveAtPos(board, 1, 6));
        assertFalse(Computer.canLegalMoveAtPos(board, 1, 3));
        assertFalse(Computer.canLegalMoveAtPos(board, 5, 5));
        assertFalse(Computer.canLegalMoveAtPos(board, 6, 1));
    }
}