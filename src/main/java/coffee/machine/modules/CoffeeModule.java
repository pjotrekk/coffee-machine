package coffee.machine.modules;

public interface CoffeeModule {
    void checkCapacity(int amountNeeded);
    void ground(int amount);
    void flipUsedCoffee();
}
