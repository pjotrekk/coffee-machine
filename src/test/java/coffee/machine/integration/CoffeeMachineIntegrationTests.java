package coffee.machine.integration;

import coffee.machine.CoffeeMachineApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CoffeeMachineApplication.class })
@WebAppConfiguration
public class CoffeeMachineIntegrationTests {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void shouldAcceptCoffeeRequest() throws Exception {
		this.mockMvc.perform(post("/coffee")
				.param("coffeeKind", "Cappuccino"))
				.andExpect(status().isCreated());
	}

	@Test
	public void shouldNotAcceptCoffeeRequestWithUnknownCoffee() throws Exception {
		this.mockMvc.perform(post("/coffee")
				.param("coffeeKind", "Unknown"))
				.andExpect(status().isBadRequest());
	}

}
