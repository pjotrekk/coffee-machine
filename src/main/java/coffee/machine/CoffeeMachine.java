package coffee.machine;

import coffee.machine.coffee.Coffee;
import coffee.machine.coffee.CoffeeEssence;
import coffee.machine.coffee.CoffeeKind;
import coffee.machine.coffee.ImmutableCoffee;
import coffee.machine.ingredients.CoffeeGrain;
import coffee.machine.ingredients.Milk;
import coffee.machine.ingredients.Water;
import coffee.machine.modules.CoffeeModule;
import coffee.machine.modules.MilkModule;
import coffee.machine.modules.WastesModule;
import coffee.machine.modules.WaterModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.*;

@Component
@RequiredArgsConstructor(staticName = "of")
public class CoffeeMachine {

    private final WaterModule waterModule;

    private final CoffeeModule coffeeModule;

    private final WastesModule wastesModule;

    private final MilkModule milkModule;

    private final ExecutorService executorService;

    public Coffee makeCoffee(CoffeeKind coffeeKind) {
        checkContainers(coffeeKind);

        Future<Water> steam = getWaterFuture(coffeeKind);
        Future<CoffeeGrain> groundedCoffee = getCoffeeGrainFuture(coffeeKind);
        Future<Milk> hotMilk = getMilkFuture(coffeeKind);

        ImmutableCoffee.Builder coffeeBuilder = ImmutableCoffee.builder();
        try {
            CoffeeEssence coffeeEssence = coffeeModule.pushSteamThroughGroundedCoffee(steam.get(), groundedCoffee.get());

            coffeeBuilder.coffeeExtract(coffeeEssence.getCoffeeExtract())
                    .water(coffeeEssence.getAmount())
                    .milk(hotMilk.get().getAmount())
                    .withFoam(hotMilk.get().isFoamed());
        } catch (InterruptedException | ExecutionException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Machine timeouted. Try again.");
        }

        return coffeeBuilder.build();
    }

    private Future<Milk> getMilkFuture(CoffeeKind coffeeKind) {
        if (coffeeKind.getMilkNeeded() > 0) {
            return executorService.submit(() -> milkModule.prepareMilk(coffeeKind.getMilkNeeded(), coffeeKind.isWithFoam()));
        }
        return CompletableFuture.completedFuture(Milk.of(0, 0, false));
    }

    private Future<CoffeeGrain> getCoffeeGrainFuture(CoffeeKind coffeeKind) {
        return executorService.submit(() -> coffeeModule.ground(coffeeKind.getCoffeeNeeded()));
    }

    private Future<Water> getWaterFuture(CoffeeKind coffeeKind) {
        return executorService.submit(() -> waterModule.prepareSteam(coffeeKind.getWaterNeeded()));
    }

    private void checkContainers(CoffeeKind coffeeKind) {
        wastesModule.checkOverflow();
        waterModule.checkWaterTank(coffeeKind.getWaterNeeded());
        coffeeModule.checkCapacity(coffeeKind.getCoffeeNeeded());
        milkModule.checkMilkTank(coffeeKind.getMilkNeeded());
    }

}
