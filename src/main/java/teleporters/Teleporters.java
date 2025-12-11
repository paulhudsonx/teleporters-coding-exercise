package teleporters;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Suppliers;

public class Teleporters {
  static int[] destinations(String[] teleports, int nDieSides, int from, int nBoardSize) {
    return new GameEngine(teleports, nBoardSize)
      .endPositions(from, nDieSides)
      .stream()
      .mapToInt(Integer::intValue)
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

  static class GameEngine {

    private final Teleport teleport;
    private final int boardSize;

    public GameEngine(String [] teleports, int boardSize) {
      this(new Teleport(teleports), boardSize);
    }
    public GameEngine(Teleport teleport, int boardSize) {
      this.teleport = teleport;
      this.boardSize = boardSize;
    }

    public List<Integer> endPositions(int startTile, int dieSides) {
      return IntStream.rangeClosed(1, dieSides)
        .mapToObj(roll -> endPosition(startTile, roll))
        .distinct()
        .toList();
    }

    public int endPosition(int startTile, int roll) {
      final int tileAfterMove = boundToBoardSize(startTile + roll);
      return boundToBoardSize(teleport.from(tileAfterMove));
    }

    private int boundToBoardSize(int tile) {
      return Math.min(tile, boardSize);
    }
  }
}