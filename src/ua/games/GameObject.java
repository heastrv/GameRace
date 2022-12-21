package ua.games;

import com.cs.engine.cell.Color;
import com.cs.engine.cell.Game;
import ua.games.road.MovingCar;

public class GameObject {
    public int x;
    public int y;
    public int[][] matrix;
    public int width;
    public int height;

    //Color.values()[matrix[y][x]]

    public GameObject(int x, int y, int[][] matrix) {
        this.x = x;
        this.y = y;
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
    }

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Game game){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                game.setCellColor(this.x+x,this.y+y,Color.values()[matrix[y][x]]);
            }
        }
    }

    public boolean isCollision(PlayerCar playerCar) {
        for (int carY = 0; carY < playerCar.height; carY++) {
            for (int carX = 0; carX < playerCar.width; carX++) {
                if (isCollision(carX + playerCar.x,carY + playerCar.y))
                    return true;
            }
        }
        return false;
    }

    private boolean isCollision(int x, int y) {
        for (int matrixY = 0; matrixY < height; matrixY++) {
            for (int matrixX = 0; matrixX < width; matrixX++) {
                if (matrix[matrixY][matrixX] !=0 && this.x + matrixX == x && this.y + matrixY == y)
                    return true;
            }
        }
        return false;
    }

    public boolean isCollisionPossible(GameObject otherObject) {
        if (x > otherObject.x + otherObject.width ||
           x + width < otherObject.x){
            return false;
        }

        if (y > otherObject.y + otherObject.height ||
           y + height < otherObject.y){
            return false;
        }

        return true;
    }
}
