package coffee.machine.modules;

public interface WaterModule {
    void checkCapacity(int amountNeeded);
    void moveWaterToHeater(int amount);
}
