package coffee.machine.ingredients;

import lombok.Getter;
import lombok.Setter;

public abstract class Liquid extends Ingredient {

    public static final int ROOM_TEMPERATURE = 23;

    @Getter @Setter
    private int temperature = ROOM_TEMPERATURE;

    public Liquid() {}

    public Liquid(int amount) {
        super(amount);
    }

    public Liquid(int amount, int temperature) {
        super(amount);
        this.temperature = temperature;
    }

    @Override
    public abstract Liquid newInstance();
}
