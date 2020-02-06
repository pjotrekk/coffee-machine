package coffee.machine.modules;

import coffee.machine.components.Tank;
import coffee.machine.components.Pump;
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
class WaterModuleTest {

    @Mock
    private Tank waterTank;

    @Mock
    private Pump waterPump;

    @Mock
    private HeatingModule waterHeatingModule;

    @InjectMocks
    private WaterModule waterModule;

    @Test
    void shouldPassAmountCheck() {
        int waterNeeded = 200;
        given(waterTank.getCurrentAmount()).willReturn(1000);
        given(waterTank.isOverflown()).willReturn(false);

        waterModule.checkWaterTank(waterNeeded);

        verify(waterTank, times(1)).getCurrentAmount();
        verify(waterTank, times(1)).isOverflown();
        verify(waterHeatingModule, times(1)).checkCapacity(waterNeeded);
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(waterPump);
    }

    @Test
    void shouldFailOverflowCheck() {
        int waterNeeded = 200;
        given(waterTank.getCapacity()).willReturn(1000);
        given(waterTank.isOverflown()).willReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> waterModule.checkWaterTank(waterNeeded));

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Water tank overflow! You should " +
                "reduce the amount of water to the maximum of 1000ml"));

        verify(waterTank, times(1)).isOverflown();
        verify(waterTank, times(1)).getCapacity();
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(waterPump, waterHeatingModule);
    }

    @Test
    void shouldFailAmountCheck() {
        int waterNeeded = 200;
        given(waterTank.getCurrentAmount()).willReturn(50);
        given(waterTank.isOverflown()).willReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> waterModule.checkWaterTank(waterNeeded));

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Insufficient water amount. Only 50ml left. " +
                "Refill the water tank"));

        verify(waterTank, times(2)).getCurrentAmount();
        verify(waterTank, times(1)).isOverflown();
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(waterPump, waterHeatingModule);
    }

    @Test
    void shouldCallProperComponentsToPrepareWater() {
        int waterAmount = 200;
        waterModule.prepareWater(waterAmount);

        verify(waterPump, times(1)).pump(waterAmount);
        verify(waterHeatingModule, times(1)).heat(waterAmount);
        verifyNoMoreInteractions(waterPump, waterHeatingModule);
        verifyNoInteractions(waterTank);
    }
}