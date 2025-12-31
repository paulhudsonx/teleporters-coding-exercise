package teleporters2;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

class GameBoard {

  private final HashMap<Integer, Tile> tileMap = new HashMap<>();
  private final Builder builder = new Builder();
  private final Supplier<Teleport> teleport = Suppliers.memoize(builder::teleport);

  public static class Builder {
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

    Teleport teleport() {
      return new Teleport(teleporters);
    }
  }

  public GameBoard(Consumer<Builder> builder) {
    builder.accept(this.builder);
  }

  public Tile tile(int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Tile index less than 0");
    }
    if (index > builder.boardSize) {
      throw new IllegalArgumentException("Tile index greater than board size");
    }
    return tileMap.computeIfAbsent(index, Tile::new);
  }

  public Tiles reachableTiles(Tile startTile, RollSpace rollSpace) {
    return new Tiles(rollSpace.rolls()
      .stream()
      .map(startTile::move)
      .distinct()
      .toList());
  }

  static class Tiles {

    private final List<Tile> tiles;

    public Tiles(List<Tile> tiles) {
      this.tiles = tiles;
    }
  }


  class Tile {

    private final Integer index;
    private final Supplier<Tile> jump = Suppliers.memoize(this::doJump);

    private Tile doJump() {
      return GameBoard.this.tile(bounded(teleport.get().from(index)));
    }

    private int bounded(int from) {
      return Math.min(from, builder.boardSize);
    }

    public Tile(Integer index) {
      this.index = index;
    }

    public Tile jump() {
      return jump.get();
    }

    public Tile move(Roll r) {
      return GameBoard.this.tile(bounded(index + r.roll())).jump();
    }
  }

  public int[] toIntArray(Tiles tiles) {
    return tiles.tiles.stream()
      .mapToInt(t -> t.index)
      .toArray();
  }
}
