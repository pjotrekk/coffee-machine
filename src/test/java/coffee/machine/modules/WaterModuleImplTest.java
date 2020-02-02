package coffee.machine.modules;

import coffee.machine.pumps.Pump;
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

class WaterModuleImplTest {

    @Mock
    private WaterTank waterTank;

    @Mock
    private Pump waterPump;

    private WaterModule waterModule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        waterModule = WaterModuleImpl.of(waterTank, waterPump);
    }

    @Test
    void shouldPassCapacityCheck() {
        int waterNeeded = 200;
        given(waterTank.amount()).willReturn(1000);
        given(waterTank.maxCapacity()).willReturn(1000);

        waterModule.checkWaterTank(waterNeeded);

        verify(waterTank, times(2)).amount();
        verify(waterTank, times(1)).maxCapacity();
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(waterPump);
    }

    @Test
    void shouldFailOverflowCheck() {
        int waterNeeded = 200;
        given(waterTank.amount()).willReturn(1100);
        given(waterTank.maxCapacity()).willReturn(1000);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> waterModule.checkWaterTank(waterNeeded));

        assertEquals(exception.getStatus(), HttpStatus.EXPECTATION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Water tank overflow!"));

        verify(waterTank, times(1)).amount();
        verify(waterTank, times(1)).maxCapacity();
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(waterPump);
    }

    @Test
    void shouldFailCapacityCheck() {
        int waterNeeded = 200;
        given(waterTank.amount()).willReturn(50);
        given(waterTank.maxCapacity()).willReturn(1000);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> waterModule.checkWaterTank(waterNeeded));

        assertEquals(exception.getStatus(), HttpStatus.EXPECTATION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Insufficient water amount. Only 50ml left"));

        verify(waterTank, times(3)).amount();
        verify(waterTank, times(1)).maxCapacity();
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(waterPump);
    }

    @Test
    void shouldMoveWaterToHeater() {
        waterModule.moveWaterToHeater(200);

        verify(waterPump, times(1)).suction(200);
        verifyNoMoreInteractions(waterPump);
        verifyNoInteractions(waterTank);
    }
}