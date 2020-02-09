package coffee.machine.ingredients;

import lombok.Getter;

@Getter
public final class Milk extends Ingredient<Milk> {

    public static final int PERFECT_TEMPERATURE = 65;

    private int temperature = ROOM_TEMPERATURE;

    private boolean foamed = false;

    @Override
    protected Milk self() {
        return this;
    }

    @Override
    public Milk newInstance(int amount) {
        return Milk.of(amount, getTemperature(), foamed);
    }

    public static Milk create() {
        return new Milk();
    }

    public static Milk of(int amount, int temperature, boolean foamed) {
        return new Milk(amount, temperature, foamed);
    }

    private Milk() {}

    private Milk(int amount, int temperature, boolean foamed) {
        super(amount);
        this.temperature = temperature;
        this.foamed = foamed;
    }

}
