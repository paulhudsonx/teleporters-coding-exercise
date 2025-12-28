package teleporters2;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Suppliers;

class Teleport {

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
