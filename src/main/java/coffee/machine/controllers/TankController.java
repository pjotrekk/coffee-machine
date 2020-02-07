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
    private final Tank milkTank;

    @PutMapping("/water")
    public void refillWater(@RequestParam(name = "amount") int amount) {
        validateAmount(amount);
        waterTank.setCurrentAmount(amount);
        if (waterTank.isOverflown()) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                    String.format("Water tank overflow! You should reduce the amount " +
                            "of water to the maximum of %dml", waterTank.getCapacity()));
        }
    }

    @PutMapping("/coffee")
    public void refillCoffee(@RequestParam(name = "amount") int amount) {
        validateAmount(amount);
        coffeeTank.setCurrentAmount(amount);
        if (coffeeTank.isOverflown()) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                    String.format("Coffee tank overflow! You should reduce the amount " +
                            "of coffee to the maximum of %dmg", coffeeTank.getCapacity()));
        }
    }

    @PutMapping("/milk")
    public void refillMilk(@RequestParam(name = "amount") int amount) {
        validateAmount(amount);
        milkTank.setCurrentAmount(amount);
        if (coffeeTank.isOverflown()) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                    String.format("Milk tank overflow! You should reduce the amount " +
                            "of milk to the maximum of %dmg", milkTank.getCapacity()));
        }
    }

    @PutMapping("/wastes")
    public void clearWastes() {
        wastesTank.setCurrentAmount(0);
    }

    private void validateAmount(int amount) {
        if (amount < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The amount cannot be below zero");
        }
    }

}
