package coffee.machine.configurations;

import coffee.machine.components.*;
import coffee.machine.modules.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModulesConfiguration {

    @Bean
    CoffeeModule coffeeModule(Tank coffeeTank, Pot coffeePot, Grounder coffeeGrounder) {
        return CoffeeModuleImpl.of(coffeeTank, coffeePot, coffeeGrounder);
    }

    @Bean
    WastesModule wastesModule(Tank wastesTank) {
        return WastesModuleImpl.of(wastesTank);
    }

    @Bean
    HeatingModule waterHeatingModule(Container waterHeaterContainer, Heater waterHeater) {
        return HeatingModuleImpl.of(waterHeaterContainer, waterHeater);
    }

    @Bean
    WaterModule waterModule(Tank waterTank, Pump waterPump, HeatingModule waterHeatingModule) {
        return WaterModuleImpl.of(waterTank, waterPump, waterHeatingModule);
    }

}
