package coffee.machine.ingredients;

import lombok.Getter;

@Getter
public final class Water extends Ingredient<Water> {

    public final static int BOIL_TEMPERATURE = 100;

    private int temperature = ROOM_TEMPERATURE;

    private boolean evaporated = false;

    @Override
    protected Water self() {
        return this;
    }

    @Override
    public Water newInstance(int amount) {
        return Water.of(amount, temperature, evaporated);
    }

    public Water() {}

    private Water(int amount) {
        super(amount);
    }

    private Water(int amount, int temperature, boolean evaporated) {
        super(amount);
        this.temperature = temperature;
        this.evaporated = evaporated;
    }

    public static Water create() {
        return new Water();
    }

    public static Water of(int amount) {
        return new Water(amount);
    }

    public static Water of(int amount, int temperature, boolean evaporated) {
        return new Water(amount, temperature, evaporated);
    }

}
