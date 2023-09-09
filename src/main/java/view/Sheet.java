package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sheet {
    private final ImageIcon whiteManIcon;
    private final ImageIcon whiteKingIcon;
    private final ImageIcon blackManIcon;
    private final ImageIcon blackKingIcon;

    public Sheet(String path) {
        try {
            BufferedImage sheet = ImageIO.read(new File(path));
            blackManIcon = new ImageIcon(sheet.getSubimage(0, 0, 75, 75));
            blackKingIcon = new ImageIcon(sheet.getSubimage(0, 75, 75, 75));
            whiteManIcon = new ImageIcon(sheet.getSubimage(75, 0, 75, 75));
            whiteKingIcon = new ImageIcon(sheet.getSubimage(75, 75, 75, 75));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageIcon getWhiteManIcon() {
        return whiteManIcon;
    }

    public ImageIcon getWhiteKingIcon() {
        return whiteKingIcon;
    }

    public ImageIcon getBlackManIcon() {
        return blackManIcon;
    }

    public ImageIcon getBlackKingIcon() {
        return blackKingIcon;
    }
}
