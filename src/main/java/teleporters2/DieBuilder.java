package teleporters2;

import teleporters2.Teleporters2.Die.Bias;
import teleporters2.Teleporters2.Die.Sides;

public interface DieBuilder {
  DieBuilder withSides(int sides);
  Sides buildSides();

  DieBuilder withBias(Bias bias);
  Bias bias();
}

