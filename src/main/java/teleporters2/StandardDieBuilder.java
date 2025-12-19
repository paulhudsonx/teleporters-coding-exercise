package teleporters2;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import teleporters2.Teleporters2.Die;
import teleporters2.Teleporters2.Die.Bias;
import teleporters2.Teleporters2.Die.Side;
import teleporters2.Teleporters2.Die.Side.Print;
import teleporters2.Teleporters2.Die.Sides;

public class StandardDieBuilder implements DieBuilder {

  private int sides = 0;
  private Bias bias;

  @Override
  public Bias bias() {
    if (bias == null)
      throw new IllegalStateException("Bias must be set");
    return bias;
  }
  @Override
  public StandardDieBuilder withSides(int sides) {
    this.sides = sides;
    return this;
  }

  @Override
  public Sides buildSides() {
    List<SideImpl> sides = IntStream.rangeClosed(1, this.sides).mapToObj(SideImpl::new).toList();
    return new SidesImpl(sides);
  }

  @Override
  public DieBuilder withBias(Bias bias) {
    this.bias = bias;
    return this;
  }

  static class StandardDie implements Die {

    private final Sides sides;
    private final Bias bias;

    StandardDie(Function<DieBuilder, DieBuilder> dieBuilderFunction) {
      this(dieBuilderFunction.apply(new StandardDieBuilder()));
    }

    private StandardDie(DieBuilder dieBuilder) {
      this(dieBuilder.buildSides(), dieBuilder.bias());
    }


    private StandardDie(Sides sides, Bias bias) {
      this.sides = sides;
      this.bias = bias;
    }

    @Override
    public Roll roll() {
      Side side = sides.pick(bias);
      return () -> side;
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
