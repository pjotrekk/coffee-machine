package coffee.machine.components;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class HeaterContainer implements Container {

    @Override
    public int maxCapacity() {
        log.debug("Maximum capacity of heater tank: 400ml");
        return 400;
    }

}
