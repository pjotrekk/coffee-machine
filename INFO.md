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