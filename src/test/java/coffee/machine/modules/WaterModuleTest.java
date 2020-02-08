package coffee.machine.modules;

import coffee.machine.components.Evaporator;
import coffee.machine.components.Tank;
import coffee.machine.ingredients.Water;
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
    private Tank<Water> waterTank;

    @Mock
    private Evaporator evaporator;

    @InjectMocks
    private WaterModule waterModule;

    Water testWater = Water.of(200, 23, false);

    @Test
    void shouldPassAmountCheck() {
        int waterNeeded = 200;
        given(waterTank.getCurrentAmount()).willReturn(1000);
        given(waterTank.isOverflown()).willReturn(false);

        waterModule.checkWaterTank(waterNeeded);

        verify(waterTank, times(1)).getCurrentAmount();
        verify(waterTank, times(1)).isOverflown();
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(evaporator);
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
        verifyNoInteractions(evaporator);
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
        verifyNoInteractions(evaporator);
    }

    @Test
    void shouldCallProperComponentsToPrepareTheSteam() {
        int waterAmount = 200;
        given(waterTank.acquire(anyInt())).willReturn(testWater);
        given(evaporator.evaporate(any())).willReturn(testWater);

        waterModule.prepareSteam(waterAmount);

        verify(waterTank, times(1)).acquire(waterAmount);
        verify(evaporator, times(1)).evaporate(testWater);
        verifyNoMoreInteractions(waterTank, evaporator);
    }

}