package coffee.machine.modules;

import coffee.machine.components.containers.Container;
import coffee.machine.components.heaters.Heater;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@AllArgsConstructor(staticName = "of")
public class HeatingModuleImpl implements HeatingModule {
    private Container heaterContainer;
    private Heater heater;

    @Override
    public void heat(int amount) {
        heater.heat(amount);
    }

    @Override
    public void checkCapacity(int amount) {
        checkContainerCapacity(amount);
    }

    private void checkContainerCapacity(int amount) {
        if (heaterContainer.maxAmount() < amount) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Heating module water tank is too small for such a coffee!");
        }
    }
}
