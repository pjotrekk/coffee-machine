package coffee.machine.modules;

public interface HeatingModule {
    void heat(int amount);
    void checkCapacity(int amount);
}
