package src.view;


import src.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class View {

    private final JFrame frame = new JFrame();

    private Controller controller;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel menu = new JPanel();
    private final JPanel game = new JPanel();
    private final JPanel settings = new JPanel();
    private final JPanel rules = new JPanel();

    public enum ViewPage {MENU, GAME, SETTINGS, RULES}

    private final JLabel turnLabel = new JLabel("Black's turn");
    private final JLabel warningLabel = new JLabel("                    ");
    private final Sheet sheet;
    private final JButton moveButton = new JButton("move");

    private Board board;

    public View() {
        sheet = new Sheet("res/checkers_pieces.png");
        setupFrame();
    }

    private void setupFrame() {
        int frameWidth = 1200, frameHeight = 700;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setTitle("Checkers");
        frame.setLayout(cardLayout);
        setupMenu();
        frame.add(menu, "menu");
        setupGame();
        frame.add(game, "game");
        setupSettings();
        frame.add(settings, "settings");
        setupRules();
        frame.add(rules, "rules");
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void setupMenu() {
        JLabel menuTitle = new JLabel("Checkers!");
        menuTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuTitle.setFont(new Font("ariel", Font.BOLD, 50));

        JButton gameButton = new JButton("New Game");
        gameButton.setMaximumSize(new Dimension(400, 50));
        gameButton.setFont(new Font("ariel", Font.PLAIN, 25));
        gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameButton.addActionListener(e -> controller.switchToGame());

        JButton settingsButton = new JButton("Settings");
        settingsButton.setMaximumSize(new Dimension(400, 50));
        settingsButton.setFont(new Font("ariel", Font.PLAIN, 25));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.addActionListener(e -> switchToPage(ViewPage.SETTINGS));

        JButton rulesButton = new JButton("Rules");
        rulesButton.setMaximumSize(new Dimension(400, 50));
        rulesButton.setFont(new Font("ariel", Font.PLAIN, 25));
        rulesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rulesButton.addActionListener(e -> switchToPage(ViewPage.RULES));

        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.add(menuTitle);
        menu.add(new Box.Filler(new Dimension(100, 5), new Dimension(50, 50),
                new Dimension(100, 50)));
        menu.add(gameButton);
        menu.add(new Box.Filler(new Dimension(100, 5), new Dimension(100, 100),
                new Dimension(100, 50)));
        menu.add(settingsButton);
        menu.add(new Box.Filler(new Dimension(100, 5), new Dimension(100, 100),
                new Dimension(100, 50)));
        menu.add(rulesButton);

        menu.setBackground(Color.gray);
    }

    private void setupGame() {
        game.setLayout(new BorderLayout());
        board = new Board(8, new Color(102, 68, 46), new Color(247, 236, 202), sheet,
                this);
        game.add(board, BorderLayout.CENTER);

        JPanel lineStart = new JPanel();
        lineStart.setBackground(Color.gray);
        lineStart.setPreferredSize(new Dimension(300, 300));
        lineStart.add(warningLabel);
        warningLabel.setFont(new Font("ariel", Font.PLAIN, 20));
        warningLabel.setForeground(Color.YELLOW);
        JPanel lineEnd = new JPanel();
        lineEnd.setBackground(Color.gray);
        lineEnd.setPreferredSize(new Dimension(300, 300));
        lineEnd.add(turnLabel);
        turnLabel.setFont(new Font("ariel", Font.PLAIN, 30));
        moveButton.setPreferredSize(new Dimension(200, 50));
        moveButton.setFont(new Font("ariel", Font.PLAIN, 25));
        moveButton.addActionListener(e -> controller.move());
        moveButton.setVisible(false);
        lineEnd.add(moveButton);
        JPanel pageStart = new JPanel();
        pageStart.setBackground(Color.gray);
        pageStart.setPreferredSize(new Dimension(50, 50));
        JPanel pageEnd = new JPanel();
        pageEnd.setBackground(Color.gray);
        pageEnd.setPreferredSize(new Dimension(50, 50));
        pageEnd.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton menuButton = new JButton("Back to Menu");
        menuButton.setMaximumSize(new Dimension(200, 50));
        menuButton.setFont(new Font("ariel", Font.PLAIN, 25));
        menuButton.addActionListener(e -> switchToPage(ViewPage.MENU));
        pageEnd.add(menuButton);

        game.add(lineStart, BorderLayout.LINE_START);
        game.add(lineEnd, BorderLayout.LINE_END);
        game.add(pageStart, BorderLayout.PAGE_START);
        game.add(pageEnd, BorderLayout.PAGE_END);
    }

    public void startNewGame() {
        board.setupFields();
        setTurnLabel(true);
        setMoveButtonVisible(false);
        showWarning("");
    }

    private void setupSettings() {
        //dummy...
        JLabel settingTitle = new JLabel("... The settings ...");
        settingTitle.setFont(new Font("ariel", Font.BOLD, 50));
        JButton menuButton = new JButton("Back to Menu");
        menuButton.setMaximumSize(new Dimension(200, 50));
        menuButton.setFont(new Font("ariel", Font.PLAIN, 25));
        menuButton.addActionListener(e -> switchToPage(ViewPage.MENU));
        settings.add(settingTitle);
        settings.add(menuButton);
    }

    private void setupRules() {
        //dummy...
        JLabel rulesTitle = new JLabel("... The rules ...");
        rulesTitle.setFont(new Font("ariel", Font.BOLD, 50));
        JButton menuButton = new JButton("Back to Menu");
        menuButton.setMaximumSize(new Dimension(200, 50));
        menuButton.setFont(new Font("ariel", Font.PLAIN, 25));
        menuButton.addActionListener(e -> switchToPage(ViewPage.MENU));
        rules.add(rulesTitle);
        rules.add(menuButton);
    }


    public void switchToPage(ViewPage viewPage) {
        switch (viewPage) {
            case MENU -> cardLayout.show(frame.getContentPane(), "menu");
            case GAME -> cardLayout.show(frame.getContentPane(), "game");
            case SETTINGS -> cardLayout.show(frame.getContentPane(), "settings");
            case RULES -> cardLayout.show(frame.getContentPane(), "rules");
        }
    }

    public void showWarning(String warning) {
        if (warning.equals("")) {
            warningLabel.setText("                    ");
        } else {
            warningLabel.setText("Warning: " + warning);
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public void setTurnLabel(boolean isBlacksTurn) {
        if (isBlacksTurn) {
            turnLabel.setText("Black's turn");
        } else {
            turnLabel.setText("White's turn");
        }
    }


    public void setMoveButtonVisible(boolean visible) {
        moveButton.setVisible(visible);
    }
}
