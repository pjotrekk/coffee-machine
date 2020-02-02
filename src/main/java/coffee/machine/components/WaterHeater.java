package coffee.machine.components;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class WaterHeater implements Heater {

    private static final int TEMPERATURE = 100;

    @Override
    public void heat(int amount) {
        log.info("Heating {}ml of water", amount);
    }
}
