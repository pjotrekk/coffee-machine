package coffee.machine.components;

import coffee.machine.ingredients.Milk;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(staticName = "create")
public class Foamer {

    public Milk foam(Milk milkToFoam) {
        checkMilkAlreadyFoamed(milkToFoam);
        return Milk.of(milkToFoam.getAmount(), milkToFoam.getTemperature(), true);
    }

    private void checkMilkAlreadyFoamed(Milk milkToFoam) {
        if (milkToFoam.isFoamed()) {
            throw new AssertionError("Milk is already foamed");
        }
    }

}
