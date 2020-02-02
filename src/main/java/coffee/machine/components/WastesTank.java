package coffee.machine.components;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class WastesTank implements Tank {

    @Override
    public int amount() {
        log.debug("Amount of wastes in wastes tank: 200mg");
        return 200;
    }

    @Override
    public int maxCapacity() {
        log.debug("Maximum capacity of wastes tank: 1000mg");
        return 1000;
    }
}
