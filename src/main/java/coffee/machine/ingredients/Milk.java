package coffee.machine.ingredients;

import lombok.Getter;
import lombok.Setter;

public class Milk extends Liquid {

    public static final int PERFECT_TEMPERATURE = 65;

    @Getter @Setter
    boolean foamed;

    private Milk() {}

    private Milk(int amount) {
        super(amount, ROOM_TEMPERATURE);
    }

    private Milk(int amount, int temperature) {
        super(amount, temperature);
    }

    public static Milk create() {
        return new Milk();
    }

    public static Milk of(int amount) {
        return new Milk(amount);
    }

    public static Milk of(int amount, int temperature) {
        return new Milk(amount, temperature);
    }

    @Override
    public Milk newInstance() {
        return Milk.create();
    }

}
