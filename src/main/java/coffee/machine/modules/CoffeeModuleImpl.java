package coffee.machine.modules;

import coffee.machine.grounders.Grounder;
import coffee.machine.pots.CoffeePot;
import coffee.machine.tanks.Tank;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor(staticName = "of")
@Log4j2
public class CoffeeModuleImpl implements CoffeeModule {
    private Tank coffeeTank;
    private CoffeePot coffeePot;
    private Grounder grounder;

    @Override
    public void checkCapacity(int amountNeeded) {
        log.debug("Check coffee capacity");
        if (coffeeTank.amount() < amountNeeded) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                    String.format("Insufficient coffee amount. Only %dmg left", coffeeTank.amount()));
        }
    }

    @Override
    public void ground(int amount) {
        log.debug("Ground {}mg of coffee", amount);
        grounder.ground(amount);
        log.debug("Coffee grounded.");
    }

    @Override
    public void flipUsedCoffee() {
        log.debug("Flipping used coffee");
        coffeePot.flip();
        log.debug("Coffee pot emptied.");
    }

}
