package coffee.machine.modules;

import coffee.machine.components.containers.Container;
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
    private Container heaterContainer;

    @Mock
    private Heater heater;

    @InjectMocks
    private HeatingModuleImpl heatingModule;

    @Test
    void shouldCallHeater() {
        heatingModule.heat(200);

        verify(heater, times(1)).heat(200);
        verifyNoMoreInteractions(heater);
        verifyNoInteractions(heaterContainer);
    }

    @Test
    void shouldPassCapacityCheck() {
        int waterNeeded = 200;
        given(heaterContainer.maxAmount()).willReturn(1000);

        heatingModule.checkCapacity(waterNeeded);

        verify(heaterContainer, times(1)).maxAmount();
        verifyNoMoreInteractions(heaterContainer);
        verifyNoInteractions(heater);
    }

    @Test
    void shouldFailCapacityCheck() {
        int waterNeeded = 200;
        given(heaterContainer.maxAmount()).willReturn(50);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> heatingModule.checkCapacity(waterNeeded));

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("The heating module container is too small"));

        verify(heaterContainer, times(1)).maxAmount();
        verifyNoMoreInteractions(heaterContainer);
        verifyNoInteractions(heater);
    }
}