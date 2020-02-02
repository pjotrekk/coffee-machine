package coffee.machine.beans;

import coffee.machine.CoffeeMachine;
import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.MilkModule;
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
            WastesModule wastesModule,
            MilkModule milkModule
    ) {
        return CoffeeMachine.of(waterModule, coffeeModule, wastesModule, milkModule);
    }
}
