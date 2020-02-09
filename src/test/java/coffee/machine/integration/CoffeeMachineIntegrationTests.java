package coffee.machine.integration;

import coffee.machine.CoffeeMachine;
import coffee.machine.components.Tank;
import coffee.machine.ingredients.CoffeeGrain;
import coffee.machine.ingredients.Milk;
import coffee.machine.ingredients.Water;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static coffee.machine.coffee.CoffeeKind.*;
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
	private Tank<Water> waterTank;

	@Autowired
	private Tank<Milk> milkTank;

	@Autowired
	private Tank<CoffeeGrain> coffeeTank;

	@Autowired
	private Tank<CoffeeGrain> wastesTank;

	@BeforeEach
	void setUp() {
		waterTank.setCurrentAmount(250);
		milkTank.setCurrentAmount(200);
		coffeeTank.setCurrentAmount(150);
		wastesTank.setCurrentAmount(0);
	}

	@Test
	public void shouldReturnCappuccinoCoffee() throws Exception {
		this.mockMvc.perform(get("/coffee")
				.param("coffeeKind", "Cappuccino"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.water").value(150))
				.andExpect(jsonPath("$.coffeeExtract").value(20))
				.andExpect(jsonPath("$.withFoam").value(true))
				.andExpect(jsonPath("$.milk").value(150));

		assertThat(wastesTank.getCurrentAmount()).isEqualTo(20);
		assertThat(waterTank.getCurrentAmount()).isEqualTo(100);
		assertThat(milkTank.getCurrentAmount()).isEqualTo(50);
		assertThat(coffeeTank.getCurrentAmount()).isEqualTo(130);

		verify(coffeeMachine, times(1)).makeCoffee(CAPPUCCINO);
		verifyNoMoreInteractions(coffeeMachine);
	}

	@Test
	public void shouldReturnEspressoCoffee() throws Exception {
		this.mockMvc.perform(get("/coffee")
				.param("coffeeKind", "Espresso"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.water").value(50))
				.andExpect(jsonPath("$.coffeeExtract").value(40))
				.andExpect(jsonPath("$.withFoam").value(false))
				.andExpect(jsonPath("$.milk").value(0));

		assertThat(wastesTank.getCurrentAmount()).isEqualTo(40);
		assertThat(waterTank.getCurrentAmount()).isEqualTo(200);
		assertThat(milkTank.getCurrentAmount()).isEqualTo(200);
		assertThat(coffeeTank.getCurrentAmount()).isEqualTo(110);

		verify(coffeeMachine, times(1)).makeCoffee(ESPRESSO);
		verifyNoMoreInteractions(coffeeMachine);
	}

	@Test
	public void shouldMakeOneAmericanoAndHaveInsufficientWaterForLatte() throws Exception {
		this.mockMvc.perform(get("/coffee")
				.param("coffeeKind", "Americano"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.water").value(250))
				.andExpect(jsonPath("$.coffeeExtract").value(40))
				.andExpect(jsonPath("$.withFoam").value(false))
				.andExpect(jsonPath("$.milk").value(0));

		Exception exception = this.mockMvc.perform(get("/coffee")
				.param("coffeeKind", "Latte"))
				.andExpect(status().isPreconditionFailed())
				.andExpect(jsonPath("$").doesNotExist())
				.andReturn().getResolvedException();

		assertThat(exception).isNotNull()
				.hasMessageContaining("Insufficient water amount. Only 0ml left. Refill " +
						"the water tank");

		assertThat(wastesTank.getCurrentAmount()).isEqualTo(40);
		assertThat(waterTank.getCurrentAmount()).isEqualTo(0);
		assertThat(milkTank.getCurrentAmount()).isEqualTo(200);
		assertThat(coffeeTank.getCurrentAmount()).isEqualTo(110);

		verify(coffeeMachine, times(1)).makeCoffee(AMERICANO);
		verify(coffeeMachine, times(1)).makeCoffee(LATTE);
		verifyNoMoreInteractions(coffeeMachine);
	}

	@Test
	public void shouldNotAcceptCoffeeRequestWithUnknownCoffee() throws Exception {
		this.mockMvc.perform(get("/coffee")
				.param("coffeeKind", "Unknown"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$").doesNotExist());

		assertThat(wastesTank.getCurrentAmount()).isEqualTo(0);
		assertThat(waterTank.getCurrentAmount()).isEqualTo(250);
		assertThat(milkTank.getCurrentAmount()).isEqualTo(200);
		assertThat(coffeeTank.getCurrentAmount()).isEqualTo(150);

		verifyNoInteractions(coffeeMachine);
	}

}
