package coffee.machine.integration;

import coffee.machine.components.Tank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TanksIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Tank waterTank;

    @Autowired
    private Tank wastesTank;

    @Autowired
    private Tank milkTank;

    @Autowired
    private Tank coffeeTank;

    @BeforeEach
    void setUp() {
        waterTank.setCurrentAmount(0);
        milkTank.setCurrentAmount(0);
        wastesTank.setCurrentAmount(0);
        coffeeTank.setCurrentAmount(0);
    }

    @Test
    void shouldRefillWaterTankProperly() throws Exception {
        this.mockMvc.perform(put("/tanks/water")
                .param("amount", "250"))
                .andExpect(status().isOk());

        assertThat(waterTank.getCurrentAmount()).isEqualTo(250);
    }

    @Test
    void shouldRefillMilkTankProperly() throws Exception {
        this.mockMvc.perform(put("/tanks/milk")
                .param("amount", "200"))
                .andExpect(status().isOk());

        assertThat(milkTank.getCurrentAmount()).isEqualTo(200);
    }

    @Test
    void shouldRefillCoffeeTankAndThrowOverflowException() throws Exception {
        int overflownValue = coffeeTank.getCapacity() + 1;
        Exception exception = this.mockMvc.perform(put("/tanks/coffee")
                .param("amount", Integer.toString(overflownValue)))
                .andExpect(status().isPayloadTooLarge())
                .andReturn().getResolvedException();

        assertThat(exception).isNotNull().hasMessageContaining(String.format("Coffee tank overflow! You should " +
                "reduce the amount of coffee to the maximum of %dmg", coffeeTank.getCapacity()));

        assertThat(coffeeTank.getCurrentAmount()).isEqualTo(overflownValue);
    }

    @Test
    void shouldClearWastesTankProperly() throws Exception {
        wastesTank.setCurrentAmount(200);

        this.mockMvc.perform(put("/tanks/wastes"))
                .andExpect(status().isOk());

        assertThat(wastesTank.getCurrentAmount()).isEqualTo(0);
    }
}
