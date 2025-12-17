package teleporters2;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import teleporters.StandardDieBuilder;
import teleporters2.Teleporters2.Die;

public class TestTeleporters2 {

  @Test
  public void testBuildSixSidedStandardDie() {
    Die die = new StandardDieBuilder()
      .withSides(6)
      .build();
    assertThat(die.sides().list()).hasSize(6);
  }

  @Test
  public void testStandardDieSides() {
    Die die = new StandardDieBuilder()
      .withSides(6)
      .build();
    List<Integer> values = new ArrayList<>();
    die.sides().export(values::add);
    assertThat(values).contains(1, 2, 3, 4, 5, 6);
  }
}
