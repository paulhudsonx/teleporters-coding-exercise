package teleporters2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static teleporters2.Teleporters2.destinations;

import org.junit.jupiter.api.Test;

import teleporters2.GameBoard.Teleport.TeleportPath;
import teleporters2.GameBoard.Tile;
import teleporters2.GameBoard.Tiles;

public class TestTeleporters2 {


  @Test
  public void testBuildTileWithIndex1() {
    GameBoard gameFactory = new GameBoard(builder -> builder.withBoardSize(2));
    Tile tile = gameFactory.tile(1);
    assertThat(tile).isEqualTo(gameFactory.tile(1));
  }

  @Test
  public void testBuildTileWithIndex2() {
    GameBoard gameFactory = new GameBoard(builder -> builder.withBoardSize(2));
    Tile tile0 = gameFactory.tile(0);
    Tile tile2 = gameFactory.tile(2);
    assertThat(tile0).isNotEqualTo(tile2);
  }

  @Test
  public void testTileIndexShouldBeUpperBoundedByBoardSize() {
    GameBoard gameFactory = new GameBoard(builder -> builder.withBoardSize(2));
    assertThatThrownBy(() -> gameFactory.tile(3))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Tile index greater than board size");
  }

  @Test
  public void testTileIndexShouldBeLowerBoundedBy1() {
    GameBoard gameFactory = new GameBoard(builder -> builder.withBoardSize(2));
    assertThatThrownBy(() -> gameFactory.tile(-1))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Tile index less than 0");
  }

  @Test
  public void testGameFactoryRollSpace() {
    assertThat(new RollSpace(2).rolls())
      .map(Roll::roll)
      .contains(1, 2);
  }

  @Test
  public void shouldConstructTeleportPath() {
    TeleportPath teleportPath = new TeleportPath("1,3");
    assertEquals(1, teleportPath.path().getLeft());
    assertEquals(3, teleportPath.path().getRight());
  }

  @Test
  public void shouldTeleportFromMultipleLocations() {
    GameBoard.Teleport teleport = new GameBoard.Teleport("1,3", "4,1");
    assertThat(teleport.from(1)).isEqualTo(3);
    assertThat(teleport.from(2)).isEqualTo(2);
    assertThat(teleport.from(3)).isEqualTo(3);
    assertThat(teleport.from(4)).isEqualTo(1);
  }

  @Test
  public void shouldConstructTilesWithoutTeleport() {
    GameBoard gameBoard = new GameBoard(builder -> builder.withBoardSize(1));
    Tile tile1 = gameBoard.tile(1);
    assertThat(tile1.jump()).isEqualTo(gameBoard.tile(1));
  }

  @Test
  public void shouldConstructTilesWithTeleport() {
    GameBoard gameFactory = new GameBoard(builder -> builder.withTeleporters("1,3", "3,5").withBoardSize(6));
    Tile tile1 = gameFactory.tile(1);
    assertThat(tile1.jump()).isEqualTo(gameFactory.tile(3));
    assertThat(gameFactory.tile(5)).isEqualTo(tile1.jump().jump());
  }


  @Test
  public void testRollSpaceReachableTiles() {
    GameBoard gameBoard = new GameBoard(builder -> builder.withBoardSize(6));
    Tile start = gameBoard.tile(0);
    Tiles actual = gameBoard.reachableTiles(start, new RollSpace(6));
    assertThat(gameBoard.toIntArray(actual)).contains(1, 2, 3, 4, 5, 6);
  }

  @Test
  public void testSampleInputs() {
    String[] teleporters1 = {"3,1", "4,2", "5,10"};
    String[] teleporters2 = {"5,10", "6,22", "39,40", "40,49", "47,29"};
    String[] teleporters3 = {"6,18", "36,26", "41,21", "49,55", "54,52", "71,58", "74,77", "78,76", "80,73", "92,85"};
    String[] teleporters4 = {"97,93", "99,81", "36,33", "92,59", "17,3", "82,75", "4,1", "84,79", "54,4", "88,53", "91,37", "60,57", "61,7", "62,51", "31,19"};
    String[] teleporters5 = {"3,8", "8,9", "9,3"};

    assertThat(destinations(teleporters1, 6, 0, 12)).containsExactlyInAnyOrder(1, 2, 10, 6);
    assertThat(destinations(teleporters2, 6, 46, 100)).containsExactlyInAnyOrder(48, 49, 50, 51, 52, 29);
    assertThat(destinations(teleporters2, 10, 0, 50)).containsExactlyInAnyOrder(1, 2, 3, 4, 7, 8, 9, 10, 22);
    assertThat(destinations(teleporters3, 10, 95, 100)).containsExactlyInAnyOrder(96, 97, 98, 99, 100);
    assertThat(destinations(teleporters3, 10, 70, 100)).containsExactlyInAnyOrder(72, 73, 75, 76, 77, 79, 58);
    assertThat(destinations(teleporters4, 6, 0, 100)).containsExactlyInAnyOrder(1, 2, 3, 5, 6);
    assertThat(destinations(teleporters5, 7, 2, 20)).containsExactlyInAnyOrder(3, 4, 5, 6, 7, 8, 9);
  }
}
