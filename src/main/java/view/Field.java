package view;

import javax.swing.*;
import java.awt.*;

public class Field {

    private final JButton button;

    public enum FieldStatus {BLANK, BLACK_MAN, WHITE_MAN, BLACK_KING, WHITE_KING}

    private FieldStatus occupiedBy;
    private final Sheet sheet;


    private final Color background;

    public Field(FieldStatus occupiedBy, Color background, Sheet sheet, Board board, int row, int col,
                 JButton button) {
        this.background = background;
        this.button = button;
        button.setBackground(background);
        this.sheet = sheet;
        setPiece(occupiedBy);
        button.addActionListener(e -> board.getView().getController().handleFieldClick(row, col, board));
    }

    public void setPiece(FieldStatus occupiedBy) {
        this.occupiedBy = occupiedBy;
        switch (occupiedBy) {
            case BLANK -> button.setIcon(null);
            case BLACK_MAN -> button.setIcon(sheet.getBlackManIcon());
            case WHITE_MAN -> button.setIcon(sheet.getWhiteManIcon());
            case BLACK_KING -> button.setIcon(sheet.getBlackKingIcon());
            case WHITE_KING -> button.setIcon(sheet.getWhiteKingIcon());
        }
    }

    public void promote() {
        if (occupiedBy == FieldStatus.BLACK_MAN) {
            setPiece(FieldStatus.BLACK_KING);
        } else if (occupiedBy == FieldStatus.WHITE_MAN) {
            setPiece(FieldStatus.WHITE_KING);
        }
    }

    public void toSelectColor() {
        button.setBackground(Color.CYAN);
    }

    public void toNormalColor() {
        button.setBackground(background);
    }

    public void toRemoveColor() {
        button.setBackground(Color.red);
    }

    public FieldStatus getOccupiedBy() {
        return occupiedBy;
    }
}
