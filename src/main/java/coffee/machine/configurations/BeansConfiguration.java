package coffee.machine.configurations;

import coffee.machine.components.*;
import coffee.machine.ingredients.CoffeeGrain;
import coffee.machine.ingredients.CoffeeWastes;
import coffee.machine.ingredients.Milk;
import coffee.machine.ingredients.Water;
import coffee.machine.modules.HeatingModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    LiquidTank waterTank() {
        return LiquidTank.of(Water.create(), 1500);
    }

    @Bean
    LiquidTank milkTank() {
        return LiquidTank.of(Milk.create(), 1000);
    }

    @Bean
    SolidTank coffeeTank() {
        return SolidTank.of(CoffeeGrain.create(), 1200);
    }

    @Bean
    SolidTank wastesTank() {
        return SolidTank.of(CoffeeWastes.create(), 1500);
    }

    @Bean
    LiquidTank waterHeaterTank() {
        return LiquidTank.of(Water.create(), 500);
    }

    @Bean
    LiquidTank milkHeaterTank() {
        return LiquidTank.of(Milk.create(), 500);
    }

    @Bean
    HeatingModule waterHeatingModule(LiquidTank waterHeaterTank, Heater waterHeater) {
        return HeatingModule.of(waterHeaterTank, waterHeater);
    }

    @Bean
    HeatingModule milkHeatingModule(LiquidTank milkHeaterTank, Heater milkHeater) {
        return HeatingModule.of(milkHeaterTank, milkHeater);
    }

    @Bean
    Pump waterToHeaterPump(LiquidTank waterTank, LiquidTank waterHeaterTank) {
        return Pump.of(waterTank, waterHeaterTank);
    }

    @Bean
    Pump milkToHeaterPump(LiquidTank milkTank, LiquidTank milkHeaterTank) {
        return Pump.of(milkTank, milkHeaterTank);
    }

    @Bean
    Pump milkHeaterToCupPump(LiquidTank milkTank, LiquidTank milkHeaterTank) {
        return Pump.of(milkTank, milkHeaterTank);
    }

    @Bean
    Heater milkHeater() {
        return Heater.create(Milk.PERFECT_TEMPERATURE);
    }

    @Bean
    Heater waterHeater() {
        return Heater.create(Water.BOIL_TEMPERATURE);
    }
}
