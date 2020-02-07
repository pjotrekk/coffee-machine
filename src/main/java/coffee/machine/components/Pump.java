package coffee.machine.components;

import coffee.machine.ingredients.Liquid;

public class Pump {
    private final LiquidTank tankFrom;
    private final LiquidTank tankTo;

    private Pump(LiquidTank from, LiquidTank to) {
        checkIngredientTypesSame(from.getIngredient(), to.getIngredient());
        tankFrom = from;
        tankTo = to;
    }

    public static Pump of(LiquidTank from, LiquidTank to) {
        return new Pump(from, to);
    }

    public void pump(int amount) {
        checkEnoughAmountInFrom(amount);
        checkWhetherToOverflows(amount);
        tankFrom.reduceAmount(amount);
        tankTo.addAmount(amount);
    }

    private void checkEnoughAmountInFrom(int amount) {
        if (tankFrom.getCurrentAmount() < amount) {
            throw new AssertionError("Cannot pump from a tank that has not enough liquid");
        }
    }

    private void checkWhetherToOverflows(int amount) {
        if (tankTo.getCurrentAmount() + amount > tankTo.getCapacity()) {
            throw new AssertionError("Cannot pump because the destination tank would overflow");
        }
    }

    private void checkIngredientTypesSame(Liquid first, Liquid second) {
        if (!first.getClass().equals(second.getClass())) {
            throw new AssertionError("Pump has to be connected to the tanks with the same ingredient");
        }
    }

}
