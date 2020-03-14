# Assumptions:
### Tanks:
Tanks contain an ingredient. They can be modified by setting/adding a new amount
or by acquiring an element. This replaces pumps that added no functionality to the
program.

### Ingredients:
Ingredients can be mutated only by splitting them. They return a new object with
asked amount and subtract their one.

### Heater:
Heater returns heated object. Used only for milk.

### Evaporator:
It evaporates water and returns steam (water with evaporated set to true)

### Grounder:
Grounder returns grounded coffee (coffee grain with grounded set to true)

### Pot:
The coffee pot takes grounded coffee and steam and combines them to the coffee
essence (water with a coffee extract)

### Milk Module
It can check whether its tank has a proper amount of milk or whether is overflown
and prepare heated and foamed milk.

### Water Module
It can check whether its tank has a proper amount of water or whether is overflown
and prepare steam needed to flush it through coffee pot in coffee module.

### Coffee Module
It can check whether its tank has a proper amount of coffee or whether is overflown,
ground coffee needed by coffee pot and accept steam and that coffee to prepare
coffee essence

### Wastes Module
It can only check whether it is full. 

# How to run
In Intellij:
Install lombok plugin
Run CoffeeMachineApplication

Use GET requests on http://localhost:8080/coffee with param coffeeKind where
CoffeeKind = { "Americano", "Espresso", "Latte", "Cappuccino" } - the capitalization doesn't matter.

The return value is in the form:
```
{
  water: <int>,
  coffeeExtract: <int>,
  milk: <int>,
  withFoam: <boolean>
}
```
At the start of the application all the tanks are empty. They can be refilled by
sending a PUT request on /tanks/{tank} with amount param, tank = {water, milk, 
coffee}.

To empty wastes bin send PUT request on /tanks/wastes with no params.
