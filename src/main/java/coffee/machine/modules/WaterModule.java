package coffee.machine.modules;

public interface WaterModule {
    void checkWaterTank(int amountNeeded);
    void moveWaterToHeater(int amount);
    void heatWater(int amount);
}
