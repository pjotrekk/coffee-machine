package coffee.machine.components;

import coffee.machine.ingredients.CoffeeEssence;
import coffee.machine.ingredients.CoffeeWastes;
import coffee.machine.ingredients.Water;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoffeePotTest {
    private CoffeeWastes coffeeWastes;
    private SolidTank wastesTank;
    private CoffeePot coffeePot;

    @BeforeEach
    void setUp() {
        coffeeWastes = CoffeeWastes.of(40);
        wastesTank = SolidTank.of(coffeeWastes, 200);
        coffeePot = CoffeePot.of(wastesTank);
    }

    @Test
    void shouldCreateCoffeeEssenceAndRemoveItsWastes() {
        coffeePot.addGroundedCoffee(30);
        CoffeeEssence essence = coffeePot.combineSteamAndGroundedCoffee(Water.of(100, 20));
        assertThat(essence.getAmount()).isEqualTo(100);
        assertThat(essence.getCoffeeExtract()).isEqualTo(30);
        assertThat(wastesTank.getCurrentAmount()).isEqualTo(70);
        assertThat(coffeePot.getGroundedCoffeeAmount()).isEqualTo(0);
        assertThat(coffeePot.getCoffeeWastesAmount()).isEqualTo(0);
    }
}