package coffee.machine.components;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class WaterTank implements Tank {

    @Override
    public int amount() {
        log.debug("Amount of coffee in water tank: 800ml");
        return 800;
    }

    @Override
    public int maxCapacity() {
        log.debug("Maximum capacity of water tank: 1500ml");
        return 1500;
    }

}
