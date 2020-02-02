package coffee.machine;

public enum CoffeeKind {
    ESPRESSO(50, 20),
    AMERICANO(250, 20);

    private int waterNeeded;
    private int coffeeNeeded;

    CoffeeKind(int waterNeeded, int coffeeNeeded) {
        this.waterNeeded = waterNeeded;
        this.coffeeNeeded = coffeeNeeded;
    }

    public int getWaterNeeded() {
        return waterNeeded;
    }

    public int getCoffeeNeeded() {
        return coffeeNeeded;
    }
}
