package teleporters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Suppliers;

public class GameBoard {

  private final HashMap<Integer, Tile> tileMap = new HashMap<>();
  private final Supplier<Teleport> teleport = Suppliers.memoize(this::teleport);
  private final Config config;

  public List<Tile> tiles(int... tiles) {
    return Arrays.stream(tiles)
      .mapToObj(this::tile)
      .toList();
  }

  static class Builder {
    private String[] teleporters = {};
    private int boardSize;

    Builder withTeleporters(String... teleporters) {
      this.teleporters = teleporters;
      return this;
    }

    Builder withBoardSize(int boardSize) {
      this.boardSize = boardSize;
      return this;
    }

    public Config build() {
      return new Config(this);
    }
  }

  private static class Config {
    private final String[] teleporters;
    private final int boardSize;
    Config(Builder builder) {
      this.teleporters = builder.teleporters;
      this.boardSize = builder.boardSize;
    }
  }

  private Teleport teleport() {
    return new Teleport(config.teleporters);
  }

  public GameBoard(Function<Builder, Builder> builderFn) {
    this.config = builderFn.apply(new Builder()).build();
  }

  public Tile tile(int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Tile index less than 0");
    }
    if (index > config.boardSize) {
      throw new IllegalArgumentException("Tile index greater than board size");
    }
    return tileMap.computeIfAbsent(index, Tile::new);
  }

  public static class ReachableTiles {
    private final Tile tile;
    private final RollSpace rollSpace;

    public ReachableTiles(Tile tile, RollSpace rollSpace) {
      this.tile = tile;
      this.rollSpace = rollSpace;
    }

    public List<Tile> tiles() {
      return rollSpace.rolls()
        .stream()
        .map(tile::move)
        .distinct()
        .toList();
    }

    int[] indexes() {
      return tiles().stream().mapToInt(t -> t.index).toArray();
    }
  }

  public class Tile {

    private final Integer index;
    private final Supplier<Tile> jump = Suppliers.memoize(this::doJump);

    private Tile doJump() {
      return GameBoard.this.tile(bounded(teleport.get().from(index)));
    }

    private int bounded(int from) {
      return Math.min(from, config.boardSize);
    }

    private Tile(int index) {
      this.index = index;
    }

    Tile jump() {
      return jump.get();
    }

    public Tile move(int roll) {
      return GameBoard.this.tile(bounded(index + roll)).jump();
    }
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
        .map(TeleportPath::pair)
        .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }

    static class TeleportPath {
      private static final Pattern pattern = Pattern.compile("(\\d+),(\\d+)");
      private final String path;
      private final Supplier<Pair<Integer, Integer>> pair = Suppliers.memoize(this::pair);

      TeleportPath(String path) {
        this.path = path;
      }

      Pair<Integer, Integer> path() {
        return pair.get();
      }

      private Pair<Integer, Integer>  pair() {
        Matcher m = pattern.matcher(path);
        if (m.matches()) {
          Integer left = Integer.parseInt(m.group(1));
          Integer right = Integer.parseInt(m.group(2));
          return Pair.of(left, right);
        }
        else {
          throw new IllegalStateException("Invalid teleport path " + path);
        }
      }
    }
  }
}
