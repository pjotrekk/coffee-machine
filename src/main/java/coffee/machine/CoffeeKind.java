package coffee.machine;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoffeeKind {
    ESPRESSO(50, 40, 0),
    AMERICANO(250, 40, 0),
    LATTE(250, 20, 100),
    CAPPUCCINO(150, 20, 150);

    private final int waterNeeded;
    private final int coffeeNeeded;
    private final int milkNeeded;
}
