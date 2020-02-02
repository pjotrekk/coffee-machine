# Assumptions:
## Tanks:
Tanks return fixed because the real ones would be able to calculate the weight on the fly.
If I would manipulate those values programatically there could be a situation where
Coffee Machine set some value and the user refilled/emptied the tank and the value in the
program would become inappropriate.

## Pumps:
In my coffee machine pumps mindlessly start pumping when they are asked to.
They depend on validation checks and want to have pipes connected properly.
They know how long they should work to transfer given amount of liquid.

## Heater:
Heater heats the water until it evaporates. The steam comes through the coffee pot, gets
the essence of the coffee and condenses directly to the user's cup.

## Grounder:
Grounder grounds the coffee directly to the coffee pot. The internet says the coffee should't
be flattened, just lay as it was poured.