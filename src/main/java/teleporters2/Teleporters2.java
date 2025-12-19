package teleporters2;

import java.util.List;

import teleporters2.Teleporters2.Die.Side.Print;

public class Teleporters2 {


  public interface Die {

    interface Bias {

      int randomise(int i, int size);
      class Fixed implements Bias {

        @Override
        public int randomise(int from, int to) {
          return 0;
        }
      }
    }

    public interface Roll {
      Side sideUp();
    }

    Roll roll();

    interface Side {
      void export(Print print);

      interface Print {
        void value(int value);
      }
    }

    interface Sides {
      List<? extends Side> list();

      void export(Print print);

      default Side pick(Bias bias) {
        List<? extends Side> sides = list();
        return sides.get(bias.randomise(1, sides.size()) - 1);
      }
    }

    Sides sides();
  }


  static class Teleport {

    public Tile from(Tile from) {
      return null;
    }
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
