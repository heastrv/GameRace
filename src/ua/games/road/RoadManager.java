package ua.games.road;

import com.cs.engine.cell.Game;
import ua.games.PlayerCar;
import ua.games.RacerGame;

import java.util.ArrayList;
import java.util.List;

public class RoadManager {
    public static final int LEFT_BORDER = RacerGame.ROADSIDE_WIDTH;
    public static final int RIGHT_BORDER = RacerGame.WIDTH - LEFT_BORDER;
    public static final int PLAYER_CAR_DISTANCE = 12;
    private static final int FIRST_LINE_POSITION = 16;
    private static final int FOURTH_LINE_POSITION = 42;
    private List<RoadObject> roadObjectList =  new ArrayList<>();
    private  int passedCarsCount = 0;

    public int getPassedCarsCount() {
        return passedCarsCount;
    }

    private boolean isRoadSpaceFree(RoadObject roadObject){
        for (RoadObject object : roadObjectList) {
            if (object.isCollisionWithDistance(roadObject,PLAYER_CAR_DISTANCE))
                return  false;
        }
        return true;
    }
    private RoadObject createRoadObject(RoadObjectType type, int x,int y){
        if (type == RoadObjectType.THORN)  return new Thorn(x,y);
        if (type == RoadObjectType.DRUNK_CAR)  return new MovingCar(x,y);
        return new Car(type,x,y);
    }

    private void addRoadObject(RoadObjectType type, Game game){
        int x =game.getRandomNumber(FIRST_LINE_POSITION, FOURTH_LINE_POSITION);
        int y = -1 * RoadObject.getHeight(type);
        RoadObject roadObject = createRoadObject(type, x, y);
        if (roadObject != null && isRoadSpaceFree(roadObject))
            roadObjectList.add(roadObject);
    }

    public void draw(Game game){
        for (RoadObject item : roadObjectList) {
            item.draw(game);
        }
    }

    public void move(int boost){
        for (RoadObject item : roadObjectList) {
            item.move(boost + item.speed,roadObjectList);
        }
        deletePassedItems();

    }

    private void deletePassedItems() {

        ArrayList<RoadObject> objectTMP = new ArrayList<>();
        objectTMP.addAll(roadObjectList);

        for (RoadObject roadObject : objectTMP) {
            if (roadObject.y> RacerGame.HEIGHT) {
                roadObjectList.remove(roadObject);
                if (!roadObject.type.equals(RoadObjectType.THORN))
                    passedCarsCount++;
            }
        }

        //roadObjectList.removeIf(a -> a.y > RacerGame.HEIGHT);
    }

    public void generateNewRoadObject(Game game) {
        generateThorn(game);
        generateRegularCar(game);
        generateMovingCar(game);

        
    }

    private void generateMovingCar(Game game) {
        if (game.getRandomNumber(100) < 10 && !isMovingCarExists())
            addRoadObject(RoadObjectType.DRUNK_CAR, game);
    }

    private boolean isMovingCarExists() {
        for (RoadObject roadObject : roadObjectList) {
            if (roadObject.type.equals(RoadObjectType.DRUNK_CAR))
                return  true;
        }
        return false;
    }

    private void generateRegularCar(Game game) {
        int carTypeNumber = game.getRandomNumber(4);
        if (game.getRandomNumber(100) < 30)
            addRoadObject(RoadObjectType.values()[carTypeNumber], game);
    }

    private void generateThorn(Game game) {
        if (game.getRandomNumber(100) < 10 && !isThornExists())
            addRoadObject(RoadObjectType.THORN, game);
    }

    private boolean isThornExists() {
        //iter
        for (RoadObject roadObject : roadObjectList) {
            if (roadObject.type.equals(RoadObjectType.THORN))
                return true;
        }
        return false;
    }

    public boolean checkCrush(PlayerCar playerCar){
        for (RoadObject roadObject : roadObjectList) {
            if (roadObject.isCollision(playerCar))
                return true;
        }
        return false;
    }
}
