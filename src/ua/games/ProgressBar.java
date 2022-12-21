package ua.games;


import com.cs.engine.cell.Color;
import com.cs.engine.cell.Game;

import java.util.Arrays;

public class ProgressBar {

    private int maxValue;
    private GameObject progressBar;
    private GameObject progressBarField;


    public ProgressBar(int maxValue) {
        this.maxValue = maxValue;
        int[][] fieldMatrix = createColoredMatrix(1,maxValue, Color.BLACK);
        int[][] indexMatrix = createColoredMatrix(1,1, Color.WHITE);
        int x = RacerGame.WIDTH - 5;
        int y = RacerGame.HEIGHT/2 - maxValue/2;

        progressBar = new GameObject(x,y+maxValue,indexMatrix);
        progressBarField = new GameObject(x,y,fieldMatrix);
    }

    public void draw(Game game){
        progressBarField.draw(game);
        progressBar.draw(game);
    }

    private int[][] createColoredMatrix(int width, int height, Color color) {
        int[] line = new int[width];
        Arrays.fill(line,color.ordinal());
        int[][] matrix = new int[height][width];
        Arrays.fill(matrix,line);

        return matrix;
    }

    public void move(int currentValue){
        int dy = (currentValue < maxValue-1) ? currentValue: maxValue-1;
//        if(currentValue < maxValue)
//            dy = currentValue;
//        else dy = maxValue;
        progressBar.y = progressBarField.y+ progressBarField.height - dy - 1;
    }
}
