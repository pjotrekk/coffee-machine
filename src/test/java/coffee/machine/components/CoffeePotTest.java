package coffee.machine.components;

import coffee.machine.coffee.CoffeeEssence;
import coffee.machine.ingredients.CoffeeGrain;
import coffee.machine.ingredients.Water;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoffeePotTest {

    private Tank<CoffeeGrain> wastesTank;

    private CoffeePot coffeePot;

    @BeforeEach
    void setUp() {
        wastesTank = new Tank<>(CoffeeGrain.create(), 300);
        coffeePot = CoffeePot.of(wastesTank);
    }

    @Test
    void shouldCreateCoffeeEssenceAndRemoveUsedCoffeeToWastesTank() {
        Water steam = Water.of(100, 100, true);
        CoffeeGrain groundedCoffee = CoffeeGrain.of(50, true);

        CoffeeEssence essence = coffeePot.combineSteamAndGroundedCoffee(steam, groundedCoffee);

        assertThat(essence.getAmount()).isEqualTo(100);
        assertThat(essence.getCoffeeExtract()).isEqualTo(50);
        assertThat(wastesTank.getCurrentAmount()).isEqualTo(50);
    }

    @Test
    void shouldFailOnWaterNotEvaporated() {
        Water steam = Water.of(100, 100, false);
        CoffeeGrain groundedCoffee = CoffeeGrain.of(50, true);

        AssertionError error = assertThrows(AssertionError.class, () ->
                coffeePot.combineSteamAndGroundedCoffee(steam, groundedCoffee));

        assertThat(error).isNotNull().hasMessageContaining("Water in coffee pot is not evaporated");
    }

    @Test
    void shouldFailOnCoffeeGrainNotGrounded() {
        Water steam = Water.of(100, 100, true);
        CoffeeGrain groundedCoffee = CoffeeGrain.of(50, false);

        AssertionError error = assertThrows(AssertionError.class, () ->
                coffeePot.combineSteamAndGroundedCoffee(steam, groundedCoffee));

        assertThat(error).isNotNull().hasMessageContaining("Coffee in coffee pot is not grounded");
    }

}