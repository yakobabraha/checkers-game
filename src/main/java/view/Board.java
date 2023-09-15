package view;

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
                JButton button = new JButton();
                Field field;
                if (isFieldWhite) {
                    field = new Field(Field.FieldStatus.BLANK, whiteFieldColor, sheet, this, row, col, button);
                } else {
                    if (row < (length / 2 - 1)) {
                        field = new Field(Field.FieldStatus.WHITE_MAN, blackFieldColor, sheet, this, row, col,
                                button);
                    } else if (row > length / 2) {
                        field = new Field(Field.FieldStatus.BLACK_MAN, blackFieldColor, sheet, this, row, col,
                                button);
                    } else {
                        field = new Field(Field.FieldStatus.BLANK, blackFieldColor, sheet, this, row, col,
                                button);
                    }
                }
                isFieldWhite = !isFieldWhite;
                add(button);
                fields[row][col] = field;
            }
            isFieldWhite = !isFieldWhite;
        }
    }

    //build Board by int structure: 0 -> blank, 1 -> black Man, 2 -> white Man, 3 -> black King, 4 -> white King
    public static Board buildBoard(int[][] structure) {
        int rows = structure.length;
        int columns = rows == 0 ? 0 : structure[0].length;
        if (rows != columns) throw new IllegalArgumentException("Board is not a square");
        Board board = new Board(8, new Color(102, 68, 46), new Color(247, 236, 202),
                new Sheet("src/main/resources/checkers_pieces.png"), null);

        for (int row = 0; row < rows; row++) {
           for (int col = 0; col < columns; col++) {
                switch (structure[row][col]) {
                    case 0 -> board.setFieldPiece(row, col, Field.FieldStatus.BLANK);
                    case 1 -> board.setFieldPiece(row, col, Field.FieldStatus.BLACK_MAN);
                    case 2 -> board.setFieldPiece(row, col, Field.FieldStatus.WHITE_MAN);
                    case 3 -> board.setFieldPiece(row, col, Field.FieldStatus.BLACK_KING);
                    case 4 -> board.setFieldPiece(row, col, Field.FieldStatus.WHITE_KING);
                    default -> throw new IllegalArgumentException("Only 0,1,2,3,4 are allowed for field specification");
                }
           }
        }
        return board;
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

    public int getLength() {
        return length;
    }

    public boolean isFieldOfPlayer(boolean isBlack, int row, int col) {
        if (row >= 0 && row < fields.length && col >= 0 && col < fields[0].length) {
            return isBlack && (getFieldStatus(row, col) == Field.FieldStatus.BLACK_MAN ||
                    getFieldStatus(row, col) == Field.FieldStatus.BLACK_KING) ||
                    !isBlack && (getFieldStatus(row, col) == Field.FieldStatus.WHITE_MAN ||
                            getFieldStatus(row, col) == Field.FieldStatus.WHITE_KING);
        }
        return false;
    }

    public boolean isFieldUnoccupied(int row, int col) {
        if (row >= 0 && row < fields.length && col >= 0 && col < fields[0].length) {
            return fields[row][col].getOccupiedBy() == Field.FieldStatus.BLANK;
        }
        return false;
    }

    public void promoteMan(int row, int col) {
        if (row >= 0 && row < fields.length && col >= 0 && col < fields[0].length) {
            fields[row][col].promote();
        } else {
            throw new IllegalArgumentException("row/col not in bounds");
        }
    }

    public int[][] getBoardAsIntArray() {
        int[][] boardData = new int[length][length];
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {
                switch (fields[row][col].getOccupiedBy()) {
                    case BLANK -> boardData[row][col] = 0;
                    case BLACK_MAN -> boardData[row][col] = 1;
                    case WHITE_MAN -> boardData[row][col] = 2;
                    case BLACK_KING -> boardData[row][col] = 3;
                    case WHITE_KING -> boardData[row][col] = 4;
                }
            }
        }
        return boardData;
    }
}
