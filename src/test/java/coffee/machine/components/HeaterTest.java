package coffee.machine.components;

import coffee.machine.ingredients.Milk;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeaterTest {

    private Milk testMilk = Milk.of(100, 20, false);

    private Heater heater = Heater.of(Milk.PERFECT_TEMPERATURE);

    @Test
    void heat() {
        Milk heatedMilk = heater.heat(testMilk);

        assertThat(heatedMilk.getTemperature()).isEqualTo(Milk.PERFECT_TEMPERATURE);
        assertThat(heatedMilk.getAmount()).isEqualTo(testMilk.getAmount());
        assertThat(heatedMilk.isFoamed()).isEqualTo(testMilk.isFoamed());
    }
}