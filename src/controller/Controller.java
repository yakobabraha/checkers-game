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

    public void handleFieldClick(int row, int col, Board board) {
        view.showWarning("");
        this.board = board;
        if (!startPositionChosen) {
            if (board.isFieldOfPlayer(isBlacksTurn, row, col)) {
                if (!Computer.canLegalMoveAtPos(board, row, col)) {
                    view.showWarning("No legal moves!");
                    return;
                }
                if (Computer.areThereJumps(isBlacksTurn, board) && !Computer.canJumpAtPos(row, col, board)) {
                    view.showWarning("Mandatory jump!");
                    return;
                }
                startPositionRow = currPositionRow = row;
                startPositionCol = currPositionCol = col;
                board.fieldToSelectColor(row, col);
                startPositionChosen = true;
            } else if (board.getFieldStatus(row, col) != Field.FieldStatus.BLANK) {
                if (isBlacksTurn) {
                    view.showWarning("It's not White's turn");
                } else {
                    view.showWarning("It's not Black's turn");
                }
            }
        } else {
            Field.FieldStatus pieceToMove = board.getFieldStatus(startPositionRow, startPositionCol);
            boolean isNormalMove =
                    (
                            (board.isFieldOfPlayer(true, startPositionRow, startPositionCol)
                                    || pieceToMove == Field.FieldStatus.WHITE_KING)
                                    && row == currPositionRow - 1
                                    && (col == currPositionCol - 1
                                    && board.isFieldUnoccupied(currPositionRow - 1, currPositionCol - 1)
                                    || col == currPositionCol + 1
                                    && board.isFieldUnoccupied(currPositionRow - 1, currPositionCol + 1))
                    ) || (
                            (board.isFieldOfPlayer(false, startPositionRow, startPositionCol)
                                    || pieceToMove == Field.FieldStatus.BLACK_KING)
                                    && row == currPositionRow + 1
                                    && (col == currPositionCol - 1
                                    && board.isFieldUnoccupied(currPositionRow + 1, currPositionCol - 1)
                                    || col == currPositionCol + 1
                                    && board.isFieldUnoccupied(currPositionRow + 1, currPositionCol + 1))
                    );
            boolean isJumpTopLeft =
                    (board.isFieldOfPlayer(true, startPositionRow, startPositionCol)
                            || pieceToMove == Field.FieldStatus.WHITE_KING)
                            && row == currPositionRow - 2
                            && col == currPositionCol - 2
                            && board.isFieldOfPlayer(!isBlacksTurn, currPositionRow - 1, currPositionCol - 1)
                            && board.isFieldUnoccupied(row, col);
            boolean isJumpTopRight =
                    (board.isFieldOfPlayer(true, startPositionRow, startPositionCol)
                            || pieceToMove == Field.FieldStatus.WHITE_KING)
                            && row == currPositionRow - 2
                            && col == currPositionCol + 2
                            && board.isFieldOfPlayer(!isBlacksTurn, currPositionRow - 1, currPositionCol + 1)
                            && board.isFieldUnoccupied(row, col);
            boolean isJumpBottomLeft =
                    (board.isFieldOfPlayer(false, startPositionRow, startPositionCol)
                            || pieceToMove == Field.FieldStatus.BLACK_KING)
                            && row == currPositionRow + 2
                            && col == currPositionCol - 2
                            && board.isFieldOfPlayer(!isBlacksTurn, currPositionRow + 1, currPositionCol - 1)
                            && board.isFieldUnoccupied(row, col);
            boolean isJumpBottomRight =
                    (board.isFieldOfPlayer(false, startPositionRow, startPositionCol)
                            || pieceToMove == Field.FieldStatus.BLACK_KING)
                            && row == currPositionRow + 2
                            && col == currPositionCol + 2
                            && board.isFieldOfPlayer(!isBlacksTurn, currPositionRow + 1, currPositionCol + 1)
                            && board.isFieldUnoccupied(row, col);
            if (isNormalMove && !jumped) {
                if (Computer.areThereJumps(isBlacksTurn, board)) {
                    view.showWarning("Mandatory jump!");
                    return;
                }
                board.setFieldPiece(row, col, pieceToMove);
                board.setFieldPiece(startPositionRow, startPositionCol, Field.FieldStatus.BLANK);
                board.fieldToNormalColor(startPositionRow, startPositionCol);
                if (row == 0 && isBlacksTurn || row == 7 && !isBlacksTurn) {
                    board.promoteMan(row, col);
                }
                startPositionChosen = false;
                isBlacksTurn = !isBlacksTurn;
                view.setTurnLabel(isBlacksTurn);
                return;
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
                view.showWarning("Illegal Move!");
                return;
            }
            view.setMoveButtonVisible(true);
            jumped = true;
            currPositionRow = row;
            currPositionCol = col;
        }
    }

    public void move() {
        if (Computer.canJumpAtPos(currPositionRow, currPositionCol, board)) {
            view.showWarning("Mandatory jump!");
            return;
        }
        view.setMoveButtonVisible(false);
        board.fieldToNormalColor(startPositionRow, startPositionCol);
        board.fieldToNormalColor(toMove.get(0)[0], toMove.get(0)[1]);
        board.fieldToNormalColor(toRemove.get(0)[0], toRemove.get(0)[1]);
        board.setFieldPiece(toMove.get(0)[0], toMove.get(0)[1], board.getFieldStatus(startPositionRow, startPositionCol));
        board.setFieldPiece(startPositionRow, startPositionCol, Field.FieldStatus.BLANK);
        board.setFieldPiece(toRemove.get(0)[0], toRemove.get(0)[1], Field.FieldStatus.BLANK);
        for (int i = 1; i < toRemove.size(); i++) {
            board.fieldToNormalColor(toRemove.get(i)[0], toRemove.get(i)[1]);
            board.fieldToNormalColor(toMove.get(i)[0], toMove.get(i)[1]);
            board.setFieldPiece(toMove.get(i)[0], toMove.get(i)[1],
                    board.getFieldStatus(toMove.get(i - 1)[0], toMove.get(i - 1)[1]));
            board.setFieldPiece(toRemove.get(i)[0], toRemove.get(i)[1], Field.FieldStatus.BLANK);
            board.setFieldPiece(toMove.get(i - 1)[0], toMove.get(i - 1)[1], Field.FieldStatus.BLANK);
        }
        if (currPositionRow == 0 && isBlacksTurn || currPositionRow == 7 && !isBlacksTurn) {
            board.promoteMan(currPositionRow, currPositionCol);
        }
        toMove.clear();
        toRemove.clear();
        jumped = startPositionChosen = false;
        isBlacksTurn = !isBlacksTurn;
        view.setTurnLabel(isBlacksTurn);
    }

    public void switchToGame() {
        isBlacksTurn = true;
        jumped = startPositionChosen = false;
        toMove.clear();
        toRemove.clear();
        view.startNewGame();
        view.switchToPage(View.ViewPage.GAME);
    }
}
