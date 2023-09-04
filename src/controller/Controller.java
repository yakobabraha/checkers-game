package src.controller;

import src.model.Model;
import src.view.Board;
import src.view.Field;
import src.view.View;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final Model model;
    private final View view;

    private Board board;
    private boolean isBlacksTurn = true;

    private int startPositionRow, startPositionCol;

    private boolean jumped = false;
    private boolean startPositionChosen = false;

    private int currPositionRow, currPositionCol;

    private final List<int[]> toRemove = new ArrayList<>();
    private final List<int[]> toMove = new ArrayList<>();

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public boolean handleFieldClick(int row, int col, Board board) {
        view.setErrorLabel("");
        this.board = board;
        if (!startPositionChosen) {
            if (board.isFieldOfPlayer(true, row, col) && isBlacksTurn
                    || board.isFieldOfPlayer(false, row, col) && !isBlacksTurn) {
                startPositionRow = currPositionRow = row;
                startPositionCol = currPositionCol = col;
                board.fieldToSelectColor(row, col);
                startPositionChosen = true;
            } else if (board.getFieldStatus(row, col) != Field.fieldStatus.BLANK) {
                if (isBlacksTurn) {
                    view.setErrorLabel("It's not White's turn");
                } else {
                    view.setErrorLabel("It's not Black's turn");
                }
            }
        } else {
            Field.fieldStatus pieceToMove = board.getFieldStatus(startPositionRow, startPositionCol);
            boolean isNormalMove =
                    (
                            (pieceToMove == Field.fieldStatus.BLACK_MAN || pieceToMove == Field.fieldStatus.WHITE_KING)
                                    && row == currPositionRow - 1
                                    && (col == currPositionCol - 1
                                    && board.isFieldUnoccupied(currPositionRow - 1, currPositionCol - 1)
                                    || col == currPositionCol + 1
                                    && board.isFieldUnoccupied(currPositionRow - 1, currPositionCol + 1))
                    ) || (
                            (pieceToMove == Field.fieldStatus.WHITE_MAN || pieceToMove == Field.fieldStatus.BLACK_KING)
                                    && row == currPositionRow + 1
                                    && (col == currPositionCol - 1
                                    && board.isFieldUnoccupied(currPositionRow + 1, currPositionCol - 1)
                                    || col == currPositionCol + 1
                                    && board.isFieldUnoccupied(currPositionRow + 1, currPositionCol + 1))
                    );
            boolean isJumpTopLeft =
                    (pieceToMove == Field.fieldStatus.BLACK_MAN || pieceToMove == Field.fieldStatus.WHITE_KING)
                            && row == currPositionRow - 2
                            && col == currPositionCol - 2
                            && board.isFieldOfPlayer(!isBlacksTurn, currPositionRow - 1, currPositionCol - 1)
                            && board.isFieldUnoccupied(row, col);
            boolean isJumpTopRight =
                    (pieceToMove == Field.fieldStatus.BLACK_MAN || pieceToMove == Field.fieldStatus.WHITE_KING)
                            && row == currPositionRow - 2
                            && col == currPositionCol + 2
                            && board.isFieldOfPlayer(!isBlacksTurn, currPositionRow - 1, currPositionCol + 1)
                            && board.isFieldUnoccupied(row, col);
            boolean isJumpBottomLeft =
                    (pieceToMove == Field.fieldStatus.WHITE_MAN || pieceToMove == Field.fieldStatus.BLACK_KING)
                            && row == currPositionRow + 2
                            && col == currPositionCol - 2
                            && board.isFieldOfPlayer(!isBlacksTurn, currPositionRow + 1, currPositionCol - 1)
                            && board.isFieldUnoccupied(row, col);
            boolean isJumpBottomRight =
                    (pieceToMove == Field.fieldStatus.WHITE_MAN || pieceToMove == Field.fieldStatus.BLACK_KING)
                            && row == currPositionRow + 2
                            && col == currPositionCol + 2
                            && board.isFieldOfPlayer(!isBlacksTurn, currPositionRow + 1, currPositionCol + 1)
                            && board.isFieldUnoccupied(row, col);
            if (isNormalMove && !jumped) {
                board.setFieldPiece(row, col, pieceToMove);
                board.setFieldPiece(startPositionRow, startPositionCol, Field.fieldStatus.BLANK);
                board.fieldToNormalColor(startPositionRow, startPositionCol);
                startPositionChosen = false;
                isBlacksTurn = !isBlacksTurn;
                view.setTurnLabel(isBlacksTurn);
                return true;
            } else if (isJumpTopLeft) {
                board.fieldToRemoveColor(currPositionRow - 1, currPositionCol - 1);
                board.fieldToSelectColor(row, col);
                toRemove.add(new int[]{currPositionRow - 1, currPositionCol - 1});
                toMove.add(new int[]{row, col});
            } else if (isJumpTopRight) {
                board.fieldToRemoveColor(currPositionRow - 1, currPositionCol + 1);
                board.fieldToSelectColor(row, col);
                toRemove.add(new int[]{currPositionRow - 1, currPositionCol + 1});
                toMove.add(new int[]{row, col});
            } else if (isJumpBottomLeft) {
                board.fieldToRemoveColor(currPositionRow + 1, currPositionCol - 1);
                board.fieldToSelectColor(row, col);
                toRemove.add(new int[]{currPositionRow + 1, currPositionCol - 1});
                toMove.add(new int[]{row, col});
            } else if (isJumpBottomRight) {
                board.fieldToRemoveColor(currPositionRow + 1, currPositionCol + 1);
                board.fieldToSelectColor(row, col);
                toRemove.add(new int[]{currPositionRow + 1, currPositionCol + 1});
                toMove.add(new int[]{row, col});
            } else {
                view.setErrorLabel("Illegal Move");
                return false;
            }
            view.setMoveButtonVisible(true);
            jumped = true;
            currPositionRow = row;
            currPositionCol = col;
        }
        return true;
    }

    public void move() {
        view.setMoveButtonVisible(false);
        board.fieldToNormalColor(startPositionRow, startPositionCol);
        board.fieldToNormalColor(toMove.get(0)[0], toMove.get(0)[1]);
        board.fieldToNormalColor(toRemove.get(0)[0], toRemove.get(0)[1]);
        board.setFieldPiece(toMove.get(0)[0], toMove.get(0)[1], board.getFieldStatus(startPositionRow, startPositionCol));
        board.setFieldPiece(startPositionRow, startPositionCol, Field.fieldStatus.BLANK);
        board.setFieldPiece(toRemove.get(0)[0], toRemove.get(0)[1], Field.fieldStatus.BLANK);
        for (int i = 1; i < toRemove.size(); i++) {
            board.fieldToNormalColor(toRemove.get(i)[0], toRemove.get(i)[1]);
            board.fieldToNormalColor(toMove.get(i)[0], toMove.get(i)[1]);
            board.setFieldPiece(toMove.get(i)[0], toMove.get(i)[1],
                    board.getFieldStatus(toMove.get(i - 1)[0], toMove.get(i - 1)[1]));
            board.setFieldPiece(toRemove.get(i)[0], toRemove.get(i)[1], Field.fieldStatus.BLANK);
            board.setFieldPiece(toMove.get(i - 1)[0], toMove.get(i - 1)[1], Field.fieldStatus.BLANK);
        }
        toMove.clear();
        toRemove.clear();
        jumped = startPositionChosen = false;
        isBlacksTurn = !isBlacksTurn;
        view.setTurnLabel(isBlacksTurn);
    }
}
