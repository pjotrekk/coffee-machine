package coffee.machine;

import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.HeatingModule;
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
    private HeatingModule heatingModule;

    @Mock
    private WastesModule wastesModule;

    private CoffeeMachine coffeeMachine;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        coffeeMachine = CoffeeMachine.of(waterModule, coffeeModule, heatingModule, wastesModule);
    }

    @Test
    void makeCoffeeAmericano() {
        coffeeMachine.makeCoffee(CoffeeKind.AMERICANO);
        int waterNeeded = CoffeeKind.AMERICANO.getWaterNeeded();
        int coffeeNeeded = CoffeeKind.AMERICANO.getCoffeeNeeded();

        verify(waterModule, times(1)).checkWaterTank(waterNeeded);
        verify(heatingModule, times(1)).checkCapacity(waterNeeded);
        verify(coffeeModule, times(1)).checkCapacity(coffeeNeeded);
        verify(wastesModule, times(1)).checkOverflow();
        verify(waterModule, times(1)).moveWaterToHeater(waterNeeded);
        verify(coffeeModule, times(1)).ground(coffeeNeeded);
        verify(heatingModule, times(1)).heat(waterNeeded);
        verify(coffeeModule, times(1)).flipUsedCoffee();

        verifyNoMoreInteractions(waterModule, coffeeModule, heatingModule, wastesModule);
    }

    @Test
    void makeCoffeeEspresso() {
        coffeeMachine.makeCoffee(CoffeeKind.ESPRESSO);
        int waterNeeded = CoffeeKind.ESPRESSO.getWaterNeeded();
        int coffeeNeeded = CoffeeKind.ESPRESSO.getCoffeeNeeded();

        verify(waterModule, times(1)).checkWaterTank(waterNeeded);
        verify(heatingModule, times(1)).checkCapacity(waterNeeded);
        verify(coffeeModule, times(1)).checkCapacity(coffeeNeeded);
        verify(wastesModule, times(1)).checkOverflow();
        verify(waterModule, times(1)).moveWaterToHeater(waterNeeded);
        verify(coffeeModule, times(1)).ground(coffeeNeeded);
        verify(heatingModule, times(1)).heat(waterNeeded);
        verify(coffeeModule, times(1)).flipUsedCoffee();

        verifyNoMoreInteractions(waterModule, coffeeModule, heatingModule, wastesModule);
    }
}