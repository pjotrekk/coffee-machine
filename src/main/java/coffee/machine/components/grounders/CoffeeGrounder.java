package coffee.machine.components.grounders;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(staticName = "create")
@Log4j2
public class CoffeeGrounder implements Grounder {

    @Override
    public void ground(int amount) {
        log.info("Grounding {}mg of coffee", amount);
    }
}
