package coffee.machine.components;

import coffee.machine.ingredients.CoffeeEssence;
import coffee.machine.ingredients.CoffeeWastes;
import coffee.machine.ingredients.GroundedCoffee;
import coffee.machine.ingredients.Water;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(staticName = "of")
public class CoffeePot {

    private final GroundedCoffee groundedCoffee = GroundedCoffee.of(0);

    private final CoffeeWastes coffeeWastes = CoffeeWastes.of(0);

    private final SolidTank wastesTank;

    public CoffeeEssence combineSteamAndGroundedCoffee(Water steam) {
        int coffeeAmount = groundedCoffee.getAmount();
        coffeeWastes.addAmount(coffeeAmount);
        groundedCoffee.setAmount(0);
        flip();
        return CoffeeEssence.of(steam.getAmount(), coffeeAmount);
    }

    public void addGroundedCoffee(int amount) {
        groundedCoffee.addAmount(amount);
    }

    public int getGroundedCoffeeAmount() {
        return groundedCoffee.getAmount();
    }

    public int getCoffeeWastesAmount() {
        return coffeeWastes.getAmount();
    }

    private void flip() {
        wastesTank.addAmount(coffeeWastes.getAmount());
        coffeeWastes.setAmount(0);
    }
}
