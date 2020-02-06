package coffee.machine.configurations;

import coffee.machine.components.Tank;
import coffee.machine.components.Heater;
import coffee.machine.components.Pump;
import coffee.machine.modules.HeatingModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    Tank waterTank() {
        return Tank.of(1500);
    }

    @Bean
    Tank milkTank() {
        return Tank.of(1500);
    }

    @Bean
    Tank coffeeTank() {
        return Tank.of(1000);
    }

    @Bean
    Tank wastesTank() {
        return Tank.of(1500);
    }

    @Bean
    Tank waterHeaterTank() {
        return Tank.of(500);
    }

    @Bean
    Tank milkHeaterTank() {
        return Tank.of(400);
    }

    @Bean
    HeatingModule waterHeatingModule(Tank waterHeaterTank, Heater waterHeater) {
        return HeatingModule.of(waterHeaterTank, waterHeater);
    }

    @Bean
    HeatingModule milkHeatingModule(Tank milkHeaterTank, Heater milkHeater) {
        return HeatingModule.of(milkHeaterTank, milkHeater);
    }

    @Bean
    Pump waterToHeaterPump(Tank waterTank, Tank waterHeaterTank) {
        return Pump.of(waterTank, waterHeaterTank);
    }

    @Bean
    Pump milkToHeaterPump(Tank milkTank, Tank milkHeaterTank) {
        return Pump.of(milkTank, milkHeaterTank);
    }

    @Bean
    Pump milkHeaterToCupPump(Tank waterTank, Tank waterHeaterTank) {
        return Pump.of(waterTank, waterHeaterTank);
    }

    @Bean
    Heater milkHeater() {
        return Heater.create(65);
    }

    @Bean
    Heater waterHeater() {
        return Heater.create(100);
    }
}
