package coffee.machine;

import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.HeatingModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import lombok.Builder;

@Builder
class CoffeeMachine {
    private WaterModule waterModule;
    private CoffeeModule coffeeModule;
    private HeatingModule heatingModule;
    private WastesModule wastesModule;

    void makeCoffee(CoffeeKind coffeeKind) {
        int amountWater = coffeeKind.getWaterNeeded();
        int amountCoffee = coffeeKind.getCoffeeNeeded();

        waterModule.checkCapacity(amountWater);
        coffeeModule.checkCapacity(amountCoffee);
        wastesModule.checkOverflow();
        waterModule.moveWaterToHeater(amountWater);
        coffeeModule.ground(amountCoffee);
        heatingModule.heat(amountWater);
        coffeeModule.flipUsedCoffee();
    }

}
