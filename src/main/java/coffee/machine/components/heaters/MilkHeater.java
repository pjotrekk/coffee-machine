package coffee.machine.components.heaters;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(staticName = "create")
@Log4j2
public class MilkHeater implements Heater {

    private static final int TEMPERATURE = 70;

    @Override
    public void heat(int amount) {
        log.info("Heating {}ml of milk to {} celsius degrees",
                amount, TEMPERATURE);
    }
}
