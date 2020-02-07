package coffee.machine;

import coffee.machine.ingredients.CoffeeEssence;
import coffee.machine.ingredients.Milk;
import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.MilkModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
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
        Coffee expected = Coffee.of(CoffeeEssence.of(waterNeeded, coffeeNeeded), null);

        given(waterModule.prepareTheEssence(waterNeeded)).willReturn(CoffeeEssence.of(waterNeeded, coffeeNeeded));

        Coffee coffee = coffeeMachine.makeCoffee(CoffeeKind.AMERICANO);

        assertEquals(expected, coffee);

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
        Coffee expected = Coffee.of(CoffeeEssence.of(waterNeeded, coffeeNeeded), null);

        given(waterModule.prepareTheEssence(anyInt())).willReturn(CoffeeEssence.of(waterNeeded, coffeeNeeded));

        Coffee coffee = coffeeMachine.makeCoffee(CoffeeKind.ESPRESSO);

        assertThat(coffee).isEqualTo(expected);

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
        Coffee expected = Coffee.of(CoffeeEssence.of(waterNeeded, coffeeNeeded),
                Milk.of(milkNeeded, Milk.PERFECT_TEMPERATURE, false));

        given(waterModule.prepareTheEssence(anyInt())).willReturn(CoffeeEssence.of(waterNeeded, coffeeNeeded));
        given(milkModule.prepareMilk(anyInt(), anyBoolean()))
                .willReturn(Milk.of(milkNeeded, Milk.PERFECT_TEMPERATURE, false));

        Coffee coffee = coffeeMachine.makeCoffee(CoffeeKind.LATTE);

        assertThat(coffee).isEqualTo(expected);

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
        Coffee expected = Coffee.of(CoffeeEssence.of(waterNeeded, coffeeNeeded),
                Milk.of(milkNeeded, Milk.PERFECT_TEMPERATURE, true));

        given(waterModule.prepareTheEssence(anyInt())).willReturn(CoffeeEssence.of(waterNeeded, coffeeNeeded));
        given(milkModule.prepareMilk(anyInt(), anyBoolean()))
                .willReturn(Milk.of(milkNeeded, Milk.PERFECT_TEMPERATURE, true));

        Coffee coffee = coffeeMachine.makeCoffee(CoffeeKind.CAPPUCCINO);

        assertThat(coffee).isEqualTo(expected);

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