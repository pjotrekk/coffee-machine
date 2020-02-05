package coffee.machine.modules;

import coffee.machine.components.grounders.Grounder;
import coffee.machine.components.pots.Pot;
import coffee.machine.components.containers.Tank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor(staticName = "of")
public class CoffeeModuleImpl implements CoffeeModule {
    private final Tank coffeeTank;
    private final Pot coffeePot;
    private final Grounder grounder;

    @Override
    public void checkCapacity(int amountNeeded) {
        checkWaterTankCapacity(amountNeeded);
    }

    @Override
    public void ground(int amount) {
        grounder.ground(amount);
    }

    @Override
    public void flipUsedCoffee() {
        coffeePot.flip();
    }

    private void checkWaterTankCapacity(int amountNeeded) {
        if (coffeeTank.amount() < amountNeeded) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    String.format("Insufficient coffee amount. Only %dmg left", coffeeTank.amount()));
        }
    }

}
