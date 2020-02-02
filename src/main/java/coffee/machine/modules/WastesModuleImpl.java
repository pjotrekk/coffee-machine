package coffee.machine.modules;

import coffee.machine.components.containers.Tank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor(staticName = "of")
public class WastesModuleImpl implements WastesModule {

    private Tank wastesTank;

    @Override
    public void checkOverflow() {
        checkWastesTankOverflow();
    }

    private void checkWastesTankOverflow() {
        if (wastesTank.maxCapacity() <= wastesTank.amount()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Wastes tank overflow!");
        }
    }
}
