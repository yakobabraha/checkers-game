package src.view;

import javax.swing.*;
import java.awt.*;

public class Field extends JButton {
    public enum fieldStatus {BLANK, BLACK_MAN, WHITE_MAN, BLACK_KING, WHITE_KING}

    private fieldStatus occupiedBy;
    private final Sheet sheet;

    public Field(fieldStatus occupiedBy, Color background, Sheet sheet) {
        setBackground(background);
        this.sheet = sheet;
        setPiece(occupiedBy);
    }

    public void setPiece(fieldStatus occupiedBy) {
        this.occupiedBy = occupiedBy;
        switch (occupiedBy) {
            case BLANK -> setIcon(null);
            case BLACK_MAN -> setIcon(sheet.getBlackManIcon());
            case WHITE_MAN -> setIcon(sheet.getWhiteManIcon());
            case BLACK_KING -> setIcon(sheet.getBlackKingIcon());
            case WHITE_KING -> setIcon(sheet.getWhiteKingIcon());
        }
    }

    public fieldStatus getOccupiedBy() {
        return occupiedBy;
    }
}
