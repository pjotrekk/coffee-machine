package coffee.machine.components;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class HeaterTank implements Tank {

    @Override
    public int amount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int maxCapacity() {
        log.debug("Maximum capacity of water tank: 400ml");
        return 400;
    }

}
