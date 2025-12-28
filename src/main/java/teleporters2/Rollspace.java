package teleporters2;

import java.util.List;
import java.util.stream.IntStream;

class Rollspace {

  private List<Roll> rolls;
  private int sides;

  Rollspace(int sides) {
    this.sides = sides;
  }

  List<Roll> rolls() {
    return IntStream.rangeClosed(1, sides)
      .mapToObj(i -> new Roll(i))
      .toList();
  }
}
