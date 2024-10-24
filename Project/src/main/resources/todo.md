# Launcher
- Maybe a loading template for the launcher?
- Function to dynamically resize the launcher (I'm thinking of a listener for window resize and setting the preferred size as a function of the window size for the panels).

# Games

## QUALITY OF LIFE IMPLEMENTATIONS:
- Nullify objects for controller, view, and model in the close method.
- *refactor view with getters to all its views so i don't need to clutter main view with addlistenermethods*
- *Or, let snake controller hold all views and models*
- refactor to have one addlistener method with switchcases using strings to determine where to add the listener

## BASIC GAME MECHANICS FOR SINGLE-PLAYER SNAKE:
*- Collision handling (with itself and **possibly** the edges).*

## MENUS:
- Settings, choose snake color.
- Start speed.
- Solid walls or not.
- Power-ups on or off, one for single-player and one for multiplayer (for single-player, it could be to slow down speed, bomb placement of food, etc.; for multiplayer, it could involve setting solid walls, slowing down, preventing food spawn and increasing speed, etc.).