package src.view;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    private final Field[][] fields;
    private final int length;
    private final Color blackFieldColor;
    private final Color whiteFieldColor;
    private final Sheet sheet;
    public Board(int length, Color blackFieldColor, Color whiteFieldColor, Sheet sheet) {
        this.length = length;
        this.blackFieldColor = blackFieldColor;
        this.whiteFieldColor = whiteFieldColor;
        this.sheet = sheet;
        this.fields = new Field[length][length];

        setLayout(new GridLayout(length, length));
        setBackground(Color.gray);
        setupFields();
    }

    private void setupFields() {
        boolean isWhite = true;
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {
                Field field;
                if (isWhite) {
                    field = new Field(Field.fieldStatus.BLANK, whiteFieldColor, sheet);
                } else {
                    if (row < (length / 2 - 1)) {
                        field = new Field(Field.fieldStatus.BLACK_MAN, blackFieldColor, sheet);
                    } else if (row > length / 2) {
                        field = new Field(Field.fieldStatus.WHITE_MAN, blackFieldColor, sheet);
                    } else {
                        field = new Field(Field.fieldStatus.BLANK, blackFieldColor, sheet);
                    }
                }
                isWhite = !isWhite;
                add(field);
                fields[row][col] = field;
            }
            isWhite = !isWhite;
        }
    }

    public void setFieldPiece(int row, int col, Field.fieldStatus fieldStatus) {
        fields[row][col].setPiece(fieldStatus);
    }
}
