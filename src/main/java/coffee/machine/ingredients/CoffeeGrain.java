package coffee.machine.ingredients;

import lombok.Getter;

@Getter
public final class CoffeeGrain extends Ingredient<CoffeeGrain> {

    private boolean grounded = false;

    @Override
    protected CoffeeGrain self() {
        return this;
    }

    @Override
    public CoffeeGrain newInstance(int amount) {
        return CoffeeGrain.of(amount, grounded);
    }

    public static CoffeeGrain create() {
        return new CoffeeGrain();
    }

    public static CoffeeGrain of(int amount) {
        return new CoffeeGrain(amount);
    }

    public static CoffeeGrain of(int amount, boolean grounded) {
        return new CoffeeGrain(amount, grounded);
    }

    private CoffeeGrain() {}

    private CoffeeGrain(int amount) {
        super(amount);
    }

    private CoffeeGrain(int amount, boolean grounded) {
        super(amount);
        this.grounded = grounded;
    }

}
