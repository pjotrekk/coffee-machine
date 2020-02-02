package coffee.machine.components.containers;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(staticName = "create")
@Log4j2
public class HeaterContainer implements Container {

    @Override
    public int maxAmount() {
        return 400;
    }

}
