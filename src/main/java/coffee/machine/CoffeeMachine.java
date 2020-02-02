package coffee.machine;

import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.HeatingModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import lombok.Builder;

import static coffee.machine.CoffeeKind.AMERICANO;
import static coffee.machine.CoffeeKind.ESPRESSO;

@Builder
public class CoffeeMachine {

    private WaterModule waterModule;
    private CoffeeModule coffeeModule;
    private HeatingModule heatingModule;
    private WastesModule wastesModule;

    void makeCoffee(CoffeeKind coffeeKind) {
        switch (coffeeKind) {
            case ESPRESSO:
                makeBlackCoffee(ESPRESSO);
                break;
            case AMERICANO:
                makeBlackCoffee(AMERICANO);
                break;
            default:
                throw new AssertionError("Unknown coffee program: " + coffeeKind);
        }
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
