package coffee.machine.components;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class CoffeeGrounder implements Grounder {

    @Override
    public void ground(int amount) {
        log.info("Grounding {}mg of coffee", amount);
    }
}
