package coffee.machine;

import coffee.machine.controllers.CoffeeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoffeeController.class)
public class CoffeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoffeeMachine coffeeMachine;

    @Test
    public void shouldAcceptCorrectCoffee() throws Exception {
        this.mockMvc.perform(get("/coffee")
                .param("coffeeKind", "ESPRESSO"))
                .andExpect(status().isCreated());

        verify(coffeeMachine, times(1)).makeCoffee(CoffeeKind.ESPRESSO);
        verifyNoMoreInteractions(coffeeMachine);
    }

    @Test
    public void shouldAcceptCoffeeWithoutUppercase() throws Exception {
        this.mockMvc.perform(get("/coffee")
                .param("coffeeKind", "ameRIcano"))
                .andExpect(status().isCreated());

        verify(coffeeMachine, times(1)).makeCoffee(CoffeeKind.AMERICANO);
        verifyNoMoreInteractions(coffeeMachine);
    }

    @Test
    public void shouldNotAcceptNotRegisteredCoffeeKind() throws Exception {
        this.mockMvc.perform(get("/coffee")
                .param("coffeeKind", "noSuchCoffee"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(coffeeMachine);
    }

}