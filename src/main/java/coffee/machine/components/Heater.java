package coffee.machine.components;

import coffee.machine.ingredients.Milk;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class Heater {

    private final int temperature;

    public Milk heat(Milk milk) {
        return Milk.of(milk.getAmount(), temperature, milk.isFoamed());
    }

}
