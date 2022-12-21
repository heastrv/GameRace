package ua.games.road;

import ua.games.GameObject;
import ua.games.PlayerCar;
import ua.games.RoadMarking;
import ua.games.ShapeMatrix;

import java.util.List;

public class RoadObject  extends GameObject {
    public RoadObjectType type;
    public int speed;
    public RoadObject(RoadObjectType type,int x, int y) {
        super(x, y);
        this.type = type;
        this.matrix = getMatrix(type);
        this.height = matrix.length;
        this.width = matrix[0].length;
    }

    public static int getHeight(RoadObjectType type) {
        return getMatrix(type).length;
    }

    /**
    * Метод, отвечающий за передвижение препятствий
     */
    public void move(int boost, List<RoadObject> roadObjects){
        this.y += boost;
    }

    /**
     * Возвращает матрицу изображения в зависимости от типа
     */
    private static int[][] getMatrix(RoadObjectType type) {
        return switch (type) {
            case SPORT_CAR -> ShapeMatrix.SPORT_CAR;
            case TRUCK -> ShapeMatrix.TRUCK;
            case DRUNK_CAR -> ShapeMatrix.DRUNK_CAR;
            case BUS -> ShapeMatrix.BUS;
            case PASSENGER_CAR -> ShapeMatrix.PASSENGER_CAR;
            default -> ShapeMatrix.THORN;
        };
    }

    public boolean isCollisionWithDistance(RoadObject roadObject, int distance) {
        
        if (x - distance > roadObject.x + roadObject.width ||
            x +width < roadObject.x - distance)
            return false;

        if(roadObject.y + roadObject.height < y - distance ||
           y + height < roadObject.y - distance )
            return false;

        return true;
    }
}
