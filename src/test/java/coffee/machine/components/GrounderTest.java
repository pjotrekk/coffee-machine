package coffee.machine.components;

import coffee.machine.ingredients.CoffeeGrain;
import coffee.machine.ingredients.CoffeeWastes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GrounderTest {

    private SolidTank coffeeTank;

    private CoffeePot coffeePot;

    private Grounder grounder;

    @BeforeEach
    void setUp() {
        coffeeTank = SolidTank.of(CoffeeGrain.of(50), 200);
        coffeePot = CoffeePot.of(SolidTank.of(CoffeeWastes.of(0), 300));
        grounder = Grounder.of(coffeeTank, coffeePot);
    }

    @Test
    void shouldSubtractItsCoffeeGrainAndIncreaseGroundedCoffeeInPot() {
        grounder.ground(30);
        assertThat(coffeeTank.getCurrentAmount()).isEqualTo(20);
        assertThat(coffeePot.getGroundedCoffeeAmount()).isEqualTo(30);
    }
}