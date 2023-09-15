package model;

import view.Board;

public class Model {
    Game currentGame;

    public void saveNewGame(Board board) {
        this.currentGame = new Game(board);
    }

    public boolean isGameRunning() {
        return currentGame != null;
    }
}
