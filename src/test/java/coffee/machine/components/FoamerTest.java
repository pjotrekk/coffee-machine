package coffee.machine.components;

import coffee.machine.ingredients.Milk;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FoamerTest {
    private Foamer foamer = Foamer.create();

    @Test
    void shouldSetFoamedToTrue() {
        Milk milk = Milk.of(100, 20);
        assertThat(milk.isFoamed()).isFalse();
        foamer.foam(milk);
        assertThat(milk.isFoamed()).isTrue();
    }
}