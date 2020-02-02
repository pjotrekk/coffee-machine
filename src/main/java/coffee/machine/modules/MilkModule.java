package coffee.machine.modules;

public interface MilkModule {
    void checkMilkContainer(int amountNeeded);
    void moveMilkToHeater(int amount);
    void moveMilkToCup(int amount);
    void heatMilk(int amount);
}
