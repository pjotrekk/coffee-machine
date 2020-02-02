package coffee.machine.configurations;

import coffee.machine.CoffeeMachine;
import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.HeatingModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoffeeMachinesConfiguration {

    @Bean
    CoffeeMachine coffeeMachine(
            WaterModule waterModule,
            CoffeeModule coffeeModule,
            HeatingModule heatingModule,
            WastesModule wastesModule
    ) {
        return CoffeeMachine.builder()
                .waterModule(waterModule)
                .coffeeModule(coffeeModule)
                .heatingModule(heatingModule)
                .wastesModule(wastesModule)
                .build();
    }
}
