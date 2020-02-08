package coffee.machine.ingredients;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public abstract class Ingredient<T extends Ingredient<T>> {

    public static final int ROOM_TEMPERATURE = 23;

    @Getter
    private int amount = 0;

    public T addAmount(int amount) {
        return self().newInstance(this.amount + amount);
    }

    public T setAmount(int amount) {
        return self().newInstance(amount);
    }

    public T split(int amount) {
        checkAmount(amount);
        this.amount -= amount;
        return self().newInstance(amount);
    }

    protected abstract T self();

    public abstract T newInstance(int amount);

    private void checkAmount(int amount) {
        if (this.amount < amount) {
            throw new AssertionError("Trial of split over current amount");
        }
    }
}
