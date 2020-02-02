package coffee.machine;

import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.MilkModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class CoffeeMachineTest {

    @Mock
    private WaterModule waterModule;

    @Mock
    private CoffeeModule coffeeModule;

    @Mock
    private WastesModule wastesModule;

    @Mock
    private MilkModule milkModule;

    private CoffeeMachine coffeeMachine;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        coffeeMachine = CoffeeMachine.of(waterModule, coffeeModule, wastesModule, milkModule);
    }

    @Test
    void makeCoffeeAmericano() {
        coffeeMachine.makeCoffee(CoffeeKind.AMERICANO);
        int waterNeeded = CoffeeKind.AMERICANO.getWaterNeeded();
        int coffeeNeeded = CoffeeKind.AMERICANO.getCoffeeNeeded();
        int milkNeeded = CoffeeKind.AMERICANO.getMilkNeeded();

        verify(milkModule, times(1)).checkMilkContainer(milkNeeded);
        verify(waterModule, times(1)).checkWaterTank(waterNeeded);
        verify(coffeeModule, times(1)).checkCapacity(coffeeNeeded);
        verify(wastesModule, times(1)).checkOverflow();

        verify(coffeeModule, times(1)).ground(coffeeNeeded);
        verify(waterModule, times(1)).prepareWater(waterNeeded);
        verify(coffeeModule, times(1)).flipUsedCoffee();

        verifyNoMoreInteractions(waterModule, coffeeModule, wastesModule, milkModule);
    }

    @Test
    void makeCoffeeEspresso() {
        coffeeMachine.makeCoffee(CoffeeKind.ESPRESSO);
        int waterNeeded = CoffeeKind.ESPRESSO.getWaterNeeded();
        int coffeeNeeded = CoffeeKind.ESPRESSO.getCoffeeNeeded();
        int milkNeeded = CoffeeKind.ESPRESSO.getMilkNeeded();

        verify(milkModule, times(1)).checkMilkContainer(milkNeeded);
        verify(waterModule, times(1)).checkWaterTank(waterNeeded);
        verify(coffeeModule, times(1)).checkCapacity(coffeeNeeded);
        verify(wastesModule, times(1)).checkOverflow();

        verify(coffeeModule, times(1)).ground(coffeeNeeded);
        verify(waterModule, times(1)).prepareWater(waterNeeded);
        verify(coffeeModule, times(1)).flipUsedCoffee();

        verifyNoMoreInteractions(waterModule, coffeeModule, wastesModule, milkModule);
    }

    @Test
    void makeCoffeeLatte() {
        coffeeMachine.makeCoffee(CoffeeKind.LATTE);
        int waterNeeded = CoffeeKind.LATTE.getWaterNeeded();
        int coffeeNeeded = CoffeeKind.LATTE.getCoffeeNeeded();
        int milkNeeded = CoffeeKind.LATTE.getMilkNeeded();

        verify(milkModule, times(1)).checkMilkContainer(milkNeeded);
        verify(waterModule, times(1)).checkWaterTank(waterNeeded);
        verify(coffeeModule, times(1)).checkCapacity(coffeeNeeded);
        verify(wastesModule, times(1)).checkOverflow();

        verify(coffeeModule, times(1)).ground(coffeeNeeded);
        verify(waterModule, times(1)).prepareWater(waterNeeded);
        verify(milkModule, times(1)).prepareMilk(milkNeeded);
        verify(coffeeModule, times(1)).flipUsedCoffee();

        verifyNoMoreInteractions(waterModule, coffeeModule, wastesModule, milkModule);
    }

}