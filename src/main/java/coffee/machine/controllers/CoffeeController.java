package coffee.machine.controllers;

import coffee.machine.Coffee;
import coffee.machine.CoffeeKind;
import coffee.machine.CoffeeMachine;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coffee")
@AllArgsConstructor
@Log4j2
public class CoffeeController {

    private final CoffeeMachine coffeeMachine;

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee makeCoffee(@RequestParam(name = "coffeeKind") CoffeeKind coffeeKind) {
        log.info("Request for {} arrived", coffeeKind.toString().toLowerCase());
        return coffeeMachine.makeCoffee(coffeeKind);
    }

}
