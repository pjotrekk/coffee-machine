package coffee.machine.modules;

import coffee.machine.components.Pump;
import coffee.machine.components.Tank;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor(staticName = "of")
@Log4j2
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
        log.debug("Transfer {}ml of water from water tank to heating tank", amount);
        waterPump.suction(amount);
        log.debug("Water transferred successfully");
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
