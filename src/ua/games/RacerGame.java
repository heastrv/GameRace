package ua.games;

import com.cs.engine.cell.Color;
import com.cs.engine.cell.Game;
import com.cs.engine.cell.Key;
import ua.games.road.RoadManager;

public class RacerGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int CENTER_X = WIDTH / 2;
    public static final int ROADSIDE_WIDTH = 14;
    private static final int RACE_GOAL_CARS_COUNT = 40;
    private boolean isGameStopped;

    private PlayerCar player;
    private RoadMarking roadMarking;
    private RoadManager roadManager;
    private FinishLine finishLine;
    private ProgressBar progressBar;

    public void initialize() {
        setScreenSize(WIDTH,HEIGHT);
        //showGrid(false);
        createGame();
    }

    private void createGame() {
       player = new PlayerCar();
       roadMarking = new RoadMarking();
       roadManager = new RoadManager();
       finishLine =  new FinishLine();
       progressBar = new ProgressBar(RACE_GOAL_CARS_COUNT);
       drawScene();
       setTurnTimer(100);
       isGameStopped = false;
    }

    /**
     * Метод отрисовки всех игровых объектов
     */
    private void drawScene() {
       drawField();
       roadMarking.draw(this);
       player.draw(this);
        roadManager.draw(this);
        finishLine.draw(this);
        progressBar.draw(this);

    }

    /**
     * Метод отрисовки игрового поля
     */
    private void drawField() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (CENTER_X == x){
                    setCellColor(x, y, Color.WHITE);
                }else if ( ROADSIDE_WIDTH < x && x < WIDTH-ROADSIDE_WIDTH){
                    setCellColor(x, y, Color.DIMGREY);
                }else {
                    setCellColor(x, y, Color.GREEN);
                }
            }
        }
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if(x>=0 && x< WIDTH && y>=0 && y < HEIGHT)
            super.setCellColor(x, y, color);
    }

    @Override
    public void onTurn(int t) {

        //super.onTurn(step);
        if(roadManager.checkCrush(player)){
            gameOver();
            drawScene();
            return;
        }
        if ( roadManager.getPassedCarsCount() >= RACE_GOAL_CARS_COUNT)
            finishLine.show();

        if(finishLine.isCrossed(player)){
            win();
            drawScene();
            return;
        }
        moveAll();
        roadManager.generateNewRoadObject(this);
        drawScene();
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "Game Over",Color.YELLOW,75);
        stopTurnTimer();
        player.stop();
    }

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "You Win!!!",Color.YELLOW,75);
        stopTurnTimer();

    }

    @Override
    public void onKeyPress(Key key) {
        switch (key){
            case RIGHT: player.setDirection(Direction.RIGHT);
            break;
            case LEFT: player.setDirection(Direction.LEFT);
            break;
            case UP: player.speed = 2;
            break;
            case SPACE: if (isGameStopped) createGame();
            break;
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        switch (key){
            case RIGHT:
                if(player.getDirection() == Direction.RIGHT)
                    player.setDirection(Direction.NONE);
                break;
            case LEFT:
                if (player.getDirection() == Direction.LEFT)
                    player.setDirection(Direction.NONE);
                break;
            case UP: player.speed = 1;
        }
    }

    /**
     * метод moveAll(), который будет перемещать
     * все подвижные игровые объекты
     * (пока только разметку)
     */
    private void moveAll() {
        roadMarking.move(player.speed);
        player.move();
        roadManager.move(player.speed);
        finishLine.move(player.speed);
        progressBar.move(roadManager.getPassedCarsCount());
    }
}
