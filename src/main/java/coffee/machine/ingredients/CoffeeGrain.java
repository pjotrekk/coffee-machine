package coffee.machine.ingredients;

import lombok.Getter;

@Getter
public class CoffeeGrain extends Solid {

    public CoffeeGrain() {}

    private CoffeeGrain(int amount) {
        super(amount);
    }

    public static CoffeeGrain create() {
        return new CoffeeGrain();
    }
    public static CoffeeGrain of(int amount) {
        return new CoffeeGrain(amount);
    }

    @Override
    public CoffeeGrain newInstance() {
        return CoffeeGrain.create();
    }
}
