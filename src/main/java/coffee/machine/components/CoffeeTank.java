package coffee.machine.components;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CoffeeTank implements Tank {

    @Override
    public int amount() {
        log.debug("Amount of coffee in coffee tank: 200mg");
        return 200;
    }

    @Override
    public int maxCapacity() {
        log.debug("Maximum capacity of coffee tank: 500mg");
        return 500;
    }
}
