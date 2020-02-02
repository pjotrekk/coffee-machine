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
    WaterModule waterModule(Tank waterTank, Pump waterPump) {
        return WaterModuleImpl.of(waterTank, waterPump);
    }

    @Bean
    WastesModule wastesModule(Tank wastesTank) {
        return WastesModuleImpl.of(wastesTank);
    }

    @Bean
    HeatingModule heatingModule(Tank heaterTank, Heater waterHeater) {
        return HeatingModuleImpl.of(heaterTank, waterHeater);
    }

}
