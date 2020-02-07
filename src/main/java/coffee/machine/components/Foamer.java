package coffee.machine.components;

import coffee.machine.ingredients.Milk;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(staticName = "create")
public class Foamer {

    public void foam(Milk milkToFoam) {
        milkToFoam.setFoamed(true);
    }

}
