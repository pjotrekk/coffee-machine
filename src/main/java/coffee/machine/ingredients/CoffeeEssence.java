package coffee.machine.ingredients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@Getter @Setter
public class CoffeeEssence {
    private int amount;
    private int coffeeExtract;
}
