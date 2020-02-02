package coffee.machine.modules;

import coffee.machine.components.Pump;
import coffee.machine.components.Tank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor(staticName = "of")
public class WaterModuleImpl implements WaterModule {
    private Tank waterTank;
    private Pump waterPump;
    private HeatingModule waterHeatingModule;

    @Override
    public void checkWaterTank(int amountNeeded) {
        checkForOverflow();
        checkWaterCapacity(amountNeeded);
        waterHeatingModule.checkCapacity(amountNeeded);
    }

    @Override
    public void moveWaterToHeater(int amount) {
        waterPump.pump(amount);
    }

    @Override
    public void heatWater(int amount) {
        waterHeatingModule.heat(amount);
    }

    private void checkForOverflow() {
        if (waterTank.maxCapacity() < waterTank.amount()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Water tank overflow!");
        }
    }

    private void checkWaterCapacity(int amountNeeded) {
        if (waterTank.amount() < amountNeeded) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    String.format("Insufficient water amount. Only %dml left", waterTank.amount()));
        }
    }
}
