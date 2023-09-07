package src.controller;

import src.view.Board;
import src.view.Field;


public class Computer {
    public static boolean canLegalMoveAtPos(Board board, int row, int col) {
        if (row < 0 || row >= board.getLength() || col < 0 || col >= board.getLength())
            throw new IllegalArgumentException("row/col not in bounds");
        if (board.isFieldUnoccupied(row, col)) return false;

        boolean isBlack = board.isFieldOfPlayer(true, row, col);
        boolean isWhiteKing = board.getFieldStatus(row, col) == Field.FieldStatus.WHITE_KING;
        boolean isBlackKing = board.getFieldStatus(row, col) == Field.FieldStatus.BLACK_KING;
        boolean canJumpUpwards = isBlack || isWhiteKing;
        boolean canJumpDownwards = !isBlack || isBlackKing;
        int boardLength = board.getLength();

        //check if normal moves without jumping are possible
        boolean isMoveTopLeftPossible = canJumpUpwards && row - 1 >= 0 && col - 1 >= 0
                && board.isFieldUnoccupied(row - 1, col - 1);
        if (isMoveTopLeftPossible) return true;
        boolean isMoveTopRightPossible = canJumpUpwards && row - 1 >= 0 && col + 1 < boardLength
                && board.isFieldUnoccupied(row - 1, col + 1);
        if (isMoveTopRightPossible) return true;
        boolean isMoveBottomLeftPossible = canJumpDownwards && row + 1 < boardLength && col - 1 >= 0
                && board.isFieldUnoccupied(row + 1, col - 1);
        if (isMoveBottomLeftPossible) return true;
        boolean isMoveBottomRightPossible = canJumpDownwards && row + 1 < boardLength && col + 1 < boardLength
                && board.isFieldUnoccupied(row + 1, col + 1);
        if (isMoveBottomRightPossible) return true;

        //check if jumps over enemies are possible
        return canJumpAtPos(row, col, board);
    }

    public static boolean canJumpAtPos(int row, int col, Board board) {
        boolean isBlack = board.isFieldOfPlayer(true, row, col);
        boolean isWhiteKing = board.getFieldStatus(row, col) == Field.FieldStatus.WHITE_KING;
        boolean isBlackKing = board.getFieldStatus(row, col) == Field.FieldStatus.BLACK_KING;
        boolean canJumpUpwards = isBlack || isWhiteKing;
        boolean canJumpDownwards = !isBlack || isBlackKing;
        int boardLength = board.getLength();

        boolean isJumpTopLeftPossible = canJumpUpwards && row - 2 >= 0 && col - 2 >= 0
                && board.isFieldUnoccupied(row - 2, col - 2)
                && board.isFieldOfPlayer(!isBlack, row - 1, col - 1);
        if (isJumpTopLeftPossible) return true;
        boolean isJumpTopRightPossible = canJumpUpwards && row - 2 >= 0 && col + 2 < boardLength
                && board.isFieldUnoccupied(row - 2, col + 2)
                && board.isFieldOfPlayer(!isBlack, row - 1, col + 1);
        if (isJumpTopRightPossible) return true;
        boolean isJumpBottomLeftPossible = canJumpDownwards && row + 2 < boardLength && col - 2 >= 0
                && board.isFieldUnoccupied(row + 2, col - 2)
                && board.isFieldOfPlayer(!isBlack, row + 1, col - 1);
        if (isJumpBottomLeftPossible) return true;
        //JumpBottomRight as last Condition
        return canJumpDownwards && row + 2 < boardLength && col + 2 < boardLength
                && board.isFieldUnoccupied(row + 2, col + 2)
                && board.isFieldOfPlayer(!isBlack, row + 1, col + 1);
    }

    public static boolean areThereJumps(boolean isBlacksTurn, Board board) {
        for (int row = 0; row < board.getLength(); row++) {
            for (int col = 0; col < board.getLength(); col++) {
                if (board.isFieldOfPlayer(isBlacksTurn, row, col)
                        && canJumpAtPos(row, col, board)) return true;
            }
        }
        return false;
    }
}
