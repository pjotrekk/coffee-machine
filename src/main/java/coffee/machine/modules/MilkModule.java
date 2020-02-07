package coffee.machine.modules;

import coffee.machine.components.Foamer;
import coffee.machine.components.LiquidTank;
import coffee.machine.components.Pump;
import coffee.machine.ingredients.Milk;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor(staticName = "of")
public class MilkModule {
    private final LiquidTank milkTank;
    private final HeatingModule milkHeatingModule;
    private final Pump milkToHeaterPump;
    private final Foamer foamer;

    public void checkMilkTank(int amountNeeded) {
        checkForOverflow();
        checkMilkCapacity(amountNeeded);
    }

    public Milk prepareMilk(int amount, boolean withFoam) {
        moveMilkToHeater(amount);
        Milk hotMilk = (Milk) milkHeatingModule.heatContent();
        if (withFoam) {
            foamer.foam(hotMilk);
        }
        return hotMilk;
    }

    private void moveMilkToHeater(int amount) {
        milkToHeaterPump.pump(amount);
    }

    private void checkForOverflow() {
        if (milkTank.isOverflown()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    String.format("Milk tank overflow! You should reduce the amount " +
                            "of milk to the maximum of %dml", milkTank.getCapacity()));
        }
    }

    private void checkMilkCapacity(int amountNeeded) {
        if (milkTank.getCurrentAmount() < amountNeeded) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    String.format("Insufficient milk amount. Only %dml left. Refill " +
                            "the milk tank", milkTank.getCurrentAmount()));
        }
    }

}
