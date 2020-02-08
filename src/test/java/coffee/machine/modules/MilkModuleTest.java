package coffee.machine.modules;

import coffee.machine.components.Foamer;
import coffee.machine.components.Heater;
import coffee.machine.components.Tank;
import coffee.machine.ingredients.Milk;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MilkModuleTest {

    @Mock
    private Tank<Milk> milkTank;

    @Mock
    private Heater milkHeater;

    @Mock
    private Foamer foamer;

    @InjectMocks
    private MilkModule milkModule;

    private Milk testMilk = Milk.of(200, 23, false);

    @Test
    void shouldCallTankAndHeaterToPrepareNonFoamedMilk() {
        int milkAmount = 200;

        given(milkTank.acquire(anyInt())).willReturn(testMilk);
        given(milkHeater.heat(any())).willReturn(testMilk);

        milkModule.prepareMilk(milkAmount, false);

        verify(milkTank, times(1)).acquire(milkAmount);
        verify(milkHeater, times(1)).heat(testMilk);
        verifyNoMoreInteractions(milkTank, milkHeater);
        verifyNoInteractions(foamer);
    }

    @Test
    void shouldCallTankHeaterAndFoamerToPrepareFoamedMilk() {
        int milkAmount = 200;

        given(milkTank.acquire(anyInt())).willReturn(testMilk);
        given(milkHeater.heat(any())).willReturn(testMilk);
        given(foamer.foam(any())).willReturn(testMilk);

        milkModule.prepareMilk(milkAmount, true);

        verify(milkTank, times(1)).acquire(milkAmount);
        verify(milkHeater, times(1)).heat(testMilk);
        verify(foamer, times(1)).foam(testMilk);
        verifyNoMoreInteractions(milkTank, milkHeater, foamer);
    }

    @Test
    void shouldFailAmountCheck() {
        int milkAmount = 1000;

        given(milkTank.getCurrentAmount()).willReturn(200);
        given(milkTank.isOverflown()).willReturn(false);

        ResponseStatusException rse = assertThrows(ResponseStatusException.class, () ->
                milkModule.checkMilkTank(milkAmount));

        assertThat(rse).isNotNull().hasMessageContaining("Insufficient milk amount. Only 200ml left." +
                " Refill the milk tank");
        assertThat(rse.getStatus()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    void shouldFailOverflowCheck() {
        milkTank.setCurrentAmount(2000);

        given(milkTank.getCapacity()).willReturn(400);
        given(milkTank.isOverflown()).willReturn(true);

        ResponseStatusException rse = assertThrows(ResponseStatusException.class, () ->
                milkModule.checkMilkTank(200));

        assertThat(rse).isNotNull().hasMessageContaining("Milk tank overflow! You should reduce the amount " +
                "of milk to the maximum of 400ml");
        assertThat(rse.getStatus()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

}