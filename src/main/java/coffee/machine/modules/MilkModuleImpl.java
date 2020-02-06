package coffee.machine.modules;

import coffee.machine.components.foamers.Foamer;
import coffee.machine.components.pumps.Pump;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(staticName = "of")
public class MilkModuleImpl implements MilkModule {
    private final HeatingModule milkHeatingModule;
    private final Pump milkToHeaterPump;
    private final Pump milkToCupPump;
    private final Foamer foamer;

    @Override
    public void checkMilkContainer(int amountNeeded) {
        milkHeatingModule.checkCapacity(amountNeeded);
    }

    @Override
    public void prepareMilk(int amount, boolean withFoam) {
        milkToHeaterPump.pump(amount);
        milkHeatingModule.heat(amount);
        if (withFoam) {
            foamer.foam(amount);
        }
        milkToCupPump.pump(amount);
    }

}
