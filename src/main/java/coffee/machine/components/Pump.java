package coffee.machine.components;

import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class Pump {
    private final Tank tankFrom;
    private final Tank tankTo;

    public void pump(int amount) {
        tankFrom.setCurrentAmount(tankFrom.getCurrentAmount() - amount);
        tankTo.setCurrentAmount(tankTo.getCurrentAmount() + amount);
    }

}
