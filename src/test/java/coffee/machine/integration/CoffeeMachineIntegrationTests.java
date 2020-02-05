package coffee.machine.integration;

import coffee.machine.CoffeeKind;
import coffee.machine.CoffeeMachine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoffeeMachineIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@SpyBean
	private CoffeeMachine coffeeMachine;

	@Test
	public void shouldAcceptCoffeeRequest() throws Exception {
		this.mockMvc.perform(post("/coffee")
				.param("coffeeKind", "Cappuccino"))
				.andExpect(status().isCreated());

		verify(coffeeMachine, times(1)).makeCoffee(CoffeeKind.CAPPUCCINO);
		verifyNoMoreInteractions(coffeeMachine);
	}

	@Test
	public void shouldNotAcceptCoffeeRequestWithUnknownCoffee() throws Exception {
		this.mockMvc.perform(post("/coffee")
				.param("coffeeKind", "Unknown"))
				.andExpect(status().isBadRequest());

		verifyNoInteractions(coffeeMachine);
	}

}
