package   AbstractFactory.female;

import   AbstractFactory.AbstractFactory;
import   AbstractFactory.Human;

public class FemaleFactory implements AbstractFactory {
    public  Human getPerson(int age){
        if (age <= KidGirl.MAX_AGE)  return new KidGirl();
        else if (age <= TeenGirl.MAX_AGE) return new TeenGirl();
        else return new Woman();
    }
}
