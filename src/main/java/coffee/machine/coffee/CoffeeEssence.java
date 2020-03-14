package coffee.machine.coffee;

import org.immutables.value.Value;

@Value.Style(allParameters = true)
@Value.Immutable
public interface CoffeeEssence {
    int getAmount();
    int getCoffeeExtract();
}
