package coffee.machine;

import coffee.machine.ingredients.CoffeeEssence;
import coffee.machine.ingredients.Milk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(staticName = "create")
@AllArgsConstructor(staticName = "of")
@Getter @Setter
public class Coffee {
    private CoffeeEssence coffeeEssence;
    private Milk milk;
}
