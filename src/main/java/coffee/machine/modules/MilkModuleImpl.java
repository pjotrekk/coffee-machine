package coffee.machine.modules;

import coffee.machine.components.foamers.Foamer;
import coffee.machine.components.pumps.Pump;
import lombok.AllArgsConstructor;

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
    public void prepareMilk(int amount) {
        milkToHeaterPump.pump(amount);
        milkHeatingModule.heat(amount);
        milkToCupPump.pump(amount);
    }

    @Override
    public void prepareFoamedMilk(int amount) {
        milkToHeaterPump.pump(amount);
        milkHeatingModule.heat(amount);
        foamer.foam(amount);
        milkToCupPump.pump(amount);
    }

}
