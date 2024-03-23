package com.lukullu.undersquare;

import com.kilix.processing.ExtendedPApplet;

import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.MetaObject;
import com.lukullu.tbck.utils.*;
import com.lukullu.undersquare.entities.Player;

import java.util.List;

public class UnderSquare3 extends ExtendedPApplet {

    public static GameObjectMultiHashMap gameObjects = new GameObjectMultiHashMap();

    public void setup()
    {
        gameObjects.putEntity(new EntityObject(Shapes.PENTAGON,new Vec2(1400,600), 0, 75,2));
        gameObjects.putMetaObject(new MetaObject(Shapes.SQUARE,new Vec2(1000,700), 0, 75,()->{System.out.println("Hello World");},false));
        gameObjects.putEntity(new EntityObject(Shapes.POLY16,new Vec2(1000,450), 0, 150,2));
        gameObjects.putEntity(new Player(Shapes.HEXAGON,new Vec2(600,600), 0, 50));

    }

    public void draw()
    {

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

        background(40);

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
