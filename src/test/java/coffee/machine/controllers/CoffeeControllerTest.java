package coffee.machine.controllers;

import coffee.machine.CoffeeMachine;
import coffee.machine.coffee.Coffee;
import coffee.machine.coffee.CoffeeKind;
import coffee.machine.coffee.ImmutableCoffee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoffeeController.class)
public class CoffeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoffeeMachine coffeeMachine;

    private Coffee testCoffee = ImmutableCoffee.builder()
            .water(10)
            .coffeeExtract(10)
            .milk(10)
            .withFoam(true)
            .build();

    @BeforeEach
    void setUp() {
        given(coffeeMachine.makeCoffee(any())).willReturn(testCoffee);
    }

    @Test
    public void shouldAcceptCorrectCoffee() throws Exception {
        this.mockMvc.perform(get("/coffee")
                .param("coffeeKind", "ESPRESSO"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.water").value(10))
                .andExpect(jsonPath("$.coffeeExtract").value(10))
                .andExpect(jsonPath("$.withFoam").value(true))
                .andExpect(jsonPath("$.milk").value(10));

        verify(coffeeMachine, times(1)).makeCoffee(CoffeeKind.ESPRESSO);
        verifyNoMoreInteractions(coffeeMachine);
    }

    @Test
    public void shouldAcceptCoffeeWithoutUppercase() throws Exception {
        this.mockMvc.perform(get("/coffee")
                .param("coffeeKind", "ameRIcano"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.water").value(10))
                .andExpect(jsonPath("$.coffeeExtract").value(10))
                .andExpect(jsonPath("$.withFoam").value(true))
                .andExpect(jsonPath("$.milk").value(10));

        verify(coffeeMachine, times(1)).makeCoffee(CoffeeKind.AMERICANO);
        verifyNoMoreInteractions(coffeeMachine);
    }

    @Test
    public void shouldNotAcceptNotRegisteredCoffeeKind() throws Exception {
        this.mockMvc.perform(get("/coffee")
                .param("coffeeKind", "noSuchCoffee"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());

        verifyNoInteractions(coffeeMachine);
    }

}