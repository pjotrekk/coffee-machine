package coffee.machine.components;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class WaterPump implements Pump {
    @Override
    public void suction(int amount) {
        log.debug("Pumping {}ml water from water tank to heater tank", amount);
    }
}
