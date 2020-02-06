package coffee.machine.controllers;

import coffee.machine.components.Tank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tanks")
@AllArgsConstructor
public class TankController {
    private final Tank waterTank;
    private final Tank coffeeTank;
    private final Tank wastesTank;

    @PutMapping("/water")
    public void refillWater(@RequestParam(name = "amount") int amount) {
        waterTank.setCurrentAmount(amount);
        if (waterTank.isOverflown()) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                    String.format("Water tank overflow! You should reduce the amount " +
                            "of water to the maximum of %dml", waterTank.getCapacity()));
        }
    }

    @PutMapping("/coffee")
    public void refillCoffee(@RequestParam(name = "amount") int amount) {
        coffeeTank.setCurrentAmount(amount);
        if (coffeeTank.isOverflown()) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                    String.format("Coffee tank overflow! You should reduce the amount " +
                            "of coffee to the maximum of %dmg", coffeeTank.getCapacity()));
        }
    }

    @PutMapping("/wastes")
    public void clearWastes() {
        wastesTank.setCurrentAmount(0);
    }

}
