package coffee.machine.modules;

import coffee.machine.components.CoffeePot;
import coffee.machine.components.Pump;
import coffee.machine.components.Tank;
import coffee.machine.ingredients.CoffeeEssence;
import coffee.machine.ingredients.Water;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor(staticName = "of")
public class WaterModule {
    private final Tank waterTank;
    private final Pump waterToHeaterPump;
    private final HeatingModule waterHeatingModule;
    private final CoffeePot coffeePot;

    public void checkWaterTank(int amountNeeded) {
        checkForOverflow();
        checkWaterCapacity(amountNeeded);
    }

    public CoffeeEssence prepareTheEssence(int amount) {
        moveWaterToHeater(amount);
        Water steam = evaporateWater();
        return pushSteamThroughCoffeePot(steam);
    }

    private CoffeeEssence pushSteamThroughCoffeePot(Water steam) {
        return coffeePot.combineSteamAndGroundedCoffee(steam);
    }

    private void moveWaterToHeater(int amount) {
        waterToHeaterPump.pump(amount);
    }

    private Water evaporateWater() {
        return (Water) waterHeatingModule.heatContent();
    }

    private void checkForOverflow() {
        if (waterTank.isOverflown()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    String.format("Water tank overflow! You should reduce the amount " +
                            "of water to the maximum of %dml", waterTank.getCapacity()));
        }
    }

    private void checkWaterCapacity(int amountNeeded) {
        if (waterTank.getCurrentAmount() < amountNeeded) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    String.format("Insufficient water amount. Only %dml left. Refill " +
                                    "the water tank", waterTank.getCurrentAmount()));
        }
    }
}
