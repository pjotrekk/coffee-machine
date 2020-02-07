package coffee.machine.modules;

import coffee.machine.components.Foamer;
import coffee.machine.components.Heater;
import coffee.machine.components.LiquidTank;
import coffee.machine.components.Pump;
import coffee.machine.ingredients.Milk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MilkModuleTest {

    private LiquidTank milkTank;
    private LiquidTank milkHeaterTank;
    private HeatingModule milkHeatingModule;
    private Foamer foamer;
    private Pump milkToHeaterPump;

    private MilkModule milkModule;

    @BeforeEach
    void setUp() {
        milkTank = LiquidTank.of(Milk.of(200, 20), 400);
        milkHeaterTank = LiquidTank.of(Milk.create(), 200);
        milkToHeaterPump = Pump.of(milkTank, milkHeaterTank);
        milkHeatingModule = HeatingModule.of(milkHeaterTank, Heater.create(Milk.PERFECT_TEMPERATURE));
        foamer = Foamer.create();
        milkModule = MilkModule.of(milkTank, milkHeatingModule, milkToHeaterPump, foamer);
    }

    @Test
    void shouldPrepareNonFoamedMilk() {
        int milkAmount = 200;
        Milk heatedMilk = milkModule.prepareMilk(milkAmount, false);

        assertThat(heatedMilk.isFoamed()).isFalse();
        assertThat(heatedMilk.getAmount()).isEqualTo(milkAmount);
        assertThat(heatedMilk.getTemperature()).isEqualTo(Milk.PERFECT_TEMPERATURE);
    }

    @Test
    void shouldPrepareFoamedMilk() {
        int milkAmount = 200;
        Milk heatedMilk = milkModule.prepareMilk(milkAmount, true);

        assertThat(heatedMilk.isFoamed()).isTrue();
        assertThat(heatedMilk.getAmount()).isEqualTo(milkAmount);
        assertThat(heatedMilk.getTemperature()).isEqualTo(Milk.PERFECT_TEMPERATURE);
    }

    @Test
    void shouldFailAmountCheck() {
        int milkAmount = 1000;
        ResponseStatusException rse = assertThrows(ResponseStatusException.class, () ->
                milkModule.checkMilkTank(milkAmount));

        assertThat(rse).isNotNull().hasMessageContaining("Insufficient milk amount. Only 200ml left." +
                " Refill the milk tank");
        assertThat(rse.getStatus()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    void shouldFailOverflowCheck() {
        milkTank.setCurrentAmount(2000);
        ResponseStatusException rse = assertThrows(ResponseStatusException.class, () ->
                milkModule.checkMilkTank(200));

        assertThat(rse).isNotNull().hasMessageContaining("Milk tank overflow! You should reduce the amount " +
                "of milk to the maximum of 400ml");
        assertThat(rse.getStatus()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

}