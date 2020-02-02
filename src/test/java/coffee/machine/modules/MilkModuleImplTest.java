package coffee.machine.modules;

import coffee.machine.components.Pump;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class MilkModuleImplTest {

    @Mock
    private HeatingModule milkHeatingModule;

    @Mock
    private Pump milkToHeaterPump;

    @Mock
    private Pump milkToCupPump;

    private MilkModule milkModule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        milkModule = MilkModuleImpl.of(milkHeatingModule, milkToHeaterPump, milkToCupPump);
    }

    @Test
    void shouldCallHeaterToCheckCapacity() {
        milkModule.checkMilkContainer(200);

        verify(milkHeatingModule, times(1)).checkCapacity(200);
        verifyNoMoreInteractions(milkHeatingModule);
        verifyNoInteractions(milkToHeaterPump, milkToCupPump);
    }

    @Test
    void shouldMoveMilkToHeater() {
        milkModule.moveMilkToHeater(200);

        verify(milkToHeaterPump, times(1)).pump(200);
        verifyNoMoreInteractions(milkToHeaterPump);
        verifyNoInteractions(milkHeatingModule, milkToCupPump);
    }

    @Test
    void shouldMoveMilkToCup() {
        milkModule.moveMilkToCup(200);

        verify(milkToCupPump, times(1)).pump(200);
        verifyNoMoreInteractions(milkToCupPump);
        verifyNoInteractions(milkHeatingModule, milkToHeaterPump);
    }

    @Test
    void shouldCallHeaterToHeat() {
        milkModule.heatMilk(200);

        verify(milkHeatingModule, times(1)).heat(200);
        verifyNoMoreInteractions(milkHeatingModule);
        verifyNoInteractions(milkToHeaterPump, milkToCupPump);
    }
}