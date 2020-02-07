package coffee.machine.components;

import coffee.machine.ingredients.Solid;

public class SolidTank extends Tank {
    private final Solid solid;

    private SolidTank(Solid solid, int capacity) {
        super(capacity);
        this.solid = solid;
    }

    public static SolidTank of(Solid solid, int capacity) {
        return new SolidTank(solid, capacity);
    }

    @Override
    public Solid getIngredient() {
        return solid;
    }
}
