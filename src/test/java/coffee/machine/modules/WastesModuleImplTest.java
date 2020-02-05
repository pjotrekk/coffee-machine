package coffee.machine.modules;

import coffee.machine.components.containers.Tank;
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
class WastesModuleImplTest {

    @Mock
    private Tank wastesTank;

    @InjectMocks
    private WastesModuleImpl wastesModule;

    @Test
    void shouldPassOverflowCheck() {
        given(wastesTank.amount()).willReturn(500);
        given(wastesTank.maxAmount()).willReturn(1000);

        wastesModule.checkOverflow();

        verify(wastesTank, times(1)).amount();
        verify(wastesTank, times(1)).maxAmount();
        verifyNoMoreInteractions(wastesTank);
    }

    @Test
    void shouldFailOverflowCheck() {
        given(wastesTank.amount()).willReturn(1100);
        given(wastesTank.maxAmount()).willReturn(1000);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> wastesModule.checkOverflow());

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Wastes tank overflow!"));

        verify(wastesTank, times(1)).amount();
        verify(wastesTank, times(1)).maxAmount();
        verifyNoMoreInteractions(wastesTank);
    }
}