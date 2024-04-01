package com.lukullu.undersquare;

import com.kilix.processing.ExtendedPApplet;

import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.MetaObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.StaticObject;
import com.lukullu.tbck.utils.*;
import com.lukullu.undersquare.entities.Player;
import com.lukullu.undersquare.entityTypes.Entity;
import com.lukullu.undersquare.entityTypes.Meta;
import com.lukullu.undersquare.entityTypes.Static;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.Vec2;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UnderSquare3 extends ExtendedPApplet {

    public static GameObjectMultiHashMap gameObjects = new GameObjectMultiHashMap();

    public void setup()
    {
        // "../game/src/main/resources/shapeFiles/playerShape.psff"

        gameObjects.putEntity(new Player("/shapeFiles/playerShape.psff", new Vec2(600,600), 0, 1));
        gameObjects.putEntity(new Entity("/shapeFiles/testShape.psff",new Vec2(900,600), 0, 2));
        gameObjects.putEntity(new Entity("/shapeFiles/testShape.psff",new Vec2(500,400), 0, 2));
        gameObjects.putMeta(  new Meta  ("/shapeFiles/testShape.psff",new Vec2(1000,700), 0, 1,()->{System.out.println("Hello World");},false));
        gameObjects.putStatic(new Static("/shapeFiles/testShape.psff",new Vec2(800,500), PI, 2));

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
                else if(gameObject instanceof StaticObject)
                {
                    ((StaticObject) gameObject).dynamicCollisionUpdatePolygon();
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

    private static final void storeShapes() {
        Player player = new Player("../game/src/main/resources/shapeFiles/legacy/playerShape.psff", new Vec2(600,600), 0, 5);
        Entity testEntity = new Entity("../game/src/main/resources/shapeFiles/legacy/testShape.psff",new Vec2(1400,600), 0, 5);

        try {
            SegmentDataManager.saveExternal(new File("./playerShape.psff"), player.getSegments());
            SegmentDataManager.saveExternal(new File("./testShape.psff"), testEntity.getSegments());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

    public static GameObjectMultiHashMap getGameObjects() { return gameObjects; }
    public void keyPressed() { InputManager.getInstance().keyPressed(); }
    public void keyReleased() { InputManager.getInstance().keyReleased(); }

}
