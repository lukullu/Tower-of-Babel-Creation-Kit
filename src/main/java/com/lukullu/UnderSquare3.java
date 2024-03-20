package com.lukullu;

import com.kilix.processing.ExtendedPApplet;

import com.lukullu.enums.Shapes;
import com.lukullu.gameObjects.IGameObject;
import com.lukullu.gameObjects.gameplayObjects.GameplayObject;
import com.lukullu.gameObjects.gameplayObjects.entityObjects.EntityObject;
import com.lukullu.gameObjects.gameplayObjects.entityObjects.Player.Player;
import com.lukullu.utils.*;
import com.lukullu.utils.Collision;

import java.util.List;

public class UnderSquare3 extends ExtendedPApplet {

    public static GameObjectMultiHashMap gameObjects = new GameObjectMultiHashMap();

    public void setup()
    {
        background(40);
        frameRate(60);

        //Debug Stuff

        //gameObjects.putEntity(new EntityObject(Shapes.HEXAGON,new Vec2(300,570), 0, 75,2));
        gameObjects.putEntity(new EntityObject(Shapes.HEXAGON,new Vec2(1000,450), 0, 150,2));
        gameObjects.putEntity(new Player(Shapes.SQUARE,new Vec2(600,600), 0, 50));


    }

    public void draw()
    {

        background(40);

        // calc new frame-time
        DeltaTimer.getInstance().update();

        // tick every GameObject
        for (var entry : gameObjects.entrySet()) {
            Class<?> key = entry.getKey();
            List<IGameObject> value = entry.getValue();
            for (var gameObject : value) {
                gameObject.update();
            }
        }

        // draw every GameObject
        for (var entry : gameObjects.entrySet()) {
            Class<?> key = entry.getKey();
            List<IGameObject> value = entry.getValue();
            for (var gameObject : value) {
                gameObject.paint(); //TODO Draw hierarchy
            }
        }

        // collide every GameplayObject
        @SuppressWarnings("all")
        List<GameplayObject> entities = (List<GameplayObject>)(Object)gameObjects.get(EntityObject.class);
        if(!entities.isEmpty()) {
            for (GameplayObject query : entities) {
                // TODO spacial partitioning
                for (GameplayObject entity : entities) {
                    if (query != entity) {
                        Vec2 delta = Collision.collisionResolutionSAT(query,entity);
                        query.updatePos(delta);
                    }
                }
            }
        }

    }

    public void keyPressed() { InputManager.getInstance().keyPressed(); }
    public void keyReleased() { InputManager.getInstance().keyReleased(); }

}
