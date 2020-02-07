package coffee.machine.modules;

import coffee.machine.components.Foamer;
import coffee.machine.components.Pump;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(staticName = "of")
public class MilkModule {
    private final HeatingModule milkHeatingModule;
    private final Pump milkToHeaterPump;
    private final Pump milkHeaterToCupPump;
    private final Foamer foamer;

    public void checkMilkContainer(int amountNeeded) {
    }

    public void prepareMilk(int amount, boolean withFoam) {
        milkToHeaterPump.pump(amount);
        milkHeatingModule.heatContent();
        if (withFoam) {
            foamer.foam(amount);
        }
        milkHeaterToCupPump.pump(amount);
    }

}
