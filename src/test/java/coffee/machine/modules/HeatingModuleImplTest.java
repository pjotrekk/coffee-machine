package coffee.machine.modules;

import coffee.machine.heaters.Heater;
import coffee.machine.tanks.WaterTank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class HeatingModuleImplTest {

    @Mock
    private WaterTank waterTank;

    @Mock
    private Heater heater;

    private HeatingModule heatingModule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        heatingModule = HeatingModuleImpl.of(waterTank, heater);
    }

    @Test
    void testHeat() {
        heatingModule.heat(200);

        verify(heater, times(1)).heat(200);
        verifyNoMoreInteractions(heater);
        verifyNoInteractions(waterTank);
    }

    @Test
    void shouldPassCapacityCheck() {
        int waterNeeded = 200;
        given(waterTank.maxCapacity()).willReturn(1000);

        heatingModule.checkCapacity(waterNeeded);

        verify(waterTank, times(1)).maxCapacity();
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(heater);
    }

    @Test
    void shouldFailCapacityCheck() {
        int waterNeeded = 200;
        given(waterTank.maxCapacity()).willReturn(50);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> heatingModule.checkCapacity(waterNeeded));

        assertEquals(exception.getStatus(), HttpStatus.EXPECTATION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Heating module water tank is too small for such a coffee!"));

        verify(waterTank, times(1)).maxCapacity();
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(heater);
    }
}