package coffee.machine.ingredients;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public abstract class Ingredient {
    @Getter @Setter
    private int amount = 0;

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void reduceAmount(int amount) {
        this.amount -= amount;
    }

    public abstract Ingredient newInstance();
}
