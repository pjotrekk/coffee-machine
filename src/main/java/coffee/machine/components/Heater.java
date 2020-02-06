package coffee.machine.components;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class Heater {

    private final int temperature;

    public void heat(int amount) {}
}
