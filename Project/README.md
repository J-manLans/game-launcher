# Final Project

## Environment & Tools
[replace this with relevant information]

## Purpose
[replace this with relevant information]

## Procedures
[replace this with relevant information]

## Discussion
### Purpose Fulfillment
[replace this with relevant information]

### Alternative Approaches
[replace this with relevant information]
Could go for an interface for boosters instead of an abstract class, using generics to decide what model to pass as an argument into the different methods

````java
public interface Booster<T> {
    void applyEffect(T gameModel);
}
````

I could have the mainView and model handle all other models, letting the controller only interact with them, letting them act as intermediary classes. However, I chose to let the controller handle everything since it's still a small game and the intermediary functions of mainView and model felt like it didn't add anything. But would I implement a bigger game in the future I think i might go that path for a cleaner approach and separation of concerns, alternatively have additionally dedicated controllers.

use an List<int[]> for the snake for easy addition of body segments, but I'm using an interface for both the snakemodel and cherry model

## Personal Reflections
[replace this with relevant information]