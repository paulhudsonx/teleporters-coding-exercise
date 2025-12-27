package teleporters2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import teleporters2.GameFactoryBuilder.GameFactory;
import teleporters2.GameFactoryBuilder.GameFactory.Tile;
import teleporters2.Teleporters2.Die.Roll;
import teleporters2.Teleporters2.Rollspace;

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
    Tile tile1 = gameFactory.tile(1);
    Tile tile2 = gameFactory.tile(2);
    assertThat(tile1).isNotEqualTo(tile2);
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
    assertThatThrownBy(() -> gameFactory.tile(0))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Tile index less than 1");
  }

  @Test
  public void testRollspace() {
    assertThat(new Rollspace(2).rolls()).contains(new Roll(1), new Roll(2));
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
