package teleporters2;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import teleporters2.StandardDieBuilder.StandardDie;
import teleporters2.Teleporters2.Die;
import teleporters2.Teleporters2.Die.Roll;
import teleporters2.Teleporters2.Teleport;
import teleporters2.Teleporters2.Tile;

public class TestTeleporters2 {

  @Test
  public void testBuildSixSidedStandardDie() {
    Die die = new StandardDie(dieBuilder -> dieBuilder.withSides(6));
    assertThat(die.sides().list()).hasSize(6);
  }

  @Test
  public void testStandardDieSides() {

    Die die = new StandardDie(dieBuilder -> dieBuilder.withSides(6));
    List<Integer> values = new ArrayList<>();
    die.sides().export(values::add);
    assertThat(values).contains(1, 2, 3, 4, 5, 6);
  }

  @Test
  public void testBias() {

    Die die = new StandardDie(dieBuilder -> dieBuilder
      .withSides(6)
      .withBias((from, to) -> 3));
    Roll roll = die.roll();
    List<Integer> values = new ArrayList<>();
    roll.sideUp().export(values::add);
    assertThat(values).contains(3);
  }

  @Test
  public void testTeleport() {
    Teleport teleport = new TeleportBuilder()
      .build();

    Tile from = new Tile();

    Tile to = teleport.from(from);

  }
}
