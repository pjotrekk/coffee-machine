package coffee.machine.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(staticName = "of")
public class Grounder {

    private final SolidTank coffeeTank;

    private final CoffeePot coffeePot;

    public void ground(int amount) {
        coffeeTank.reduceAmount(amount);
        coffeePot.addGroundedCoffee(amount);
    }

}
