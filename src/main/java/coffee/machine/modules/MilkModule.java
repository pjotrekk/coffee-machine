package coffee.machine.modules;

public interface MilkModule {
    void checkMilkContainer(int amountNeeded);
    void prepareMilk(int amount, boolean withFoam);
}
