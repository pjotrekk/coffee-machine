package coffee.machine.components;

import coffee.machine.ingredients.CoffeeEssence;
import coffee.machine.ingredients.CoffeeGrain;
import coffee.machine.ingredients.Water;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(staticName = "of")
public class CoffeePot {

    private final Tank<CoffeeGrain> wastesTank;

    public CoffeeEssence combineSteamAndGroundedCoffee(Water steam, CoffeeGrain groundedCoffee) {
        checkWaterEvaporated(steam);
        checkCoffeeGrounded(groundedCoffee);
        checkCoffeeNotUsed(groundedCoffee);

        CoffeeEssence coffeeEssence = CoffeeEssence.of(steam.getAmount(), groundedCoffee.getAmount());
        wastesTank.addAmount(groundedCoffee.getAmount());
        return coffeeEssence;
    }

    private void checkCoffeeGrounded(CoffeeGrain groundedCoffee) {
        if (!groundedCoffee.isGrounded()) {
            throw new AssertionError("Coffee in coffee pot is not grounded");
        }
    }

    private void checkCoffeeNotUsed(CoffeeGrain groundedCoffee) {
        if (groundedCoffee.isUsed()) {
            throw new AssertionError("Coffee in coffee pot has already been used");
        }
    }

    private void checkWaterEvaporated(Water steam) {
        if (!steam.isEvaporated()) {
            throw new AssertionError("Water in coffee pot is not evaporated");
        }
    }

}
