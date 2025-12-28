package teleporters2;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

public class GameFactoryBuilder {
  private String[] teleporters = {};
  private int dieSides = 6;
  private int startPosition = 1;
  private int boardSize;

  GameFactoryBuilder withTeleporters(String... teleporters) {
    this.teleporters = teleporters;
    return this;
  }

  GameFactoryBuilder withDieSides(int dieSides) {
    this.dieSides = dieSides;
    return this;
  }

  GameFactoryBuilder withStartPosition(int startPosition) {
    this.startPosition = startPosition;
    return this;
  }

  GameFactoryBuilder withBoardSize(int boardSize) {
    this.boardSize = boardSize;
    return this;
  }

  GameFactory build() {
    // TODO Validate config
    return new GameFactory(this);
  }

  static class GameFactory {
    private final String[] teleporters;
    private final int dieSides;
    private final int boardSize;
    private final HashMap<Integer, Tile> tileMap = new HashMap<>();
    private final Supplier<Teleport> teleport = Suppliers.memoize(this::teleport);

    private GameFactory(GameFactoryBuilder gameConfig) {
      this.teleporters = gameConfig.teleporters;
      this.dieSides = gameConfig.dieSides;
      this.boardSize = gameConfig.boardSize;
    }

    Teleport teleport() {
      return new Teleport(teleporters);
    }

    public Tile tile(int index) {
      if (index < 0)
        throw new IllegalArgumentException("Tile index less than 0");
      if (index > boardSize)
        throw new IllegalArgumentException("Tile index greater than board size");
      return tileMap.computeIfAbsent(index, Tile::new);
    }

    public Destination destination() {

      Teleport teleport = new Teleport(teleporters);

      return null;
    }

    public Rollspace rollspace() {
      return new Rollspace(dieSides);
    }

    public int toIndex(Tile tile) {
      return tile.index;
    }

    static class Tiles {
      private List<Tile> tiles;
    }


    class Tile {
      private Integer index;
      private Supplier<Tile> jump = Suppliers.memoize(this::doJump);

      private Tile doJump() {
        return GameFactory.this.tile(teleport.get().from(index));
      }

      public Tile(Integer index) {
        this.index = index;
      }

      public Tile jump() {
        return jump.get();
      }
    }

    public int[] toIntArray(Tiles tiles) {
      return tiles.tiles.stream()
        .mapToInt(t -> t.index)
        .toArray();
    }


    static class Destination {

      private final List<Tile> tiles;

      Destination(List<Tile> tiles) {
        this.tiles = tiles;
      }

      Tiles possibilities(Tile start, Rollspace rollspace) {
        return null;
      }
    }
  }
}
