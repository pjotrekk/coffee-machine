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
        waterModule = new WaterModuleImpl(waterTank, waterPump);
    }

    @Test
    void shouldPassCapacityCheck() {
        int waterNeeded = 200;
        given(waterTank.amount()).willReturn(1000);

        waterModule.checkCapacity(waterNeeded);

        verify(waterTank, times(1)).amount();
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(waterPump);
    }

    @Test
    void shouldFailCapacityCheck() {
        int waterNeeded = 200;
        given(waterTank.amount()).willReturn(50);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> waterModule.checkCapacity(waterNeeded));

        assertEquals(exception.getStatus(), HttpStatus.EXPECTATION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Insufficient water amount. Only 50ml left"));

        verify(waterTank, times(2)).amount();
        verifyNoMoreInteractions(waterTank);
        verifyNoInteractions(waterPump);
    }

    @Test
    void moveWaterToHeater() {
        waterModule.moveWaterToHeater(200);

        verify(waterPump, times(1)).suction(200);
        verifyNoMoreInteractions(waterPump);
        verifyNoInteractions(waterTank);
    }
}