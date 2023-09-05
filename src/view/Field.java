package src.view;

import javax.swing.*;
import java.awt.*;

public class Field extends JButton {
    public enum FieldStatus {BLANK, BLACK_MAN, WHITE_MAN, BLACK_KING, WHITE_KING}

    private FieldStatus occupiedBy;
    private final Sheet sheet;


    private final Color background;

    public Field(FieldStatus occupiedBy, Color background, Sheet sheet, Board board, int row, int col) {
        this.background = background;
        setBackground(background);
        this.sheet = sheet;
        setPiece(occupiedBy);
        addActionListener(e -> board.getView().getController().handleFieldClick(row, col, board));
    }

    public void setPiece(FieldStatus occupiedBy) {
        this.occupiedBy = occupiedBy;
        switch (occupiedBy) {
            case BLANK -> setIcon(null);
            case BLACK_MAN -> setIcon(sheet.getBlackManIcon());
            case WHITE_MAN -> setIcon(sheet.getWhiteManIcon());
            case BLACK_KING -> setIcon(sheet.getBlackKingIcon());
            case WHITE_KING -> setIcon(sheet.getWhiteKingIcon());
        }
    }

    public void toSelectColor() {
        setBackground(Color.CYAN);
    }

    public void toNormalColor() {
        setBackground(background);
    }

    public void toRemoveColor() {
        setBackground(Color.red);
    }

    public FieldStatus getOccupiedBy() {
        return occupiedBy;
    }
}
