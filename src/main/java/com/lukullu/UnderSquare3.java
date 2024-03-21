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
        //frameRate(30);

        gameObjects.putEntity(new EntityObject(Shapes.TRIANGLE,new Vec2(1000,600), 0, 75,2));
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

        // collide every GameplayObject
        /*@SuppressWarnings("all")
        List<EntityObject> entities = (List<EntityObject>)(Object)gameObjects.get(EntityObject.class);
        if(!entities.isEmpty()) {
            for (EntityObject query : entities) {
                // TODO spacial partitioning
                for (EntityObject entity : entities) {
                    if (query != entity) {
                        CollisionResult res = Collision.collisionResolutionSAT(query,entity);

                        if(res.collisionCheck)
                        {
                            // TODO: implement proper push physics based on weight
                            if(query.getPushPriority() <= entity.getPushPriority())
                            {
                                query.updatePos(res.delta);
                            }
                            else
                            {
                                entity.updatePos(res.delta.multiply(-1));
                            }
                        }
                    }
                }
            }
        }*/

        // draw every GameObject
        for (var entry : gameObjects.entrySet()) {
            Class<?> key = entry.getKey();
            List<IGameObject> value = entry.getValue();
            for (var gameObject : value) {
                gameObject.paint(); //TODO Draw hierarchy
            }
        }

    }

    public static GameObjectMultiHashMap getGameObjects() { return gameObjects; }
    public void keyPressed() { InputManager.getInstance().keyPressed(); }
    public void keyReleased() { InputManager.getInstance().keyReleased(); }

}
