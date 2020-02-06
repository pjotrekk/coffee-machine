package coffee.machine;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoffeeKind {
    ESPRESSO(50, 40, 0, false),
    AMERICANO(250, 40, 0, false),
    LATTE(250, 20, 100, false),
    CAPPUCCINO(150, 20, 150, true);

    private final int waterNeeded;
    private final int coffeeNeeded;
    private final int milkNeeded;
    private final boolean withFoam;
}
