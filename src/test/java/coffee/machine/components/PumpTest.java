package coffee.machine.components;

import coffee.machine.ingredients.Milk;
import coffee.machine.ingredients.Water;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PumpTest {

    private LiquidTank from;

    private LiquidTank to;

    private Pump pump;

    @BeforeEach
    void setUp() {
        from = LiquidTank.of(Water.of(200), 500);
        to = LiquidTank.of(Water.of(300), 450);
        pump = Pump.of(from, to);
    }

    @Test
    void shouldPumpCorrectly() {
        pump.pump(150);
        assertEquals(50, from.getCurrentAmount());
        assertEquals(450, to.getCurrentAmount());
    }

    @Test
    void shouldNotBeAbleToPumpIfAmountFromExceeded() {
        AssertionError error = assertThrows(AssertionError.class, () -> pump.pump(210));

        assertThat(error).isNotNull().hasMessageContaining("Cannot pump from a tank that has not enough liquid");

        assertEquals(200, from.getCurrentAmount());
        assertEquals(300, to.getCurrentAmount());
    }

    @Test
    void shouldNotBeAbleToPumpIfAmountToWouldOverflow() {
        AssertionError error = assertThrows(AssertionError.class, () -> pump.pump(200));

        assertThat(error).isNotNull().hasMessageContaining("Cannot pump because the destination tank would overflow");

        assertEquals(200, from.getCurrentAmount());
        assertEquals(300, to.getCurrentAmount());
    }

    @Test
    void shouldNotCreatePumpConnectingDifferentTypesOfLiquid() {
        AssertionError error = assertThrows(AssertionError.class, () ->
                Pump.of(LiquidTank.of(Water.create(), 100), LiquidTank.of(Milk.create(), 100)));

        assertThat(error).isNotNull().hasMessageContaining("Pump has to be connected to the tanks with the same ingredient");
    }
}