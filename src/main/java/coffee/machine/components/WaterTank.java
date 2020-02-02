package coffee.machine.components;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class WaterTank implements Tank {

    @Override
    public int amount() {
        log.debug("Amount of coffee in water tank: 600ml");
        return 600;
    }

    @Override
    public int maxCapacity() {
        log.debug("Maximum capacity of water tank: 1500ml");
        return 1500;
    }

}
