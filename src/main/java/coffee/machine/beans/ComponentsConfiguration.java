package coffee.machine.beans;

import coffee.machine.components.containers.*;
import coffee.machine.components.foamers.Foamer;
import coffee.machine.components.foamers.MilkFoamer;
import coffee.machine.components.grounders.CoffeeGrounder;
import coffee.machine.components.grounders.Grounder;
import coffee.machine.components.heaters.Heater;
import coffee.machine.components.heaters.MilkHeater;
import coffee.machine.components.heaters.WaterHeater;
import coffee.machine.components.pots.CoffeePot;
import coffee.machine.components.pots.Pot;
import coffee.machine.components.pumps.MilkToCupPump;
import coffee.machine.components.pumps.MilkToHeaterPump;
import coffee.machine.components.pumps.WaterPump;
import coffee.machine.components.pumps.Pump;
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
        return WaterPump.create();
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
        return MilkToHeaterPump.create();
    }

    @Bean
    public Pump milkToCupPump() {
        return MilkToCupPump.create();
    }

    @Bean
    public Foamer milkFoamer() {
        return MilkFoamer.create();
    }

}
