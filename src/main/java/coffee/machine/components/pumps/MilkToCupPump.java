package coffee.machine.components.pumps;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class MilkToCupPump implements Pump {
    @Override
    public void pump(int amount) {
        log.info("Pumping {}ml of milk from heater to the cup", amount);
    }
}
