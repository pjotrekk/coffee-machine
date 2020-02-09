package coffee.machine.components;

import coffee.machine.ingredients.Water;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EvaporatorTest {

    Water testWater = Water.of(100, 20, false);

    Evaporator evaporator = Evaporator.create();

    @Test
    void shouldReturnWaterWithTemperatureSetTo100AndEvaporatedTrue() {
        Water steam = evaporator.evaporate(testWater);

        assertThat(steam.isEvaporated()).isTrue();
        assertThat(steam.getTemperature()).isEqualTo(100);
        assertThat(steam.getAmount()).isEqualTo(testWater.getAmount());
    }
}