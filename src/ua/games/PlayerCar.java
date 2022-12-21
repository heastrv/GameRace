package ua.games;

import ua.games.road.RoadManager;

public class PlayerCar extends GameObject{
    private static final int playerCarHeight = ShapeMatrix.PLAYER.length;
    private Direction direction = Direction.NONE;
    public int speed = 1;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public PlayerCar() {
        super(RacerGame.CENTER_X + 2,
                RacerGame.HEIGHT- 1 - playerCarHeight ,
                ShapeMatrix.PLAYER);
    }

    public void stop(){
        matrix = ShapeMatrix.PLAYER_DEAD;
    }

    public void move(){
        switch (direction) {
            case LEFT: x--;
            break;
            case RIGHT: x++;
            break;
        }
        if (x < RoadManager.LEFT_BORDER+1) x = RoadManager.LEFT_BORDER+1;
        if (x > RoadManager.RIGHT_BORDER-width) x = RoadManager.RIGHT_BORDER - width;
    }
}
