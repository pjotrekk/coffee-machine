package coffee.machine.modules;

import coffee.machine.components.Foamer;
import coffee.machine.components.Heater;
import coffee.machine.components.Tank;
import coffee.machine.ingredients.Milk;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor(staticName = "of")
public class MilkModule {
    private final Tank<Milk> milkTank;
    private final Heater milkHeater;
    private final Foamer foamer;

    public void checkMilkTank(int amountNeeded) {
        checkForOverflow();
        checkMilkCapacity(amountNeeded);
    }

    public Milk prepareMilk(int amount, boolean withFoam) {
        Milk milk = milkTank.acquire(amount);
        Milk hotMilk = milkHeater.heat(milk);
        if (withFoam) {
            hotMilk = foamer.foam(hotMilk);
        }
        return hotMilk;
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
