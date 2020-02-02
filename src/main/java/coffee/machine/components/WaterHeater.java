package coffee.machine.components;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class WaterHeater implements Heater {

    @Override
    public void heat(int amount) {
        log.debug("Heating {}ml of water", amount);
    }
}
