package coffee.machine.ingredients;

public abstract class Solid extends Ingredient {
    public Solid() {}

    public Solid(int amount) {
        super(amount);
    }

    @Override
    public abstract Solid newInstance();
}
