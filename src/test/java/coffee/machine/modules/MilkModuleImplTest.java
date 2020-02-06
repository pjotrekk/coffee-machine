package coffee.machine.modules;

import coffee.machine.components.foamers.Foamer;
import coffee.machine.components.pumps.Pump;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
        milkModule = MilkModuleImpl.of(milkHeatingModule, milkToHeaterPump, milkToCupPump, foamer);
    }

    @Test
    void shouldCallHeaterToCheckItsCapacity() {
        int milkAmount = 200;
        milkModule.checkMilkContainer(milkAmount);

        verify(milkHeatingModule, times(1)).checkCapacity(milkAmount);
        verifyNoMoreInteractions(milkHeatingModule);
        verifyNoInteractions(milkToHeaterPump, milkToCupPump);
    }

    @Test
    void shouldCallProperComponentsToPrepareMilk() {
        int milkAmount = 200;
        milkModule.prepareMilk(milkAmount, false);

        verify(milkToHeaterPump, times(1)).pump(milkAmount);
        verify(milkHeatingModule, times(1)).heat(milkAmount);
        verify(milkToCupPump, times(1)).pump(milkAmount);
        verifyNoMoreInteractions(milkToHeaterPump, milkHeatingModule, milkToCupPump);
        verifyNoInteractions(foamer);
    }

    @Test
    void shouldCallProperComponentsToPrepareFoamedMilk() {
        int milkAmount = 200;
        milkModule.prepareMilk(milkAmount, true);

        verify(milkToHeaterPump, times(1)).pump(milkAmount);
        verify(milkHeatingModule, times(1)).heat(milkAmount);
        verify(foamer, times(1)).foam(milkAmount);
        verify(milkToCupPump, times(1)).pump(milkAmount);
        verifyNoMoreInteractions(milkToHeaterPump, milkHeatingModule, milkToCupPump, foamer);
    }

}