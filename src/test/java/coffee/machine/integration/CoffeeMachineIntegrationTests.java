package coffee.machine.integration;

import coffee.machine.Coffee;
import coffee.machine.CoffeeMachine;
import coffee.machine.components.LiquidTank;
import coffee.machine.components.SolidTank;
import coffee.machine.ingredients.CoffeeEssence;
import coffee.machine.ingredients.Milk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static coffee.machine.CoffeeKind.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoffeeMachineIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@SpyBean
	private CoffeeMachine coffeeMachine;

	@Autowired
	private LiquidTank waterTank;

	@Autowired
	private LiquidTank milkTank;

	@Autowired
	private SolidTank coffeeTank;

	@BeforeEach
	void setUp() {
		waterTank.setCurrentAmount(250);
		milkTank.setCurrentAmount(200);
		coffeeTank.setCurrentAmount(150);
	}

	@Test
	public void shouldReturnCappuccinoCoffee() throws Exception {
		this.mockMvc.perform(get("/coffee")
				.param("coffeeKind", "Cappuccino"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.coffeeEssence.amount").value(150))
				.andExpect(jsonPath("$.coffeeEssence.coffeeExtract").value(20))
				.andExpect(jsonPath("$.milk.foamed").value(true))
				.andExpect(jsonPath("$.milk.amount").value(150));

		verify(coffeeMachine, times(1)).makeCoffee(CAPPUCCINO);
		verifyNoMoreInteractions(coffeeMachine);
	}

	@Test
	public void shouldReturnEspressoCoffee() throws Exception {
		this.mockMvc.perform(get("/coffee")
				.param("coffeeKind", "Espresso"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.coffeeEssence.amount").value(50))
				.andExpect(jsonPath("$.coffeeEssence.coffeeExtract").value(40))
				.andExpect(jsonPath("$.milk").doesNotExist());

		verify(coffeeMachine, times(1)).makeCoffee(ESPRESSO);
		verifyNoMoreInteractions(coffeeMachine);
	}

	@Test
	public void shouldMakeOneAmericanoAndHaveInsufficientWaterForLatte() throws Exception {
		this.mockMvc.perform(get("/coffee")
				.param("coffeeKind", "Americano"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.coffeeEssence.amount").value(250))
				.andExpect(jsonPath("$.coffeeEssence.coffeeExtract").value(40))
				.andExpect(jsonPath("$.milk").doesNotExist());

		Exception exception = this.mockMvc.perform(get("/coffee")
				.param("coffeeKind", "Latte"))
				.andExpect(status().isPreconditionFailed())
				.andReturn().getResolvedException();


		assertThat(exception).isNotNull()
				.hasMessageContaining("Insufficient water amount. Only 0ml left. Refill " +
						"the water tank");

		verify(coffeeMachine, times(1)).makeCoffee(AMERICANO);
		verify(coffeeMachine, times(1)).makeCoffee(LATTE);
		verifyNoMoreInteractions(coffeeMachine);
	}


	@Test
	public void shouldNotAcceptCoffeeRequestWithUnknownCoffee() throws Exception {
		this.mockMvc.perform(get("/coffee")
				.param("coffeeKind", "Unknown"))
				.andExpect(status().isBadRequest());

		verifyNoInteractions(coffeeMachine);
	}

	private Coffee expectedCappuccino() {
		return Coffee.of(CoffeeEssence.of(CAPPUCCINO.getWaterNeeded(), CAPPUCCINO.getCoffeeNeeded()),
				Milk.of(CAPPUCCINO.getMilkNeeded(), Milk.PERFECT_TEMPERATURE));
	}

	private Coffee expectedEspresso() {
		return Coffee.of(CoffeeEssence.of(ESPRESSO.getWaterNeeded(), ESPRESSO.getCoffeeNeeded()), null);
	}

}
