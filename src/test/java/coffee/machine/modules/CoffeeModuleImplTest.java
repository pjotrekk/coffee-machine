package coffee.machine.modules;

import coffee.machine.components.grounders.Grounder;
import coffee.machine.components.pots.Pot;
import coffee.machine.components.containers.Tank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CoffeeModuleImplTest {

    @Mock
    private Tank coffeeTank;

    @Mock
    private Pot coffeePot;

    @Mock
    private Grounder grounder;

    private CoffeeModule coffeeModule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        coffeeModule = CoffeeModuleImpl.of(coffeeTank, coffeePot, grounder);
    }

    @Test
    void shouldPassCapacityCheck() {
        int coffeeNeeded = 20;
        given(coffeeTank.amount()).willReturn(500);

        coffeeModule.checkCapacity(coffeeNeeded);

        verify(coffeeTank, times(1)).amount();
        verifyNoMoreInteractions(coffeeTank);
        verifyNoInteractions(coffeePot, grounder);
    }

    @Test
    void shouldFailCapacityCheck() {
        int coffeeNeeded = 50;
        given(coffeeTank.amount()).willReturn(20);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> coffeeModule.checkCapacity(coffeeNeeded));

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Insufficient coffee amount. Only 20mg left"));

        verify(coffeeTank, times(2)).amount();
        verifyNoMoreInteractions(coffeeTank);
        verifyNoInteractions(coffeePot, grounder);
    }

    @Test
    void shouldCallGrounder() {
        int coffeeAmount = 20;
        coffeeModule.ground(coffeeAmount);

        verify(grounder, times(1)).ground(coffeeAmount);
        verifyNoMoreInteractions(grounder);
        verifyNoInteractions(coffeePot, coffeeTank);
    }

    @Test
    void shouldCallFlipOnCoffeePot() {
        coffeeModule.flipUsedCoffee();

        verify(coffeePot, times(1)).flip();
        verifyNoMoreInteractions(coffeePot);
        verifyNoInteractions(grounder, coffeeTank);
    }
}