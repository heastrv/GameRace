package ua.games;

import com.cs.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class RoadMarking {
    List<GameObject> roadMarking = new ArrayList<>();

    public RoadMarking() {
        for (int i = -4; i < RacerGame.HEIGHT + 4; i = i+8) {
            roadMarking.add(new GameObject(RacerGame.CENTER_X-9,i,
                    ShapeMatrix.ROAD_MARKING));

            roadMarking.add(new GameObject(RacerGame.CENTER_X + 9,i,
                ShapeMatrix.ROAD_MARKING));
        }

    }

    public void move(int boost){
        for (GameObject item : roadMarking) {
            if (item.y >= RacerGame.HEIGHT){
                item.y = item.y - RacerGame.HEIGHT -8 + boost;
            }else
                item.y = item.y + boost ;
        }
    }

    public void draw(Game game){
        for (GameObject item : roadMarking) {
           item.draw(game);
        }
    }
}
