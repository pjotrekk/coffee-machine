package coffee.machine.components;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class CoffeePot implements Pot {
    @Override
    public void flip() {
        log.debug("Removing used coffee");
    }
}
