package coffee.machine;

import coffee.machine.ingredients.CoffeeEssence;
import coffee.machine.ingredients.CoffeeGrain;
import coffee.machine.ingredients.Milk;
import coffee.machine.ingredients.Water;
import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.MilkModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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

    private Water testWater = Water.of(200, 23, false);
    private Milk testMilk = Milk.of(100, 23, false);
    private CoffeeGrain testCoffeeGrain = CoffeeGrain.of(20, false, false);
    private CoffeeEssence testCoffeeEssence = CoffeeEssence.of(200, 20);

    @BeforeEach
    void setUp() {
        given(waterModule.prepareSteam(anyInt())).willReturn(testWater);
        given(coffeeModule.ground(anyInt())).willReturn(testCoffeeGrain);
        given(coffeeModule.pushSteamThroughGroundedCoffee(any(), any())).willReturn(testCoffeeEssence);
    }

    @Test
    void shouldUseProperModulesToMakeBlackCoffee() {
        int waterNeeded = CoffeeKind.AMERICANO.getWaterNeeded();
        int coffeeNeeded = CoffeeKind.AMERICANO.getCoffeeNeeded();
        int milkNeeded = CoffeeKind.AMERICANO.getMilkNeeded();

        Coffee expected = Coffee.of(testCoffeeEssence.getAmount(), testCoffeeEssence.getCoffeeExtract(), 0, false);

        Coffee coffee = coffeeMachine.makeCoffee(CoffeeKind.AMERICANO);

        assertThat(coffee).isEqualTo(expected);

        checkModules(waterNeeded, coffeeNeeded, milkNeeded);
        verify(coffeeModule, times(1)).ground(coffeeNeeded);
        verify(coffeeModule, times(1))
                .pushSteamThroughGroundedCoffee(testWater, testCoffeeGrain);
        verify(waterModule, times(1)).prepareSteam(waterNeeded);
        verifyNoMoreInteractions(waterModule, coffeeModule, wastesModule, milkModule);
    }


    @Test
    void shouldUseProperModulesToMakeWhiteCoffee() {
        int waterNeeded = CoffeeKind.LATTE.getWaterNeeded();
        int coffeeNeeded = CoffeeKind.LATTE.getCoffeeNeeded();
        int milkNeeded = CoffeeKind.LATTE.getMilkNeeded();
        boolean withFoam = CoffeeKind.LATTE.isWithFoam();

        given(milkModule.prepareMilk(anyInt(), anyBoolean())).willReturn(testMilk);

        Coffee expected = Coffee.of(testCoffeeEssence.getAmount(), testCoffeeEssence.getCoffeeExtract(),
                testMilk.getAmount(), testMilk.isFoamed());

        Coffee coffee = coffeeMachine.makeCoffee(CoffeeKind.LATTE);

        assertThat(coffee).isEqualTo(expected);

        checkModules(waterNeeded, coffeeNeeded, milkNeeded);
        verify(coffeeModule, times(1)).ground(coffeeNeeded);
        verify(coffeeModule, times(1))
                .pushSteamThroughGroundedCoffee(testWater, testCoffeeGrain);
        verify(waterModule, times(1)).prepareSteam(waterNeeded);
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