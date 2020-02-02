package coffee.machine.components;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class CoffeeTank implements Tank {

    @Override
    public int amount() {
        return 200;
    }

    @Override
    public int maxCapacity() {
        return 500;
    }
}
