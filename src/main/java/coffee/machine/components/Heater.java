package coffee.machine.components;

import coffee.machine.ingredients.Liquid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class Heater {

    private final int temperature;

    public void heat(Liquid liquid) {
        liquid.setTemperature(temperature);
    }
}
