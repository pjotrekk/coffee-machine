package coffee.machine.components;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class WaterTank implements Tank {

    @Override
    public int amount() {
        return 600;
    }

    @Override
    public int maxCapacity() {
        return 1500;
    }

}
