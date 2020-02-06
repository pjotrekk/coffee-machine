package coffee.machine.configurations;

import coffee.machine.components.containers.Tank;
import coffee.machine.components.heaters.Heater;
import coffee.machine.modules.HeatingModule;
import coffee.machine.modules.HeatingModuleImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    Tank waterTank() {
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
        return HeatingModuleImpl.of(waterHeaterTank, waterHeater);
    }

    @Bean
    HeatingModule milkHeatingModule(Tank milkHeaterTank, Heater milkHeater) {
        return HeatingModuleImpl.of(milkHeaterTank, milkHeater);
    }

}
