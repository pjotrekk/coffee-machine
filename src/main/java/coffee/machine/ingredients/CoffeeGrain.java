package coffee.machine.ingredients;

import lombok.Getter;

@Getter
public class CoffeeGrain extends Ingredient<CoffeeGrain> {

    private boolean grounded = false;

    private boolean used = false;

    @Override
    protected CoffeeGrain self() {
        return this;
    }

    @Override
    public CoffeeGrain newInstance(int amount) {
        return CoffeeGrain.of(amount, grounded, used);
    }


    public static CoffeeGrain create() {
        return new CoffeeGrain();
    }

    public static CoffeeGrain of(int amount) {
        return new CoffeeGrain(amount);
    }

    public static CoffeeGrain of(int amount, boolean grounded, boolean used) {
        return new CoffeeGrain(amount, grounded, used);
    }

    private CoffeeGrain() {}

    private CoffeeGrain(int amount) {
        super(amount);
    }

    private CoffeeGrain(int amount, boolean grounded, boolean used) {
        super(amount);
        this.grounded = grounded;
        this.used = used;
    }

}
