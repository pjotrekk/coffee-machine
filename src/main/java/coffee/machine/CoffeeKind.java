package coffee.machine;

public enum CoffeeKind {
    ESPRESSO(50, 20, 0),
    AMERICANO(250, 20, 0),
    LATTE(250, 20, 100);

    private int waterNeeded;
    private int coffeeNeeded;
    private int milkNeeded;

    CoffeeKind(int waterNeeded, int coffeeNeeded, int milkNeeded) {
        this.waterNeeded = waterNeeded;
        this.coffeeNeeded = coffeeNeeded;
        this.milkNeeded = milkNeeded;
    }

    public int getWaterNeeded() {
        return waterNeeded;
    }

    public int getCoffeeNeeded() {
        return coffeeNeeded;
    }

    public int getMilkNeeded() {
        return milkNeeded;
    }
}
