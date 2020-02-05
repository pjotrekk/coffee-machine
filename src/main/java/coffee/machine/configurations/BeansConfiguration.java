package coffee.machine.configurations;

import coffee.machine.components.containers.Container;
import coffee.machine.components.containers.HeaterContainer;
import coffee.machine.components.heaters.Heater;
import coffee.machine.modules.HeatingModule;
import coffee.machine.modules.HeatingModuleImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    HeatingModule waterHeatingModule(Container waterHeaterContainer, Heater waterHeater) {
        return HeatingModuleImpl.of(waterHeaterContainer, waterHeater);
    }

    @Bean
    HeatingModule milkHeatingModule(Container milkHeaterContainer, Heater milkHeater) {
        return HeatingModuleImpl.of(milkHeaterContainer, milkHeater);
    }

    @Bean
    public Container milkHeaterContainer() {
        return HeaterContainer.create();
    }

    @Bean
    public Container waterHeaterContainer() {
        return HeaterContainer.create();
    }
}
