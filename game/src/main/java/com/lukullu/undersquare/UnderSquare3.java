package com.lukullu.undersquare;

import com.kilix.processing.ExtendedPApplet;

import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.utils.*;
import com.lukullu.undersquare.entities.Player;
import com.lukullu.undersquare.entityTypes.SegmentEntity;
import com.tbck.math.Vec2;

import java.util.List;

public class UnderSquare3 extends ExtendedPApplet {

    public static GameObjectMultiHashMap gameObjects = new GameObjectMultiHashMap();

    public void setup()
    {
        gameObjects.putEntity(new Player("src/main/resources/shapeFiles/playerShape.psff",new Vec2(600,600), 0, 5));
        gameObjects.putEntity(new EntityObject(Shapes.SQUARE,new Vec2(900,600),0,75));
        gameObjects.putEntity(new SegmentEntity("src/main/resources/shapeFiles/testShape.psff",new Vec2(1400,600), 0, 5));
        //gameObjects.putMetaObject(new MetaObject(Shapes.SQUARE,new Vec2(1000,700), 0, 75,()->{System.out.println("Hello World");},false));


    }

    public void draw()
    {

        background(40);

        // calc new frame-time
        DeltaTimer.getInstance().update();

        // tick every GameObject
        for (var entry : gameObjects.entrySet())
        {
            Class<?> key = entry.getKey();
            List<IGameObject> value = entry.getValue();
            for (var gameObject : value)
            {
                gameObject.update();
            }
        }

        // collide every GameObject
        for (var entry : gameObjects.entrySet())
        {
            Class<?> key = entry.getKey();
            List<IGameObject> value = entry.getValue();
            for (var gameObject : value)
            {
                if(gameObject instanceof EntityObject)
                {
                    ((EntityObject) gameObject).dynamicCollisionUpdatePolygon();
                }

            }
        }

        // draw every GameObject
        for (var entry : gameObjects.entrySet())
        {
            Class<?> key = entry.getKey();
            List<IGameObject> value = entry.getValue();
            for (var gameObject : value)
            {
                gameObject.paint(); //TODO Draw hierarchy
            }
        }

    }

    public static GameObjectMultiHashMap getGameObjects() { return gameObjects; }
    public void keyPressed() { InputManager.getInstance().keyPressed(); }
    public void keyReleased() { InputManager.getInstance().keyReleased(); }

}