package coffee.machine.components.pots;

import coffee.machine.components.containers.Tank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(staticName = "of")
public class CoffeePot {

    @Getter @Setter
    private int currentAmount = 0;

    private final Tank wastesTank;

    public void flip() {
        wastesTank.setCurrentAmount(wastesTank.getCurrentAmount() + currentAmount);
    }

}
