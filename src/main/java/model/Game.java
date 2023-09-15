package model;

import view.Board;

import java.util.ArrayList;
import java.util.List;

public class Game {
   Board board;
   List<int[][]> boardHistory;
   public Game(Board board) {
      this.board = board;
      this.boardHistory = new ArrayList<>();
      boardHistory.add(board.getBoardAsIntArray());
   }
}
