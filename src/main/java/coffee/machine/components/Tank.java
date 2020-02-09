package coffee.machine.components;

import coffee.machine.ingredients.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Tank<T extends Ingredient<T>> {

    private T ingredient;

    @Getter
    private final int capacity;

    public boolean isOverflown() {
        return ingredient.getAmount() > capacity;
    }

    public void addAmount(int amount) {
        checkAmountBelowZero(amount);
        ingredient = ingredient.addAmount(amount);
    }

    public void setCurrentAmount(int amount) {
        checkAmountBelowZero(amount);
        ingredient = ingredient.setAmount(amount);
    }

    public int getCurrentAmount() {
        return ingredient.getAmount();
    }

    public T acquire(int amount) {
        checkEnoughIngredient(amount);
        checkAmountBelowZero(amount);
        ingredient = ingredient.setAmount(ingredient.getAmount() - amount);
        return ingredient.newInstance(amount);
    }

    private void checkEnoughIngredient(int amount) {
        if (amount > ingredient.getAmount()) {
            throw new AssertionError("Insufficient amount in tank");
        }
    }

    private void checkAmountBelowZero(int amount) {
        if (amount < 0) {
            throw new AssertionError("Amount cannot be below zero");
        }
    }

}
