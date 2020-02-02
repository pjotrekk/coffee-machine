package coffee.machine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CoffeeController.class)
public class CoffeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoffeeMachine coffeeMachine;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAcceptCorrectCoffee() throws Exception {
        this.mockMvc.perform(post("/coffee")
                .param("coffeeKind", "ESPRESSO"))
                .andExpect(status().isCreated());

        verify(coffeeMachine, times(1)).makeCoffee(CoffeeKind.ESPRESSO);
        verifyNoMoreInteractions(coffeeMachine);
    }

    @Test
    public void shouldAcceptCoffeeWithoutUppercase() throws Exception {
        this.mockMvc.perform(post("/coffee")
                .param("coffeeKind", "ameRIcano"))
                .andExpect(status().isCreated());

        verify(coffeeMachine, times(1)).makeCoffee(CoffeeKind.AMERICANO);
        verifyNoMoreInteractions(coffeeMachine);
    }

    @Test
    public void shouldNotAcceptNotRegisteredCoffeeKind() throws Exception {
        this.mockMvc.perform(post("/coffee")
                .param("coffeeKind", "noSuchCoffee"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(coffeeMachine);
    }

}