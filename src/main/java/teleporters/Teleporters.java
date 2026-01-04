package teleporters;

import teleporters.GameBoard.ReachableTiles;
import teleporters.GameBoard.Tile;

public class Teleporters {

  static int[] destinations(String[] teleports, int dieSides, int startTileIndex, int boardSize) {
    GameBoard gameBoard = new GameBoard(gameConfig -> gameConfig.withTeleporters(teleports).withBoardSize(boardSize));
    Tile startTile = gameBoard.tile(startTileIndex);
    ReachableTiles reachableTiles = new ReachableTiles(startTile, new RollSpace(dieSides));
    return reachableTiles.indexes();
  }
}
