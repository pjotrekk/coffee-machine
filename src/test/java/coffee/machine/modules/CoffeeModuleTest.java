package coffee.machine.modules;

import coffee.machine.components.CoffeePot;
import coffee.machine.components.Grounder;
import coffee.machine.components.Tank;
import coffee.machine.ingredients.CoffeeGrain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoffeeModuleTest {

    @Mock
    private Tank<CoffeeGrain> coffeeTank;

    @Mock
    private Grounder grounder;

    @Mock
    private CoffeePot coffeePot;

    @InjectMocks
    private CoffeeModule coffeeModule;

    @Test
    void shouldPassCapacityCheck() {
        given(coffeeTank.getCurrentAmount()).willReturn(500);
        given(coffeeTank.isOverflown()).willReturn(false);

        coffeeModule.checkCapacity(20);

        verify(coffeeTank, times(1)).getCurrentAmount();
        verify(coffeeTank, times(1)).isOverflown();
        verifyNoMoreInteractions(coffeeTank);
        verifyNoInteractions(grounder);
    }

    @Test
    void shouldFailCapacityCheckBecauseOfInsufficientCoffee() {
        int coffeeNeeded = 50;
        given(coffeeTank.getCurrentAmount()).willReturn(20);
        given(coffeeTank.isOverflown()).willReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> coffeeModule.checkCapacity(coffeeNeeded));

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Insufficient coffee amount. Only 20mg left." +
                " You should refill the coffee tank"));

        verify(coffeeTank, times(2)).getCurrentAmount();
        verify(coffeeTank, times(1)).isOverflown();
        verifyNoMoreInteractions(coffeeTank);
        verifyNoInteractions(grounder);
    }

    @Test
    void shouldFailCapacityCheckBecauseOfOverflow() {
        given(coffeeTank.getCapacity()).willReturn(200);
        given(coffeeTank.isOverflown()).willReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> coffeeModule.checkCapacity(50));

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Coffee tank overflow. Reduce the coffee amount" +
                " to be below the maximum value of 200mg"));

        verify(coffeeTank, times(1)).getCapacity();
        verify(coffeeTank, times(1)).isOverflown();
        verifyNoMoreInteractions(coffeeTank);
        verifyNoInteractions(grounder);
    }

    @Test
    void shouldCallGrounder() {
        int amountAcquired = 20;
        CoffeeGrain coffeeGrain = CoffeeGrain.of(amountAcquired, false, false);

        given(coffeeTank.acquire(anyInt())).willReturn(coffeeGrain);
        given(grounder.ground(any())).willReturn(coffeeGrain);

        coffeeModule.ground(amountAcquired);

        verify(coffeeTank, times(1)).acquire(amountAcquired);
        verify(grounder, times(1)).ground(coffeeGrain);
        verifyNoMoreInteractions(grounder, coffeeTank);
    }

}