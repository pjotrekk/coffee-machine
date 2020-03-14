package coffee.machine.components;

import coffee.machine.ingredients.CoffeeGrain;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GrounderTest {

    private Grounder grounder = Grounder.create();

    @Test
    void shouldReturnGroundedCoffee() {
        CoffeeGrain coffeeGrain = CoffeeGrain.of(50, false);
        CoffeeGrain groundedCoffee = grounder.ground(coffeeGrain);

        assertThat(groundedCoffee.getAmount()).isEqualTo(coffeeGrain.getAmount());
        assertThat(groundedCoffee.isGrounded()).isTrue();
    }

    @Test
    void shouldNotGroundAlreadyGroundedCoffee() {
        CoffeeGrain coffeeGrain = CoffeeGrain.of(50, true);

        AssertionError error = assertThrows(AssertionError.class, () -> grounder.ground(coffeeGrain));

        assertThat(error).isNotNull().hasMessageContaining("Coffee grain already grounded");
    }
}