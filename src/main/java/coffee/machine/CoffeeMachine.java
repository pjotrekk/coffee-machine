package coffee.machine;

import coffee.machine.ingredients.CoffeeEssence;
import coffee.machine.ingredients.CoffeeGrain;
import coffee.machine.ingredients.Milk;
import coffee.machine.ingredients.Water;
import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.MilkModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(staticName = "of")
@Log4j2
public class CoffeeMachine {

    private final WaterModule waterModule;

    private final CoffeeModule coffeeModule;

    private final WastesModule wastesModule;

    private final MilkModule milkModule;

    public Coffee makeCoffee(CoffeeKind coffeeKind) {
        checkContainers(coffeeKind);

        Coffee coffee = Coffee.create();

        CoffeeGrain groundedCoffee = coffeeModule.ground(coffeeKind.getCoffeeNeeded());

        Water steam = waterModule.prepareSteam(coffeeKind.getWaterNeeded());

        CoffeeEssence coffeeEssence = coffeeModule.pushSteamThroughGroundedCoffee(steam, groundedCoffee);

        coffee.setCoffeeEssence(coffeeEssence);

        if (coffeeKind.getMilkNeeded() > 0) {
            Milk milk = milkModule.prepareMilk(coffeeKind.getMilkNeeded(), coffeeKind.isWithFoam());
            coffee.setMilk(milk);
        }

        return coffee;
    }

    private void checkContainers(CoffeeKind coffeeKind) {
        wastesModule.checkOverflow();
        waterModule.checkWaterTank(coffeeKind.getWaterNeeded());
        coffeeModule.checkCapacity(coffeeKind.getCoffeeNeeded());
        milkModule.checkMilkTank(coffeeKind.getMilkNeeded());
    }

}
