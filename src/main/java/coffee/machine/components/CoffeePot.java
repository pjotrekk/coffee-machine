package coffee.machine.components;

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
