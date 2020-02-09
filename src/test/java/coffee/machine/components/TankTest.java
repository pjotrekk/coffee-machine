package coffee.machine.components;

import coffee.machine.ingredients.Water;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    Water testWater = Water.of(200, 23, false);
    Tank<Water> tank;

    @BeforeEach
    void setUp() {
        tank = new Tank<>(Water.of(200, 23, false), 500);
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
        Water acquired = tank.acquire(100);

        assertEquals(100, acquired.getAmount());
        assertFalse(acquired.isEvaporated());
        assertEquals(testWater.getTemperature(), acquired.getTemperature());
        assertEquals(100, tank.getCurrentAmount());
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