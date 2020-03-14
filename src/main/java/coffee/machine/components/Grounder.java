package coffee.machine.components;

import coffee.machine.ingredients.CoffeeGrain;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(staticName = "create")
public class Grounder {

    public CoffeeGrain ground(CoffeeGrain coffeeGrain) {
        checkCoffeeGrainGrounded(coffeeGrain);
        return CoffeeGrain.of(coffeeGrain.getAmount(), true);
    }

    private void checkCoffeeGrainGrounded(CoffeeGrain coffeeGrain) {
        if (coffeeGrain.isGrounded()) {
            throw new AssertionError("Coffee grain already grounded");
        }
    }

}
