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

    @Override
    public void checkWaterTank(int amountNeeded) {
        log.debug("Check water tank for overflow");
        if (waterTank.maxCapacity() < waterTank.amount()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Water tank overflow!");
        }

        log.debug("Check water capacity");
        if (waterTank.amount() < amountNeeded) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    String.format("Insufficient water amount. Only %dml left", waterTank.amount()));
        }
    }

    @Override
    public void moveWaterToHeater(int amount) {
        log.debug("Transfer {}ml of water from water tank to heating tank", amount);
        waterPump.suction(amount);
        log.debug("Water transferred successfully");
    }
}
