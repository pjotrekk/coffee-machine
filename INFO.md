# Assumptions:
### Tanks:
Tanks return fixed because the real ones would be able to calculate the weight on the fly.
If I would manipulate those values programatically there could be a situation where
Coffee Machine set some value and the user refilled/emptied the tank and the value in the
program would become inappropriate.

### Pumps:
In my coffee machine pumps mindlessly start pumping when they are asked to.
They depend on validation checks and want to have pipes connected properly.
They know how long they should work to transfer given amount of liquid.

### Heater:
Heater heats the water until it evaporates. The steam comes through the coffee pot, gets
the essence of the coffee and condenses directly to the user's cup.

### Grounder:
Grounder grounds the coffee directly to the coffee pot. The internet says the coffee should't
be flattened, just lay as it was poured.

### Pot:
The coffee pot stores the coffee while the heater evaporates water through it and
is able to flip, which removes used coffee to the wastes tank.

# How to run
In Intellij:
Install lombok plugin
Run CoffeeMachineApplication

Use POST requests on /coffee with param coffeeKind where
CoffeeKind = { "Americano", "Espresso", "Latte", "Cappuccino" } - the capitalization doesn't matter.

## Comments
This task could be done without Spring, but I wanted to use it as it's a recruitment task and most 
of Java projects are done with it.
