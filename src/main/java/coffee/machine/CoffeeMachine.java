package coffee.machine;

import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.MilkModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static coffee.machine.CoffeeKind.AMERICANO;
import static coffee.machine.CoffeeKind.ESPRESSO;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeMachine {

    @NonNull
    private WaterModule waterModule;

    @NonNull
    private CoffeeModule coffeeModule;

    @NonNull
    private WastesModule wastesModule;

    @NonNull
    private MilkModule milkModule;

    private Map<CoffeeKind, Consumer<CoffeeKind>> programs = new EnumMap<>(CoffeeKind.class);

    public static CoffeeMachine of(WaterModule waterModule, CoffeeModule coffeeModule,
                                   WastesModule wastesModule, MilkModule milkModule
    ) {
        CoffeeMachine coffeeMachine = new CoffeeMachine(
                Objects.requireNonNull(waterModule),
                Objects.requireNonNull(coffeeModule),
                Objects.requireNonNull(wastesModule),
                Objects.requireNonNull(milkModule)
        );
        coffeeMachine.programs.put(ESPRESSO, coffeeMachine::makeBlackCoffee);
        coffeeMachine.programs.put(AMERICANO, coffeeMachine::makeBlackCoffee);
        return coffeeMachine;
    }

    void makeCoffee(CoffeeKind coffeeKind) {
        checkContainers(coffeeKind);
        programs.get(coffeeKind).accept(coffeeKind);
    }

    private void makeBlackCoffee(CoffeeKind coffeeKind) {
        int water = coffeeKind.getWaterNeeded();
        int coffee = coffeeKind.getCoffeeNeeded();

        coffeeModule.ground(coffee);
        waterModule.prepareWater(water);
        coffeeModule.flipUsedCoffee();
    }

    private void checkContainers(CoffeeKind coffeeKind) {
        wastesModule.checkOverflow();
        waterModule.checkWaterTank(coffeeKind.getWaterNeeded());
        coffeeModule.checkCapacity(coffeeKind.getCoffeeNeeded());
    }

}
