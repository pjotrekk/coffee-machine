package coffee.machine;

import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.MilkModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoffeeMachineTest {

    @Mock
    private WaterModule waterModule;

    @Mock
    private CoffeeModule coffeeModule;

    @Mock
    private WastesModule wastesModule;

    @Mock
    private MilkModule milkModule;

    @InjectMocks
    private CoffeeMachine coffeeMachine;

    @Test
    void shouldUseProperModulesToMakeAmericano() {
        int waterNeeded = CoffeeKind.AMERICANO.getWaterNeeded();
        int coffeeNeeded = CoffeeKind.AMERICANO.getCoffeeNeeded();
        int milkNeeded = CoffeeKind.AMERICANO.getMilkNeeded();

        coffeeMachine.makeCoffee(CoffeeKind.AMERICANO);

        checkModules(waterNeeded, coffeeNeeded, milkNeeded);

        verify(coffeeModule, times(1)).ground(coffeeNeeded);
        verify(waterModule, times(1)).prepareTheEssence(waterNeeded);

        verifyNoMoreInteractions(waterModule, coffeeModule, wastesModule, milkModule);
    }

    @Test
    void shouldUseProperModulesToMakeEspresso() {
        int waterNeeded = CoffeeKind.ESPRESSO.getWaterNeeded();
        int coffeeNeeded = CoffeeKind.ESPRESSO.getCoffeeNeeded();
        int milkNeeded = CoffeeKind.ESPRESSO.getMilkNeeded();

        coffeeMachine.makeCoffee(CoffeeKind.ESPRESSO);

        checkModules(waterNeeded, coffeeNeeded, milkNeeded);

        verify(coffeeModule, times(1)).ground(coffeeNeeded);
        verify(waterModule, times(1)).prepareTheEssence(waterNeeded);

        verifyNoMoreInteractions(waterModule, coffeeModule, wastesModule, milkModule);
    }

    @Test
    void shouldUseProperModulesToMakeLatte() {
        int waterNeeded = CoffeeKind.LATTE.getWaterNeeded();
        int coffeeNeeded = CoffeeKind.LATTE.getCoffeeNeeded();
        int milkNeeded = CoffeeKind.LATTE.getMilkNeeded();
        boolean withFoam = CoffeeKind.LATTE.isWithFoam();

        coffeeMachine.makeCoffee(CoffeeKind.LATTE);

        checkModules(waterNeeded, coffeeNeeded, milkNeeded);

        verify(coffeeModule, times(1)).ground(coffeeNeeded);
        verify(waterModule, times(1)).prepareTheEssence(waterNeeded);
        verify(milkModule, times(1)).prepareMilk(milkNeeded, withFoam);

        verifyNoMoreInteractions(waterModule, coffeeModule, wastesModule, milkModule);
    }

    @Test
    void shouldUseProperModulesToMakeCappuccino() {
        int waterNeeded = CoffeeKind.CAPPUCCINO.getWaterNeeded();
        int coffeeNeeded = CoffeeKind.CAPPUCCINO.getCoffeeNeeded();
        int milkNeeded = CoffeeKind.CAPPUCCINO.getMilkNeeded();
        boolean withFoam = CoffeeKind.CAPPUCCINO.isWithFoam();

        coffeeMachine.makeCoffee(CoffeeKind.CAPPUCCINO);

        checkModules(waterNeeded, coffeeNeeded, milkNeeded);

        verify(coffeeModule, times(1)).ground(coffeeNeeded);
        verify(waterModule, times(1)).prepareTheEssence(waterNeeded);
        verify(milkModule, times(1)).prepareMilk(milkNeeded, withFoam);

        verifyNoMoreInteractions(waterModule, coffeeModule, wastesModule, milkModule);
    }

    private void checkModules(int waterNeeded, int coffeeNeeded, int milkNeeded) {
        verify(milkModule, times(1)).checkMilkTank(milkNeeded);
        verify(waterModule, times(1)).checkWaterTank(waterNeeded);
        verify(coffeeModule, times(1)).checkCapacity(coffeeNeeded);
        verify(wastesModule, times(1)).checkOverflow();
    }

}