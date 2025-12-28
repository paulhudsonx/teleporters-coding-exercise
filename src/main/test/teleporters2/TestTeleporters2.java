package teleporters2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import teleporters2.GameFactoryBuilder.GameFactory;
import teleporters2.GameFactoryBuilder.GameFactory.Tile;
import teleporters2.Teleport.TeleportPath;

public class TestTeleporters2 {


  @Test
  public void testBuildTileWithIndex1() {
    GameFactory gameFactory = new GameFactoryBuilder()
      .withBoardSize(2)
      .build();
    Tile tile = gameFactory.tile(1);
    assertThat(tile).isEqualTo(gameFactory.tile(1));
  }

  @Test
  public void testBuildTileWithIndex2() {
    GameFactory gameFactory = new GameFactoryBuilder()
      .withBoardSize(2)
      .build();
    Tile tile0 = gameFactory.tile(0);
    Tile tile2 = gameFactory.tile(2);
    assertThat(tile0).isNotEqualTo(tile2);
  }

  @Test
  public void testTileIndexShouldBeUpperBoundedByBoardSize() {
    GameFactory gameFactory = new GameFactoryBuilder()
      .withBoardSize(2)
      .build();
    assertThatThrownBy(() -> gameFactory.tile(3))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Tile index greater than board size");
  }

  @Test
  public void testTileIndexShouldBeLowerBoundedBy1() {
    GameFactory gameFactory = new GameFactoryBuilder()
      .withBoardSize(2)
      .build();
    assertThatThrownBy(() -> gameFactory.tile(-1))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Tile index less than 0");
  }

  @Test
  public void testGameFactoryRollspace() {
    Rollspace rollspace = new GameFactoryBuilder()
      .withDieSides(2)
      .build()
      .rollspace();
    assertThat(rollspace.rolls())
      .map(Roll::roll)
      .contains(1, 2);
  }

  @Test
  public void shouldConstructTeleportPath() {
    TeleportPath teleportPath = new TeleportPath("1,3");
    assertEquals(1, teleportPath.from());
    assertEquals(3, teleportPath.to());
  }

  @Test
  public void shouldTeleportFromMultipleLocations() {
    Teleport teleport = new Teleport("1,3", "4,1");
    assertThat(teleport.from(1)).isEqualTo(3);
    assertThat(teleport.from(2)).isEqualTo(2);
    assertThat(teleport.from(3)).isEqualTo(3);
    assertThat(teleport.from(4)).isEqualTo(1);
  }

  @Test
  public void shouldConstructTilesWithTeleport() {
    GameFactory gameFactory = new GameFactoryBuilder()
      .withTeleporters("1,3", "3,5")
      .withBoardSize(6)
      .withStartPosition(0)
      .withDieSides(6)
      .build();

    Tile tile1 = gameFactory.tile(1);
    assertThat(tile1.jump()).isEqualTo(gameFactory.tile(3));
    assertThat(gameFactory.tile(5)).isEqualTo(tile1.jump().jump());
  }


  @Test
  public void testRollspacePossibleMoves() {
    GameFactory gameFactory = new GameFactoryBuilder()
      .withBoardSize(6)
      .withStartPosition(0)
      .withDieSides(6)
      .build();

    //Destination destination = new Destination();
    Tile start = gameFactory.tile(0);
    //destination.possibilities(start, gameFactory.rollspace()) TODO

  }

  @Test
  public void testTransform() {
    String[] teleporters5 = {"3,8", "8,9", "9,3"};
    int dieSides = 6;
    int startPosition;
    int boardSize;

    //assertThat(ints(1, 2, 10, 6)).containsExactlyInAnyOrder(destinations(teleporters1, 6, 0, 12));

  }
}
