package coffee.machine.components;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CoffeeGrounder implements Grounder {

    @Override
    public void ground(int amount) {
        log.debug("Grounding {}mg of coffee", amount);
    }
}
