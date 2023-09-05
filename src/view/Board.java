package src.view;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    private final View view;
    private final Field[][] fields;
    private final int length;
    private final Color blackFieldColor;
    private final Color whiteFieldColor;
    private final Sheet sheet;

    public Board(int length, Color blackFieldColor, Color whiteFieldColor, Sheet sheet, View view) {
        this.length = length;
        this.blackFieldColor = blackFieldColor;
        this.whiteFieldColor = whiteFieldColor;
        this.sheet = sheet;
        this.fields = new Field[length][length];
        this.view = view;

        setLayout(new GridLayout(length, length));
        setBackground(Color.gray);
        setupFields();
    }

    public void setupFields() {
        removeAll();
        boolean isFieldWhite = true;
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {
                Field field;
                if (isFieldWhite) {
                    field = new Field(Field.FieldStatus.BLANK, whiteFieldColor, sheet, this, row, col);
                } else {
                    if (row < (length / 2 - 1)) {
                        field = new Field(Field.FieldStatus.WHITE_MAN, blackFieldColor, sheet, this, row, col);
                    } else if (row > length / 2) {
                        field = new Field(Field.FieldStatus.BLACK_MAN, blackFieldColor, sheet, this, row, col);
                    } else {
                        field = new Field(Field.FieldStatus.BLANK, blackFieldColor, sheet, this, row, col);
                    }
                }
                isFieldWhite = !isFieldWhite;
                add(field);
                fields[row][col] = field;
            }
            isFieldWhite = !isFieldWhite;
        }
    }

    public void setFieldPiece(int row, int col, Field.FieldStatus fieldStatus) {
        fields[row][col].setPiece(fieldStatus);
    }

    public void fieldToSelectColor(int row, int col) {
        fields[row][col].toSelectColor();
    }

    public void fieldToNormalColor(int row, int col) {
        fields[row][col].toNormalColor();
    }

    public void fieldToRemoveColor(int row, int col) {
        fields[row][col].toRemoveColor();
    }

    public Field.FieldStatus getFieldStatus(int row, int col) {
        return fields[row][col].getOccupiedBy();
    }

    public View getView() {
        return view;
    }

    public boolean isFieldOfPlayer(boolean isBlack, int row, int col) {
        if (row >= 0 && row < fields.length && col >= 0 && col < fields[0].length) {
            return isBlack && (getFieldStatus(row, col) == Field.FieldStatus.BLACK_MAN ||
                    getFieldStatus(row, col) == Field.FieldStatus.BLACK_MAN) ||
                    !isBlack && (getFieldStatus(row, col) == Field.FieldStatus.WHITE_MAN ||
                            getFieldStatus(row, col) == Field.FieldStatus.WHITE_MAN);
        }
        return false;
    }

    public boolean isFieldUnoccupied(int row, int col) {
        if (row >= 0 && row < fields.length && col >= 0 && col < fields[0].length) {
            return fields[row][col].getOccupiedBy() == Field.FieldStatus.BLANK;
        }
        return false;
    }
}
