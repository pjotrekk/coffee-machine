package coffee.machine.ingredients;

public class Water extends Liquid {

    public static final int BOIL_TEMPERATURE = 100;

    public Water() {}

    public Water(int amount) {
        super(amount);
    }

    private Water(int amount, int temperature) {
        super(amount, temperature);
    }

    public static Water create() {
        return new Water();
    }

    public static Water of(int amount) {
        return new Water(amount);
    }

    public static Water of(int amount, int temperature) {
        return new Water(amount, temperature);
    }

    @Override
    public Water newInstance() {
        return Water.create();
    }
}
