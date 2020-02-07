package coffee.machine.ingredients;

public class GroundedCoffee extends Solid {

    public GroundedCoffee() {
        super();
    }

    public GroundedCoffee(int amount) {
        super(amount);
    }

    public static GroundedCoffee of() {
        return new GroundedCoffee();
    }
    public static GroundedCoffee of(int amount) {
        return new GroundedCoffee(amount);
    }

    @Override
    public CoffeeGrain newInstance() {
        return CoffeeGrain.create();
    }

}
