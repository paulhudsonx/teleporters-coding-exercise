package teleporters2;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import teleporters2.GameFactoryBuilder.GameFactory;
import teleporters2.GameFactoryBuilder.GameFactory.Tile;
import teleporters2.GameFactoryBuilder.GameFactory.Tiles;
import teleporters2.Teleporters2.Die.Roll;
import teleporters2.Teleporters2.Die.Side.Print;

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


  static class Rollspace {
    private List<Roll> rolls;
    private int sides;

    Rollspace(int sides) {
      this.sides = sides;
    }

    List<Roll> rolls() {
      return IntStream.rangeClosed(1, sides)
        .mapToObj(Roll::new)
        .toList();
    }
  }

  static class Game {

    public Game(Die die) {

    }

    public Scenarios scenarios() {
      return null;
    }
  }

  static class Scenarios {

  }

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

    public class Roll {
      private final int roll;

      public Roll(int roll) {
        this.roll = roll;
      }

      int roll() {
        return roll;
      }

      @Override
      public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }
        Roll roll1 = (Roll) o;
        return roll == roll1.roll;
      }

      @Override
      public int hashCode() {
        return Objects.hash(roll);
      }
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
    private final Map<Tile, Tile> jumps;

    Teleport(Map<Tile, Tile> jumps) {
      this.jumps = jumps;
    }

    public Tile from(Tile from) {
      return jumps.get(from);
    }
  }

  static class Teleports {

  }



  static class Destination {
    private final List<Tile> tiles;
    private final Die die;

    Destination(List<Tile> tiles, Die die) {
      this.tiles = tiles;
      this.die = die;
    }

    Tiles possibilities(Tile start, Rollspace rollspace) {
      return null;
    }
  }
}
