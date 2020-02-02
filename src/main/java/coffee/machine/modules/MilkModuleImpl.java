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
    public void prepareMilk(int amount) {
        moveMilkToHeater(amount);
        heatMilk(amount);
        moveMilkToCup(amount);
    }

    private void moveMilkToHeater(int amount) {
        milkToHeaterPump.pump(amount);
    }

    private void moveMilkToCup(int amount) {
        milkToCupPump.pump(amount);
    }

    private void heatMilk(int amount) {
        milkHeatingModule.heat(amount);
    }
}
