package coffee.machine;

import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.MilkModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static coffee.machine.CoffeeKind.*;

@Component
@Log4j2
public class CoffeeMachine {

    private final WaterModule waterModule;

    private final CoffeeModule coffeeModule;

    private final WastesModule wastesModule;

    private final MilkModule milkModule;

    private final Map<CoffeeKind, Consumer<CoffeeKind>> programs = new EnumMap<>(CoffeeKind.class);

    public CoffeeMachine(WaterModule waterModule, CoffeeModule coffeeModule,
                         WastesModule wastesModule, MilkModule milkModule) {
        this.waterModule = Objects.requireNonNull(waterModule);
        this.coffeeModule = Objects.requireNonNull(coffeeModule);
        this.wastesModule = Objects.requireNonNull(wastesModule);
        this.milkModule = Objects.requireNonNull(milkModule);
        programs.put(ESPRESSO, this::makeBlackCoffee);
        programs.put(AMERICANO, this::makeBlackCoffee);
        programs.put(LATTE, this::makeLatte);
        programs.put(CAPPUCCINO, this::makeCappuccino);
    }

    public void makeCoffee(CoffeeKind coffeeKind) {
        checkContainers(coffeeKind);
        log.info("Making coffee {}", coffeeKind.toString());
        programs.get(coffeeKind).accept(coffeeKind);
    }

    private void makeBlackCoffee(CoffeeKind coffeeKind) {
        coffeeModule.ground(coffeeKind.getCoffeeNeeded());
        waterModule.prepareWater(coffeeKind.getWaterNeeded());
        coffeeModule.flipUsedCoffee();
    }

    private void makeLatte(CoffeeKind coffeeKind) {
        coffeeModule.ground(coffeeKind.getCoffeeNeeded());
        waterModule.prepareWater(coffeeKind.getWaterNeeded());
        milkModule.prepareMilk(coffeeKind.getMilkNeeded());
        coffeeModule.flipUsedCoffee();
    }

    private void makeCappuccino(CoffeeKind coffeeKind) {
        coffeeModule.ground(coffeeKind.getCoffeeNeeded());
        waterModule.prepareWater(coffeeKind.getWaterNeeded());
        milkModule.prepareFoamedMilk(coffeeKind.getMilkNeeded());
        coffeeModule.flipUsedCoffee();
    }

    private void checkContainers(CoffeeKind coffeeKind) {
        wastesModule.checkOverflow();
        waterModule.checkWaterTank(coffeeKind.getWaterNeeded());
        coffeeModule.checkCapacity(coffeeKind.getCoffeeNeeded());
        milkModule.checkMilkContainer(coffeeKind.getMilkNeeded());
    }

}
