package coffee.machine.components.heaters;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class MilkHeater implements Heater {

    private static final int TEMPERATURE = 70;

    @Override
    public void heat(int amount) {
        log.info("Heating {}ml of milk", amount);
    }
}
