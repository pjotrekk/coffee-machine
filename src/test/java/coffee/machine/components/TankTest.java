package coffee.machine.components;

import coffee.machine.ingredients.CoffeeGrain;
import coffee.machine.ingredients.Milk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    Tank tank;

    @BeforeEach
    void setUp() {
        tank = SolidTank.of(CoffeeGrain.of(200), 500);
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
    void shouldReduceAmount() {
        tank.reduceAmount(100);
        assertEquals(100, tank.getCurrentAmount());
    }

    @Test
    void shouldNotAllowToReduceBelowZero() {
        int exceededAmount = tank.getCurrentAmount() + 1;
        AssertionError error = assertThrows(AssertionError.class, () -> tank.reduceAmount(exceededAmount));

        assertThat(error).isNotNull().hasMessageContaining("Cannot reduce the tank's ingredient amount below zero");
    }

    @Test
    void shouldNotAllowAmountBelowZero() {
        AssertionError error = assertThrows(AssertionError.class, () -> tank.setCurrentAmount(-1));

        assertThat(error).isNotNull().hasMessageContaining("Amount cannot be below zero");
    }

    @Test
    void shouldReturnProperIngredientType() {
        Tank tank = LiquidTank.of(Milk.create(), 100);
        assertThat(tank.getIngredient()).isInstanceOf(Milk.class);
    }
}