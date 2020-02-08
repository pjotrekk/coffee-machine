package coffee.machine.modules;

import coffee.machine.components.Tank;
import coffee.machine.ingredients.CoffeeGrain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WastesModuleTest {

    @Mock
    private Tank<CoffeeGrain> wastesTank;

    @InjectMocks
    private WastesModule wastesModule;

    @Test
    void shouldPassOverflowCheck() {
        given(wastesTank.isOverflown()).willReturn(false);

        wastesModule.checkOverflow();

        verify(wastesTank, times(1)).isOverflown();
        verifyNoMoreInteractions(wastesTank);
    }

    @Test
    void shouldFailOverflowCheck() {
        given(wastesTank.isOverflown()).willReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> wastesModule.checkOverflow());

        assertEquals(exception.getStatus(), HttpStatus.PRECONDITION_FAILED);
        assertThat(exception).isNotNull()
                .hasMessageContaining("Wastes tank overflow! You should remove the used coffee.");

        verify(wastesTank, times(1)).isOverflown();
        verifyNoMoreInteractions(wastesTank);
    }
}