package coffee.machine.modules;

import coffee.machine.components.containers.Tank;
import coffee.machine.components.pumps.Pump;
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

    public void checkWaterTank(int amountNeeded) {
        checkForOverflow();
        checkWaterCapacity(amountNeeded);
        waterHeatingModule.checkCapacity(amountNeeded);
    }

    public void prepareWater(int amount) {
        moveWaterToHeater(amount);
        heatWater(amount);
    }

    private void moveWaterToHeater(int amount) {
        waterToHeaterPump.pump(amount);
    }

    private void heatWater(int amount) {
        waterHeatingModule.heat(amount);
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
