package coffee.machine.components.foamers;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class MilkFoamer implements Foamer {
    @Override
    public void foam(int amount) {
        log.info("Foaming {}ml of milk", amount);
    }
}
