package coffee.machine.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class Tank {

    @Setter
    private int currentAmount = 0;

    private final int capacity;

    public boolean isOverflown() {
        return this.currentAmount > capacity;
    }

}
