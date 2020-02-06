package coffee.machine.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    Tank tank;

    @BeforeEach
    void setUp() {
        tank = Tank.of(500);
    }

    @Test
    void shouldSetCurrentAmountWithoutOverflow() {
        tank.setCurrentAmount(500);
        assertEquals(500, tank.getCurrentAmount());
        assertFalse(tank.isOverflown());
    }

    @Test
    void shouldSetCurrentAmountAndBecomeOverflown() {
        tank.setCurrentAmount(700);
        assertEquals(700, tank.getCurrentAmount());
        assertTrue(tank.isOverflown());
    }

}