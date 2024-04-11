package com.lukullu.undersquare;

import com.kilix.processing.ExtendedPApplet;

import com.lukullu.tbck.gameObjects.IGameObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.GameplayObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.MetaObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.StaticObject;
import com.lukullu.tbck.utils.*;
import com.lukullu.undersquare.entities.Player;
import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.lukullu.undersquare.objectTypes.Debris;
import com.lukullu.undersquare.objectTypes.Entity;
import com.lukullu.undersquare.objectTypes.Meta;
import com.lukullu.undersquare.objectTypes.Static;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.Vec2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UnderSquare3 extends ExtendedPApplet {

    public static GameObjectMultiHashMap gameObjects = new GameObjectMultiHashMap();
    public static ArrayList<GameplayObject> toKill = new ArrayList<>();
    public static ArrayList<GameplayObject> toBirth = new ArrayList<>();

    public void setup()
    {
        // "../game/src/main/resources/shapeFiles/playerShape.psff"

        gameObjects.putEntity(new Player("/shapeFiles/playerShape.psff", new Vec2(600,600), 0, 1));
        gameObjects.putEntity(new Entity("/shapeFiles/playerShape.psff",new Vec2(900,600) , 0, 2));
        gameObjects.putEntity(new Entity("/shapeFiles/segmentDemo.psff",new Vec2(1500,600), 0, 40));
        //gameObjects.putEntity(new Debris("/shapeFiles/playerShape.psff",new Vec2(1200,600), 0, 0.1,3));
        //gameObjects.putMeta(  new Meta  ("/shapeFiles/testShape.psff"  ,new Vec2(1000,700), 0, 1,(nil)->{System.out.println("heya");},false));
        //gameObjects.putMeta(  new Meta  ("/shapeFiles/testShape.psff"  ,new Vec2(400,300) , 0, 1,(res)->{res.collider.reset();},false));
        //gameObjects.putStatic(new Static("/shapeFiles/testShape.psff"  ,new Vec2(800,500) , PI, 4));

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
            List<GameplayObject> value = entry.getValue();
            for (var gameObject : value)
            {
                gameObject.update();
            }
        }

        // collide every GameObject
        for (var entry : gameObjects.entrySet())
        {
            Class<?> key = entry.getKey();
            List<GameplayObject> value = entry.getValue();
            for (var gameObject : value)
            {
                if(gameObject instanceof EntityObject)
                {
                    ((ICollidableObject) gameObject).dynamicCollisionUpdate(Constants.COLLISION_RECURSION_MAX_DEPTH);
                }
                else if(gameObject instanceof StaticObject || gameObject instanceof MetaObject)
                {
                    ((ICollidableObject) gameObject).staticCollisionUpdate();
                }

            }
        }

        // draw every GameObject
        for (var entry : gameObjects.entrySet())
        {
            Class<?> key = entry.getKey();
            List<GameplayObject> value = entry.getValue();
            for (var gameObject : value)
            {
                gameObject.paint(); //TODO Draw hierarchy
            }
        }

        for (GameplayObject obj : toKill)
            gameObjects.remove(obj);

        for (GameplayObject obj : toBirth)
        {
            gameObjects.putEntity(obj);
        }

        toKill = new ArrayList<>();
        toBirth = new ArrayList<>();

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
