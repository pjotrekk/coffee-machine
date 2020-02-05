package coffee.machine.components.containers;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(staticName = "create")
@Log4j2
public class WastesTank implements Tank {

    @Override
    public int amount() {
        return 200;
    }

    @Override
    public int maxAmount() {
        return 1000;
    }

}
