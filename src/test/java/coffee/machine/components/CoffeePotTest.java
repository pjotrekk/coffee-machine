package coffee.machine.components;

import coffee.machine.ingredients.CoffeeEssence;
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
        CoffeeGrain groundedCoffee = CoffeeGrain.of(50, true, false);

        CoffeeEssence essence = coffeePot.combineSteamAndGroundedCoffee(steam, groundedCoffee);

        assertThat(essence.getAmount()).isEqualTo(100);
        assertThat(essence.getCoffeeExtract()).isEqualTo(50);
        assertThat(wastesTank.getCurrentAmount()).isEqualTo(50);
    }

    @Test
    void shouldFailOnWaterNotEvaporated() {
        Water steam = Water.of(100, 100, false);
        CoffeeGrain groundedCoffee = CoffeeGrain.of(50, true, false);

        AssertionError error = assertThrows(AssertionError.class, () ->
                coffeePot.combineSteamAndGroundedCoffee(steam, groundedCoffee));

        assertThat(error).isNotNull().hasMessageContaining("Water in coffee pot is not evaporated");
    }

    @Test
    void shouldFailOnCoffeeGrainNotGrounded() {
        Water steam = Water.of(100, 100, true);
        CoffeeGrain groundedCoffee = CoffeeGrain.of(50, false, false);

        AssertionError error = assertThrows(AssertionError.class, () ->
                coffeePot.combineSteamAndGroundedCoffee(steam, groundedCoffee));

        assertThat(error).isNotNull().hasMessageContaining("Coffee in coffee pot is not grounded");
    }

    @Test
    void shouldFailOnCoffeeGrainUsed() {
        Water steam = Water.of(100, 100, true);
        CoffeeGrain groundedCoffee = CoffeeGrain.of(50, true, true);

        AssertionError error = assertThrows(AssertionError.class, () ->
                coffeePot.combineSteamAndGroundedCoffee(steam, groundedCoffee));

        assertThat(error).isNotNull().hasMessageContaining("Coffee in coffee pot has already been used");
    }
}