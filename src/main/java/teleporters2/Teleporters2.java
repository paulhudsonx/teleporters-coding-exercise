package teleporters2;

import teleporters2.GameBoard.Tile;
import teleporters2.GameBoard.Tiles;


public class Teleporters2 {

  static int[] destinations(String[] teleports, int nDieSides, int from, int nBoardSize) {
    GameBoard gameBoard = new GameBoard(gb -> gb.withTeleporters(teleports).withBoardSize(nBoardSize));
    RollSpace rollspace = new RollSpace(nDieSides);
    Tile startTile = gameBoard.tile(from);
    Tiles tiles = gameBoard.reachableTiles(startTile, rollspace);
    return gameBoard.toIntArray(tiles);
  }
}
