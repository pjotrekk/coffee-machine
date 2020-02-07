package coffee.machine.controllers;

import coffee.machine.components.Tank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TankController.class)
class TankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Tank tank;

    @Test
    public void shouldRefillWaterCorrectly() throws Exception {
        given(tank.getCapacity()).willReturn(500);
        given(tank.isOverflown()).willReturn(false);

        this.mockMvc.perform(put("/tanks/water")
                .param("amount", "500"))
                .andExpect(status().isOk());

        verify(tank, times(1)).setCurrentAmount(500);
        verify(tank, times(1)).isOverflown();
        verifyNoMoreInteractions(tank);
    }

    @Test
    public void shouldRefillWaterAndInformAboutOverflow() throws Exception {
        given(tank.getCapacity()).willReturn(500);
        given(tank.isOverflown()).willReturn(true);

        Exception exception = this.mockMvc.perform(put("/tanks/water")
                .param("amount", "800"))
                .andExpect(status().isPayloadTooLarge())
                .andReturn().getResolvedException();

        assertThat(exception).isNotNull()
                .hasMessageContaining("Water tank overflow! You should reduce the amount" +
                        " of water to the maximum of 500ml");

        verify(tank, times(1)).setCurrentAmount(800);
        verify(tank, times(1)).isOverflown();
        verify(tank, times(1)).getCapacity();
        verifyNoMoreInteractions(tank);
    }

    @Test
    public void shouldRefillCoffeeCorrectly() throws Exception {
        given(tank.getCapacity()).willReturn(500);
        given(tank.isOverflown()).willReturn(false);

        this.mockMvc.perform(put("/tanks/coffee")
                .param("amount", "500"))
                .andExpect(status().isOk());

        verify(tank, times(1)).setCurrentAmount(500);
        verify(tank, times(1)).isOverflown();
        verifyNoMoreInteractions(tank);
    }

    @Test
    public void shouldRefillCoffeeAndInformAboutOverflow() throws Exception {
        given(tank.getCapacity()).willReturn(500);
        given(tank.isOverflown()).willReturn(true);

        Exception exception = this.mockMvc.perform(put("/tanks/coffee")
                .param("amount", "800"))
                .andExpect(status().isPayloadTooLarge())
                .andExpect(status().isPayloadTooLarge())
                .andReturn().getResolvedException();

        assertNotNull(exception);
        assertThat(exception.getMessage()).contains("Coffee tank overflow! You should " +
                "reduce the amount of coffee to the maximum of 500mg");

        verify(tank, times(1)).setCurrentAmount(800);
        verify(tank, times(1)).isOverflown();
        verify(tank, times(1)).getCapacity();
        verifyNoMoreInteractions(tank);
    }

    @Test
    public void shouldClearWastes() throws Exception {
        this.mockMvc.perform(put("/tanks/wastes"))
                .andExpect(status().isOk());

        verify(tank, times(1)).setCurrentAmount(0);
        verifyNoMoreInteractions(tank);
    }

}