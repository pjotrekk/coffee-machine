package coffee.machine;

import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.HeatingModule;
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
    private HeatingModule heatingModule;

    @NonNull
    private WastesModule wastesModule;

    private Map<CoffeeKind, Consumer<CoffeeKind>> programs = new EnumMap<>(CoffeeKind.class);

    public static CoffeeMachine of(WaterModule waterModule, CoffeeModule coffeeModule,
                                   HeatingModule heatingModule, WastesModule wastesModule
    ) {
        CoffeeMachine coffeeMachine = new CoffeeMachine(
                Objects.requireNonNull(waterModule),
                Objects.requireNonNull(coffeeModule),
                Objects.requireNonNull(heatingModule),
                Objects.requireNonNull(wastesModule)
        );
        coffeeMachine.programs.put(ESPRESSO, coffeeMachine::makeBlackCoffee);
        coffeeMachine.programs.put(AMERICANO, coffeeMachine::makeBlackCoffee);
        return coffeeMachine;
    }

    void makeCoffee(CoffeeKind coffeeKind) {
        programs.get(coffeeKind).accept(coffeeKind);
    }

    private void makeBlackCoffee(CoffeeKind coffeeKind) {
        int amountWater = coffeeKind.getWaterNeeded();
        int amountCoffee = coffeeKind.getCoffeeNeeded();

        waterModule.checkWaterTank(amountWater);
        wastesModule.checkOverflow();
        heatingModule.checkCapacity(amountWater);
        coffeeModule.checkCapacity(amountCoffee);
        waterModule.moveWaterToHeater(amountWater);
        coffeeModule.ground(amountCoffee);
        heatingModule.heat(amountWater);
        coffeeModule.flipUsedCoffee();
    }

}
