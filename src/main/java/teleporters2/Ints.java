package teleporters2;

import java.util.Arrays;

class Ints {

  private final String path;

  Ints(String path) {
    this.path = path;
  }

  Integer[] get() {
    return Arrays.stream(path.split(","))
      .map(Integer::valueOf)
      .toArray(Integer[]::new);
  }
}
