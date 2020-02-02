package coffee.machine.configurations;

import coffee.machine.components.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentsConfiguration {

    @Bean
    public Tank waterTank() {
        return WaterTank.create();
    }

    @Bean
    public Container waterHeaterContainer() {
        return HeaterContainer.create();
    }

    @Bean
    public Tank coffeeTank() {
        return CoffeeTank.create();
    }

    @Bean
    public Tank wastesTank() {
        return WastesTank.create();
    }

    @Bean
    public Pot coffeePot() {
        return CoffeePot.create();
    }

    @Bean
    public Pump waterPump() {
        return LiquidPump.create();
    }

    @Bean
    public Heater waterHeater() {
        return WaterHeater.create();
    }

    @Bean
    public Grounder coffeeGrounder() {
        return CoffeeGrounder.create();
    }

    @Bean
    public Heater milkHeater() {
        return MilkHeater.create();
    }

    @Bean
    public Container milkHeaterContainer() {
        return HeaterContainer.create();
    }

    @Bean
    public Pump milkToHeaterPump() {
        return LiquidPump.create();
    }

    @Bean
    public Pump milkToCupPump() {
        return LiquidPump.create();
    }

}
