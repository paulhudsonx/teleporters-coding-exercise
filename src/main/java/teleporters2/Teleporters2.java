package teleporters2;

import java.util.List;

import teleporters2.Teleporters2.Die.Side.Print;

public class Teleporters2 {

  public interface Die {
    interface Side {
      void export(Print print);

      interface Print {
        void value(int value);
      }
    }

    interface Sides {
      List<? extends Side> list();

      void export(Print print);
    }

    Sides sides();
  }


  static class Teleport {

  }

  static class Teleports {

  }

  static class Tile {

  }

  static class Tiles {

  }

  static class GameBoard {
    private final Tiles tiles;
    private final Die die;

    GameBoard(Tiles tiles, Die die) {
      this.tiles = tiles;
      this.die = die;
    }

    Tiles endPositions(Tile start) {
      return new Tiles();
    }
  }
}
