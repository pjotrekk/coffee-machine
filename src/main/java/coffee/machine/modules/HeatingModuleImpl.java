package coffee.machine.modules;

import coffee.machine.heaters.Heater;
import coffee.machine.tanks.Tank;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor(staticName = "of")
@Log4j2
public class HeatingModuleImpl implements HeatingModule {
    private Tank waterTank;
    private Heater heater;

    @Override
    public void heat(int amount) {
        log.debug("Heating {}ml of water", amount);
        heater.heat(amount);
        log.debug("Water evaporated successfully");
    }

    @Override
    public void checkCapacity(int amount) {
        log.debug("Check heating module water tank capacity");
        if (waterTank.maxCapacity() < amount) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    "Heating module water tank is too small for such a coffee!");
        }
    }
}
