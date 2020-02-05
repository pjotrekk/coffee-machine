package coffee.machine.components.pots;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(staticName = "create")
@Log4j2
public class CoffeePot implements Pot {
    @Override
    public void flip() {
        log.info("Removing used coffee");
    }
}
