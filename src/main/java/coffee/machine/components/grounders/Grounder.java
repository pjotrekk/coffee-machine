package coffee.machine.components.grounders;

import coffee.machine.components.containers.Tank;
import coffee.machine.components.pots.CoffeePot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(staticName = "of")
public class Grounder {

    private final Tank coffeeTank;
    private final CoffeePot coffeePot;

    public void ground(int amount) {
        coffeeTank.setCurrentAmount(coffeeTank.getCurrentAmount() - amount);
        coffeePot.setCurrentAmount(coffeePot.getCurrentAmount() + amount);
    }

}
