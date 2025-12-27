package teleporters2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Suppliers;

import teleporters2.Teleporters2.Destination;
import teleporters2.Teleporters2.Rollspace;

public class GameFactoryBuilder {
  private String[] teleporters = {};
  private int dieSides = 6;
  private int startPosition = 1;
  private int boardSize;

  GameFactoryBuilder withTeleporters(String [] teleporters) {
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

    private GameFactory(GameFactoryBuilder gameConfig) {
      this.teleporters = gameConfig.teleporters;
      this.dieSides = gameConfig.dieSides;
      this.boardSize = gameConfig.boardSize;
    }

    public Tile tile(int index) {
      if (index < 1)
        throw new IllegalArgumentException("Tile index less than 1");
      if (index > boardSize)
        throw new IllegalArgumentException("Tile index greater than board size");
      return tileMap.computeIfAbsent(index, Tile::new);
    }

    public Destination destination() {
      return null;
    }

    public Rollspace rollspace() {
      return null;
    }

    public int toIndex(Tile tile) {
      return tile.index;
    }

    static class Tiles {
      private List<Tile> tiles;
    }


    static class Tile {
      private Integer index;

      public Tile(Integer index) {
        this.index = index;
      }
    }

    public int[] toIntArray(Tiles tiles) {
      return tiles.tiles.stream()
        .mapToInt(t -> t.index)
        .toArray();
    }

    static class Teleport {

      private final String[] teleports;
      private final Supplier<Map<Integer, Integer>> pathMap = Suppliers.memoize(
        this::teleportMap);

      Teleport(String... teleports) {
        this.teleports = teleports;
      }

      int from(int tile) {
        return pathMap.get().getOrDefault(tile, tile);
      }

      private Map<Integer, Integer> teleportMap() {
        return Arrays.stream(teleports)
          .map(TeleportPath::new)
          .collect(Collectors.toMap(TeleportPath::from, TeleportPath::to));
      }
    }

    static class Ints {
      private final String path;

      Ints(String path) {
        this.path = path;
      }

      Integer [] get() {
        return Arrays.stream(path.split(","))
          .map(Integer::valueOf)
          .toArray(Integer[]::new);
      }
    }

    static class TeleportPath {
      private final Supplier<Integer[]> path;

      TeleportPath(String path) {
        this(Suppliers.memoize(() -> new Ints(path).get()));
      }

      private TeleportPath(Supplier<Integer[]> path) {
        this.path = path;
      }

      int from() {
        return path.get()[0];
      }

      int to() {
        return path.get()[1];
      }
    }
  }
}
