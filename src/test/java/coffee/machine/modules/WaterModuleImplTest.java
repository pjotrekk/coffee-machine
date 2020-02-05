package coffee.machine.modules;

import coffee.machine.components.containers.Tank;
import coffee.machine.components.pumps.Pump;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WaterModuleImplTest {

    @Mock
    private Tank waterTank;

    @Mock
    private Pump waterPump;

    @Mock
    private HeatingModule waterHeatingModule;

    private WaterModule waterModule;

    @BeforeEach
    void setUp() {
        waterModule = WaterModuleImpl.of(waterTank, waterPump, waterHeatingModule);
    }

    @Test
    void shouldPassAmountCheck() {
        int waterNeeded = 200;
        given(waterTank.amount()).willReturn(1000);
        given(waterTank.maxAmount()).willReturn(1000);

        waterModule.checkWaterTank(waterNeeded);

        verify(waterTank, times(2)).amount();
        verify(waterTank, times(1)).maxAmount();
        verify(waterHeatingModule, times(1)).checkCapacity(waterNeeded);
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(waterPump);
    }

    @Test
    void shouldFailOverflowCheck() {
        int waterNeeded = 200;
        given(waterTank.amount()).willReturn(1100);
        given(waterTank.maxAmount()).willReturn(1000);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> waterModule.checkWaterTank(waterNeeded));

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Water tank overflow!"));

        verify(waterTank, times(1)).amount();
        verify(waterTank, times(1)).maxAmount();
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(waterPump, waterHeatingModule);
    }

    @Test
    void shouldFailAmountCheck() {
        int waterNeeded = 200;
        given(waterTank.amount()).willReturn(50);
        given(waterTank.maxAmount()).willReturn(1000);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> waterModule.checkWaterTank(waterNeeded));

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Insufficient water amount. Only 50ml left"));

        verify(waterTank, times(3)).amount();
        verify(waterTank, times(1)).maxAmount();
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