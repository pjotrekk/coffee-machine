package coffee.machine.modules;

import coffee.machine.components.Pump;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class MilkModuleImpl implements MilkModule {
    private HeatingModule milkHeatingModule;
    private Pump milkToHeaterPump;
    private Pump milkToCupPump;

    @Override
    public void checkMilkContainer(int amountNeeded) {
        milkHeatingModule.checkCapacity(amountNeeded);
    }

    @Override
    public void moveMilkToHeater(int amount) {
        milkToHeaterPump.pump(amount);
    }

    @Override
    public void moveMilkToCup(int amount) {
        milkToCupPump.pump(amount);
    }

    @Override
    public void heatMilk(int amount) {
        milkHeatingModule.heat(amount);
    }
}
