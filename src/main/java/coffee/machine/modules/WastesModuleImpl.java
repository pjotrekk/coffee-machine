package coffee.machine.modules;

import coffee.machine.tanks.Tank;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor(staticName = "of")
@Log4j2
public class WastesModuleImpl implements WastesModule {

    private Tank wastesTank;

    @Override
    public void checkOverflow() {
        log.debug("Check wastes tank overflow");
        if (wastesTank.maxCapacity() <= wastesTank.amount()) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,
                    "Wastes tank overflow!");
        }
    }
}
