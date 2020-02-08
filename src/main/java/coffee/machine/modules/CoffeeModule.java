package coffee.machine.modules;

import coffee.machine.components.CoffeePot;
import coffee.machine.components.Grounder;
import coffee.machine.components.Tank;
import coffee.machine.ingredients.CoffeeEssence;
import coffee.machine.ingredients.CoffeeGrain;
import coffee.machine.ingredients.Water;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor(staticName = "of")
public class CoffeeModule {
    private final Tank<CoffeeGrain> coffeeTank;
    private final Grounder grounder;
    private final CoffeePot coffeePot;

    public void checkCapacity(int amountNeeded) {
        checkCoffeeTankOverflow();
        checkCoffeeResources(amountNeeded);
    }

    public CoffeeGrain ground(int amount) {
        CoffeeGrain coffeeGrain = coffeeTank.acquire(amount);
        return grounder.ground(coffeeGrain);
    }

    public CoffeeEssence pushSteamThroughGroundedCoffee(Water steam, CoffeeGrain groundedCoffee) {
        return coffeePot.combineSteamAndGroundedCoffee(steam, groundedCoffee);
    }

    private void checkCoffeeTankOverflow() {
        if (coffeeTank.isOverflown()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    String.format("Coffee tank overflow. Reduce the coffee amount" +
                            " to be below the maximum value of %dmg", coffeeTank.getCapacity()));
        }
    }

    private void checkCoffeeResources(int amountNeeded) {
        if (coffeeTank.getCurrentAmount() < amountNeeded) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    String.format("Insufficient coffee amount. Only %dmg left. You should " +
                                    "refill the coffee tank", coffeeTank.getCurrentAmount()));
        }
    }

}
