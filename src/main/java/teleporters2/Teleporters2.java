package teleporters2;

import teleporters2.GameFactoryBuilder.GameFactory;
import teleporters2.GameFactoryBuilder.GameFactory.Destination;
import teleporters2.GameFactoryBuilder.GameFactory.Tile;
import teleporters2.GameFactoryBuilder.GameFactory.Tiles;


public class Teleporters2 {

  static int[] destinations(String[] teleports, int nDieSides, int from, int nBoardSize) {
    GameFactory gameFactory = new GameFactoryBuilder()
      .withBoardSize(nBoardSize)
      .withDieSides(nDieSides)
      .withTeleporters(teleports)
      .build();

    Tile startTile = gameFactory.tile(from);
    Destination destination = gameFactory.destination();
    Rollspace rollspace = gameFactory.rollspace();
    Tiles tiles = destination.possibilities(startTile, rollspace);

    return gameFactory.toIntArray(tiles);
  }
}
