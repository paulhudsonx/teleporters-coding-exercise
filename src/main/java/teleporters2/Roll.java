package teleporters2;

import java.util.Objects;

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
