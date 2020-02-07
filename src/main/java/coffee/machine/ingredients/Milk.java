package coffee.machine.ingredients;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
public class Milk extends Liquid {

    public static final int PERFECT_TEMPERATURE = 65;

    @Getter @Setter
    private boolean foamed = false;

    private Milk() {}

    private Milk(int amount) {
        super(amount, ROOM_TEMPERATURE);
    }

    private Milk(int amount, int temperature) {
        super(amount, temperature);
    }

    private Milk(int amount, int temperature, boolean foamed) {
        super(amount, temperature);
        this.foamed = foamed;
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

    public static Milk of(int amount, int temperature, boolean foamed) {
        return new Milk(amount, temperature, foamed);
    }

    @Override
    public Milk newInstance() {
        return Milk.create();
    }

}
