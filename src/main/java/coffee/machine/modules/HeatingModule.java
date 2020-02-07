package coffee.machine.modules;

import coffee.machine.components.Heater;
import coffee.machine.components.LiquidTank;
import coffee.machine.ingredients.Liquid;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class HeatingModule {
    private final LiquidTank heaterTank;
    private final Heater heater;

    public Liquid heatContent() {
        Liquid liquid = heaterTank.getIngredient().newInstance();
        heater.heat(liquid);
        liquid.setAmount(heaterTank.getCurrentAmount());
        heaterTank.setCurrentAmount(0);
        return liquid;
    }

}
