package coffee.machine.components.foamers;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(staticName = "create")
public class Foamer {

    public void foam(int amount) {}

}
