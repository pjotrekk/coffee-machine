package coffee.machine.ingredients;

public class CoffeeWastes extends Solid {
    public CoffeeWastes() {}

    public CoffeeWastes(int amount) {
        super(amount);
    }

    public static CoffeeWastes create() {
        return new CoffeeWastes();
    }
    public static CoffeeWastes of(int amount) {
        return new CoffeeWastes(amount);
    }

    @Override
    public CoffeeWastes newInstance() {
        return CoffeeWastes.create();
    }
}
