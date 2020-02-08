package coffee.machine;

import lombok.*;

@NoArgsConstructor(staticName = "create")
@AllArgsConstructor(staticName = "of")
@Getter @Setter
@EqualsAndHashCode
public class Coffee {
    private int water = 0;
    private int coffeeExtract = 0;
    private int milk = 0;
    private boolean withFoam = false;
}
