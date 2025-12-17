package teleporters;

import java.util.List;
import java.util.stream.IntStream;

import teleporters2.Teleporters2.Die;
import teleporters2.Teleporters2.Die.Side;
import teleporters2.Teleporters2.Die.Side.Print;
import teleporters2.Teleporters2.Die.Sides;

public class StandardDieBuilder implements DieBuilder {

  private int sides = 0;

  public StandardDieBuilder withSides(int sides) {
    this.sides = sides;
    return this;
  }


  @Override
  public Die build() {

    List<SideImpl> sides = IntStream.rangeClosed(1, this.sides).mapToObj(SideImpl::new).toList();
    return new StandardDie(new SidesImpl(sides));
  }

  private class StandardDie implements Die {

    private final Sides sides;

    private StandardDie(Sides sides) {
      this.sides = sides;
    }

    @Override
    public Sides sides() {
      return sides;
    }
  }

  private class SidesImpl implements Sides {

    private final List<? extends Side> sides;

    private SidesImpl(List<? extends Side> sides) {
      this.sides = sides;
    }

    @Override
    public List<? extends Side> list() {
      return sides;
    }

    @Override
    public void export(Print print) {
      sides.forEach(s -> s.export(print));
    }
  }

  private class SideImpl implements Side {

    private final int value;

    SideImpl(int value) {
      this.value = value;
    }

    @Override
    public void export(Print print) {
      print.value(value);
    }
  }
}
