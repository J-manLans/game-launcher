# Launcher
- Maybe a loading template for the launcher?
- Function to dynamically resize the launcher (I'm thinking of a listener for window resize and setting the preferred size as a function of the window size for the panels).

# Games

## QUALITY OF LIFE IMPLEMENTATIONS:
- Nullify objects for controller, view, and model in the close method.
- refactor to have one addListener method with switch cases using strings to determine where to add the listener
- add more statistics at deathscreen, speed, distance traveled and more.

## BASIC GAME MECHANICS FOR SINGLE-PLAYER SNAKE:
- Add a third booster if there is time, maybe invincibility, very easy i think. just add a boolean that checks for snake collision and skip that if the boolean is active,
- Another could be setting up walls around the grid so the snake cant wrap around, think this would involve maybe keeping a int[][] in that booster and just overlay it on the gamegrid, additionally have a separate method in mainview that adds the wall if a boolean is active

## MENUS:
- Settings, choose snake color.
- Start speed.
- Solid walls or not.
- Power-ups on or off, one for single-player and one for multiplayer (for single-player, it could be to slow down speed, bomb placement of food, etc.; for multiplayer, it could involve setting solid walls, slowing down, preventing food spawn and increasing speed, etc.).

## CHANGES FROM LAB3
- Add the ImageManager to support and remove the loadicon method from the controller. now this enum can be used by views across the whole application
- Change the addGameIconListeners in launcher view so it handles the for each loop instead of the controller
- change the scroll pane listener as well
- move the swingutilities i have in the startlauncher method into the view instead of the controller