package com.lukullu.undersquare;

import com.kilix.processing.ExtendedPApplet;

import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.utils.*;
import com.lukullu.undersquare.entities.Player;
import com.lukullu.undersquare.entityTypes.SegmentEntity;
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


        gameObjects.putEntity(new Player("/shapeFiles/playerShape.psff", new Vec2(600,600), 0, 5));
        gameObjects.putEntity(new EntityObject(Shapes.SQUARE,new Vec2(900,600),0,75));
        gameObjects.putEntity(new SegmentEntity("/shapeFiles/testShape.psff",new Vec2(1400,600), 0, 5));
        //gameObjects.putMetaObject(new MetaObject(Shapes.SQUARE,new Vec2(1000,700), 0, 75,()->{System.out.println("Hello World");},false));

    }

    private static final void storeShapes() {
        Player player = new Player("../game/src/main/resources/shapeFiles/legacy/playerShape.psff", new Vec2(600,600), 0, 5);
        SegmentEntity testEntity = new SegmentEntity("../game/src/main/resources/shapeFiles/legacy/testShape.psff",new Vec2(1400,600), 0, 5);

        try {
            SegmentDataManager.saveExternal(new File("./playerShape.psff"), player.segments);
            SegmentDataManager.saveExternal(new File("./testShape.psff"), testEntity.segments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
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
