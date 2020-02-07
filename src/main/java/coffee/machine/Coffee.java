package coffee.machine;

import coffee.machine.ingredients.CoffeeEssence;
import coffee.machine.ingredients.Milk;
import lombok.*;

@NoArgsConstructor(staticName = "create")
@AllArgsConstructor(staticName = "of")
@Getter @Setter
@EqualsAndHashCode
public class Coffee {
    private CoffeeEssence coffeeEssence;
    private Milk milk;
}
