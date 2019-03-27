package   task3702;

import   task3702.female.FemaleFactory;
import   task3702.male.MaleFactory;

public class FactoryProducer {
    public static enum HumanFactoryType  {MALE, FEMALE};
    public static AbstractFactory getFactory (HumanFactoryType factoryType) {
      if (factoryType == HumanFactoryType.FEMALE) return new FemaleFactory();
      if (factoryType == HumanFactoryType.MALE) return new MaleFactory();
      return null;
    }
}
