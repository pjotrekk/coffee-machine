package coffee.machine.components;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CoffeePot implements Pot {
    @Override
    public void flip() {
        log.debug("Removing used coffee");
    }
}
