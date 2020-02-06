package coffee.machine.modules;

import coffee.machine.components.grounders.Grounder;
import coffee.machine.components.containers.Tank;
import coffee.machine.components.pots.CoffeePot;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor(staticName = "of")
public class CoffeeModule {
    private final Tank coffeeTank;
    private final CoffeePot coffeePot;
    private final Grounder grounder;

    public void checkCapacity(int amountNeeded) {
        checkCoffeeTankOverflow();
        checkCoffeeResources(amountNeeded);
    }

    public void ground(int amount) {
        grounder.ground(amount);
    }

    public void flipUsedCoffee() {
        coffeePot.flip();
    }

    private void checkCoffeeTankOverflow() {
        if (coffeeTank.isOverflown()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    String.format("Tank overflow. Reduce the amount" +
                            " to be below the maximum value of %dmg", coffeeTank.getCapacity()));
        }
    }

    private void checkCoffeeResources(int amountNeeded) {
        if (coffeeTank.getCurrentAmount() < amountNeeded) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    String.format("Insufficient coffee amount. Only %dmg left. You should" +
                                    "refill the coffee tank", coffeeTank.getCurrentAmount()));
        }
    }

}
