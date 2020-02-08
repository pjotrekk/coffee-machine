package coffee.machine.ingredients;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
@EqualsAndHashCode
public class CoffeeEssence {
    private int amount;
    private int coffeeExtract;
}
