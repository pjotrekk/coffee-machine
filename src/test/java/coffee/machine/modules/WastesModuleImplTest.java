package coffee.machine.modules;

import coffee.machine.components.Tank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class WastesModuleImplTest {

    @Mock
    private Tank wastesTank;

    private WastesModule wastesModule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        wastesModule = WastesModuleImpl.of(wastesTank);
    }

    @Test
    void shouldPassOverflowCheck() {
        given(wastesTank.amount()).willReturn(500);
        given(wastesTank.maxCapacity()).willReturn(1000);

        wastesModule.checkOverflow();

        verify(wastesTank, times(1)).amount();
        verify(wastesTank, times(1)).maxCapacity();
        verifyNoMoreInteractions(wastesTank);
    }

    @Test
    void shouldFailOverflowCheck() {
        given(wastesTank.amount()).willReturn(1100);
        given(wastesTank.maxCapacity()).willReturn(1000);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> wastesModule.checkOverflow());

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Wastes tank overflow!"));

        verify(wastesTank, times(1)).amount();
        verify(wastesTank, times(1)).maxCapacity();
        verifyNoMoreInteractions(wastesTank);
    }
}