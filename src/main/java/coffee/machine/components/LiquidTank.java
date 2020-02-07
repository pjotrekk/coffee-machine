package coffee.machine.components;

import coffee.machine.ingredients.Liquid;

public class LiquidTank extends Tank {
    private final Liquid liquid;

    private LiquidTank(Liquid liquid, int capacity) {
        super(capacity);
        this.liquid = liquid;
    }

    public static LiquidTank of(Liquid liquid, int capacity) {
        return new LiquidTank(liquid, capacity);
    }

    @Override
    public Liquid getIngredient() {
        return liquid;
    }
}
