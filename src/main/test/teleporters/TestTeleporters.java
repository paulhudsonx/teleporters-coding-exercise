package teleporters;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static teleporters.Teleporters.destinations;

import java.util.List;

import org.junit.jupiter.api.Test;

import teleporters.Teleporters.GameEngine;
import teleporters.Teleporters.Teleport;
import teleporters.Teleporters.TeleportPath;


public class TestTeleporters {
  @Test
  public void shouldConstructTeleport() {
    TeleportPath teleport = new TeleportPath("1,3");
    assertEquals(1, teleport.from());
    assertEquals(3, teleport.to());
  }

  @Test
  public void shouldMoveNormallyWithoutTeleport() {
    Teleport noTeleport = new Teleport();
    GameEngine gameBoard = new GameEngine(noTeleport, 3);
    assertEquals(1, gameBoard.endPosition(0, 1));
    assertEquals(2, gameBoard.endPosition(0, 2));
    assertEquals(3, gameBoard.endPosition(0, 3));
    assertEquals(3, gameBoard.endPosition(0, 4));
    assertEquals(2, gameBoard.endPosition(1, 1));
    assertEquals(3, gameBoard.endPosition(1, 3));
  }

  @Test
  public void shouldTeleport() {
    assertEquals(1, new Teleport().from(1));
    assertEquals(1, new Teleport("1,1").from(1));
    assertEquals(3, new Teleport("1,3").from(1));
    assertEquals(3, new Teleport("1,3", "2,5").from(1));
    assertEquals(5, new Teleport("1,3", "2,5").from(2));
    assertEquals(6, new Teleport("1,3", "2,5").from(6));
  }

  @Test
  public void testMoveCombinationsWithoutTeleport() {
    Teleport noTeleport = new Teleport();
    GameEngine gameEngine = new GameEngine(noTeleport, 10);
    assertEquals(List.of(1, 2), gameEngine.endPositions(0, 2));
    assertEquals(List.of(2, 3, 4), gameEngine.endPositions(1, 3));
  }

  @Test
  public void testMoveCombinationsWithTeleport() {
    assertEquals(List.of(2), new GameEngine(new Teleport("1,2"), 10).endPositions(0, 2));
    assertEquals(List.of(2, 3), new GameEngine(new Teleport("1,2"), 10).endPositions(0, 3));
    assertEquals(List.of(2, 3), new GameEngine(new Teleport("1,2"), 10).endPositions(0, 3));
  }

  @Test
  public void shouldMoveWithTeleport() {
    Teleport teleport = new Teleport("1,3");
    GameEngine gameBoard = new GameEngine(teleport, 3);
    assertEquals(3, gameBoard.endPosition(0, 1));
  }

  @Test
  public void shouldNotMovePastEndOfBoard() {
    Teleport noTeleport = new Teleport();
    GameEngine gameBoard = new GameEngine(noTeleport, 3);
    assertEquals(3, gameBoard.endPosition(0, 4));
  }

  @Test
  public void shouldNotTeleportPastEndOfBoard() {
    Teleport teleport = new Teleport("1,4");
    GameEngine gameBoard = new GameEngine(teleport, 3);
    assertEquals(3, gameBoard.endPosition(0, 1));
  }

  @Test
  public void testSampleInputs() {
    String[] teleporters1 = {"3,1", "4,2", "5,10"};
    String[] teleporters2 = {"5,10", "6,22", "39,40", "40,49", "47,29"};
    String[] teleporters3 = {"6,18", "36,26", "41,21", "49,55", "54,52", "71,58", "74,77", "78,76", "80,73", "92,85"};
    String[] teleporters4 = {"97,93", "99,81", "36,33", "92,59", "17,3", "82,75", "4,1", "84,79", "54,4", "88,53", "91,37", "60,57", "61,7", "62,51", "31,19"};
    String[] teleporters5 = {"3,8", "8,9", "9,3"};

    assertThat(ints(1, 2, 10, 6)).containsExactlyInAnyOrder(destinations(teleporters1, 6, 0, 12));
    assertThat(ints(1, 2, 10, 6)).containsExactlyInAnyOrder(destinations(teleporters1, 6, 0, 12));
    assertThat(ints(48, 49, 50, 51, 52, 29)).containsExactlyInAnyOrder(destinations(teleporters2, 6, 46, 100));
    assertThat(ints(1, 2, 3, 4, 7, 8, 9, 10, 22)).containsExactlyInAnyOrder(destinations(teleporters2, 10, 0, 50));
    assertThat(ints(96, 97, 98, 99, 100)).containsExactlyInAnyOrder(destinations(teleporters3, 10, 95, 100));
    assertThat(ints(72, 73, 75, 76, 77, 79, 58)).containsExactlyInAnyOrder(destinations(teleporters3, 10, 70, 100));
    assertThat(ints(1, 2, 3, 5, 6)).containsExactlyInAnyOrder(destinations(teleporters4, 6, 0, 100));
    assertThat(ints(3, 4, 5, 6, 7, 8, 9)).containsExactlyInAnyOrder(destinations(teleporters5, 7, 2, 20));
  }

  private static int [] ints(int... ints) {
    return ints;
  }
}
