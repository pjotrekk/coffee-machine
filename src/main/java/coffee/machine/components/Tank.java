package coffee.machine.components;

import coffee.machine.ingredients.Ingredient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor()
public abstract class Tank {

    @Getter
    private final int capacity;

    public abstract Ingredient getIngredient();

    public boolean isOverflown() {
        return getIngredient().getAmount() > capacity;
    }

    public void addAmount(int amount) {
        checkAmountBelowZero(amount);
        getIngredient().addAmount(amount);
    }

    public void reduceAmount(int amount) {
        checkAmountBelowZero(amount);
        checkHasEnoughIngredient(amount);
        getIngredient().reduceAmount(amount);
    }

    public void setCurrentAmount(int amount) {
        checkAmountBelowZero(amount);
        getIngredient().setAmount(amount);
    }

    public int getCurrentAmount() {
        return getIngredient().getAmount();
    }

    private void checkHasEnoughIngredient(int amount) {
        if (getIngredient().getAmount() < amount) {
            throw new AssertionError("Cannot reduce the tank's ingredient amount below zero");
        }
    }

    private void checkAmountBelowZero(int amount) {
        if (amount < 0) {
            throw new AssertionError("Amount cannot be below zero");
        }
    }

}
