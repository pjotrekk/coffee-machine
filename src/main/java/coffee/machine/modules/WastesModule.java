package coffee.machine.modules;

import coffee.machine.components.Tank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor(staticName = "of")
public class WastesModule {

    private final Tank wastesTank;

    public void checkOverflow() {
        if (wastesTank.isOverflown()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Wastes tank overflow! You should remove the used coffee.");
        }
    }

}
