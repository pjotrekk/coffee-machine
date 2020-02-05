package coffee.machine.components.heaters;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(staticName = "create")
@Log4j2
public class WaterHeater implements Heater {

    @Override
    public void heat(int amount) {
        log.info("Evaporating {}ml of water", amount);
    }
}
