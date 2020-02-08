package coffee.machine.components;

import coffee.machine.ingredients.Water;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    Water water = Water.of(200, 23, false);
    Tank<Water> tank;

    @BeforeEach
    void setUp() {
        tank = new Tank<>(water, 500);
    }

    @Test
    void shouldSetCurrentAmountWithoutOverflow() {
        tank.setCurrentAmount(500);
        assertEquals(500, tank.getCurrentAmount());
        assertFalse(tank.isOverflown());
    }

    @Test
    void shouldAddAmountAndBecomeOverflown() {
        tank.addAmount(700);
        assertEquals(900, tank.getCurrentAmount());
        assertTrue(tank.isOverflown());
    }

    @Test
    void shouldSplitItsIngredient() {
        Water splitted = tank.acquire(100);

        assertEquals(100, splitted.getAmount());
        assertFalse(splitted.isEvaporated());
        assertEquals(water.getTemperature(), splitted.getTemperature());

        assertEquals(100, water.getAmount());
    }

    @Test
    void shouldNotAllowToAcquireMoreThanIngredientAmount() {
        AssertionError error = assertThrows(AssertionError.class, () -> tank.acquire(300));

        assertThat(error).isNotNull().hasMessageContaining("Insufficient amount in tank");
    }

    @Test
    void shouldNotAllowAmountBelowZero() {
        AssertionError error = assertThrows(AssertionError.class, () -> tank.setCurrentAmount(-1));

        assertThat(error).isNotNull().hasMessageContaining("Amount cannot be below zero");
    }

}