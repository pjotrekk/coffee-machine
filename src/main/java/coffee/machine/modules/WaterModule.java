package coffee.machine.modules;

public interface WaterModule {
    void checkWaterTank(int amountNeeded);
    void prepareWater(int water);
}
