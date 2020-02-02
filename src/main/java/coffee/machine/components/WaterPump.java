package coffee.machine.components;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class WaterPump implements Pump {
    @Override
    public void suction(int amount) {
        log.debug("Pumping {}ml water from water tank to heater tank", amount);
    }
}
