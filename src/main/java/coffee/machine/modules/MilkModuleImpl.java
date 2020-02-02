package coffee.machine.modules;

import coffee.machine.components.foamers.Foamer;
import coffee.machine.components.pumps.Pump;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class MilkModuleImpl implements MilkModule {
    private HeatingModule milkHeatingModule;
    private Pump milkToHeaterPump;
    private Pump milkToCupPump;
    private Foamer foamer;

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
