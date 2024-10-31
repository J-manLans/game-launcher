# Launcher
- Maybe a loading template for the launcher?
- Function to dynamically resize the launcher (I'm thinking of a listener for window resize and setting the preferred size as a function of the window size for the panels).

# Games

## QUALITY OF LIFE IMPLEMENTATIONS:
- Nullify objects for controller, view, and model in the close method.
- refactor to have one addListener method with switch cases using strings to determine where to add the listener
- *Figure out how to work with boosters*
- Set back button to BaseView as a shared variable. (maybe not, quite handy to have different listeners for controls and singleplayer)

## BASIC GAME MECHANICS FOR SINGLE-PLAYER SNAKE:
- in spawnbooster check the list for isActive and if so countdown the timer for the booster. when 0 call its reset method. the reset method in speedboster calculates half of the speed that it saved and subtract the current speed, then it reset the speed to the original saved value and subtract the value just calculated...should work

## MENUS:
- Settings, choose snake color.
- Start speed.
- Solid walls or not.
- Power-ups on or off, one for single-player and one for multiplayer (for single-player, it could be to slow down speed, bomb placement of food, etc.; for multiplayer, it could involve setting solid walls, slowing down, preventing food spawn and increasing speed, etc.).