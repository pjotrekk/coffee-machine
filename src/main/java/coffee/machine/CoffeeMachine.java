package coffee.machine;

import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.MilkModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class CoffeeMachine {

    private final WaterModule waterModule;

    private final CoffeeModule coffeeModule;

    private final WastesModule wastesModule;

    private final MilkModule milkModule;

    public void makeCoffee(CoffeeKind coffeeKind) {
        checkContainers(coffeeKind);

        log.info("Making coffee {}", coffeeKind.toString());

        coffeeModule.ground(coffeeKind.getCoffeeNeeded());
        waterModule.prepareWater(coffeeKind.getWaterNeeded());
        if (coffeeKind.getMilkNeeded() > 0) {
            milkModule.prepareMilk(coffeeKind.getMilkNeeded(), coffeeKind.isWithFoam());
        }
    }

    private void checkContainers(CoffeeKind coffeeKind) {
        wastesModule.checkOverflow();
        waterModule.checkWaterTank(coffeeKind.getWaterNeeded());
        coffeeModule.checkCapacity(coffeeKind.getCoffeeNeeded());
        milkModule.checkMilkTank(coffeeKind.getMilkNeeded());
    }

}
