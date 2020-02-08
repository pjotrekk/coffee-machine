package coffee.machine.modules;

import coffee.machine.components.Evaporator;
import coffee.machine.components.Tank;
import coffee.machine.ingredients.Water;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor(staticName = "of")
public class WaterModule {
    private final Tank<Water> waterTank;
    private final Evaporator evaporator;

    public void checkWaterTank(int amountNeeded) {
        checkForOverflow();
        checkWaterCapacity(amountNeeded);
    }

    public Water prepareSteam(int amount) {
        Water water = waterTank.acquire(amount);
        return evaporator.evaporate(water);
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
