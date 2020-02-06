package coffee.machine.modules;

import coffee.machine.components.containers.Tank;
import coffee.machine.components.heaters.Heater;
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
class HeatingModuleImplTest {

    @Mock
    private Tank heaterTank;

    @Mock
    private Heater heater;

    @InjectMocks
    private HeatingModuleImpl heatingModule;

    @Test
    void shouldCallHeater() {
        heatingModule.heat(200);

        verify(heater, times(1)).heat(200);
        verifyNoMoreInteractions(heater);
        verifyNoInteractions(heaterTank);
    }

    @Test
    void shouldPassCapacityCheck() {
        int waterNeeded = 200;
        given(heaterTank.getCapacity()).willReturn(1000);

        heatingModule.checkCapacity(waterNeeded);

        verify(heaterTank, times(1)).getCapacity();
        verifyNoMoreInteractions(heaterTank);
        verifyNoInteractions(heater);
    }

    @Test
    void shouldFailCapacityCheck() {
        int waterNeeded = 200;
        given(heaterTank.getCapacity()).willReturn(50);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> heatingModule.checkCapacity(waterNeeded));

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("The heating module container is too small. Consider disabling " +
                "this coffee program or replace with a bigger tank"));

        verify(heaterTank, times(1)).getCapacity();
        verifyNoMoreInteractions(heaterTank);
        verifyNoInteractions(heater);
    }
}