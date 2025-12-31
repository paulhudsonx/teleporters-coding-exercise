package teleporters2;

import java.util.List;
import java.util.stream.IntStream;

final class RollSpace {
  private final int sides;

  RollSpace(int sides) {
    this.sides = sides;
  }

  List<Roll> rolls() {
    return IntStream.rangeClosed(1, sides)
      .mapToObj(Roll::new)
      .toList();
  }
}
