package coffee.machine.coffee;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableCoffee.class)
public interface Coffee {
    int water();
    int coffeeExtract();
    int milk();
    boolean withFoam();
}
