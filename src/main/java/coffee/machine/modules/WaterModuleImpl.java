package coffee.machine.modules;

import coffee.machine.pumps.Pump;
import coffee.machine.tanks.WaterTank;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Log4j2
public class WaterModuleImpl implements WaterModule {
    private WaterTank waterTank;
    private Pump waterPump;

    @Override
    public void checkCapacity(int amountNeeded) {
        log.debug("Check water capacity");
        if (waterTank.amount() < amountNeeded) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
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
