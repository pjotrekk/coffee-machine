package coffee.machine.modules;

import coffee.machine.components.foamers.Foamer;
import coffee.machine.components.pumps.Pump;
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

    @Mock
    private Foamer foamer;

    private MilkModule milkModule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        milkModule = MilkModuleImpl.of(milkHeatingModule, milkToHeaterPump, milkToCupPump, foamer);
    }

    @Test
    void shouldCallHeaterToCheckCapacity() {
        int milkAmount = 200;
        milkModule.checkMilkContainer(milkAmount);

        verify(milkHeatingModule, times(1)).checkCapacity(milkAmount);
        verifyNoMoreInteractions(milkHeatingModule);
        verifyNoInteractions(milkToHeaterPump, milkToCupPump);
    }

    @Test
    void shouldPrepareMilk() {
        int milkAmount = 200;
        milkModule.prepareMilk(milkAmount);

        verify(milkToHeaterPump, times(1)).pump(milkAmount);
        verify(milkHeatingModule, times(1)).heat(milkAmount);
        verify(milkToCupPump, times(1)).pump(milkAmount);
        verifyNoMoreInteractions(milkToHeaterPump, milkHeatingModule, milkToCupPump);
        verifyNoInteractions(foamer);
    }

    @Test
    void shouldPrepareFoamedMilk() {
        int milkAmount = 200;
        milkModule.prepareFoamedMilk(milkAmount);

        verify(milkToHeaterPump, times(1)).pump(milkAmount);
        verify(milkHeatingModule, times(1)).heat(milkAmount);
        verify(foamer, times(1)).foam(milkAmount);
        verify(milkToCupPump, times(1)).pump(milkAmount);
        verifyNoMoreInteractions(milkToHeaterPump, milkHeatingModule, milkToCupPump, foamer);
    }

}