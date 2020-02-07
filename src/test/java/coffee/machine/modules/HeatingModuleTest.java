package coffee.machine.modules;

import coffee.machine.components.Heater;
import coffee.machine.components.LiquidTank;
import coffee.machine.ingredients.Liquid;
import coffee.machine.ingredients.Water;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HeatingModuleTest {

    private LiquidTank heaterTank;

    private Heater heater;

    private Liquid waterToHeat;

    private HeatingModule heatingModule;

    @BeforeEach
    void setUp() {
        waterToHeat = Water.of(200, 20);
        heaterTank = LiquidTank.of(waterToHeat, 200);
        heater = Heater.create(100);
        heatingModule = HeatingModule.of(heaterTank, heater);
    }

    @Test
    void shouldReturnHeatedLiquidAndSetItsContainerToZero() {
        Liquid heatedWater = heatingModule.heatContent();
        assertThat(heatedWater.getTemperature()).isEqualTo(100);
        assertThat(heatedWater.getAmount()).isEqualTo(200);
        assertThat(heaterTank.getCurrentAmount()).isEqualTo(0);
    }

}