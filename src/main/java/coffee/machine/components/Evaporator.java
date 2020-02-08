package coffee.machine.components;

import coffee.machine.ingredients.Water;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class Evaporator {

    public Water evaporate(Water water) {
        return Water.of(water.getAmount(), 100, true);
    }

}
