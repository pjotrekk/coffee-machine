package coffee.machine.components;

import coffee.machine.ingredients.Milk;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FoamerTest {
    private Foamer foamer = Foamer.create();

    @Test
    void shouldReturnFoamedMilk() {
        Milk milk = Milk.of(100, 20, false);
        Milk foamedMilk = foamer.foam(milk);

        assertThat(foamedMilk.getAmount()).isEqualTo(milk.getAmount());
        assertThat(foamedMilk.getTemperature()).isEqualTo(milk.getTemperature());
        assertThat(foamedMilk.isFoamed()).isTrue();
    }

    @Test
    void shouldNotFoamAlreadyFoamedMilk() {
        Milk milk = Milk.of(100, 20, true);

        AssertionError error = assertThrows(AssertionError.class, () -> foamer.foam(milk));

        assertThat(error).isNotNull().hasMessageContaining("Milk is already foamed");

    }
}