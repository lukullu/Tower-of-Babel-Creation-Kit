package com.lukullu.rayMarchingExperiment;

import com.kilix.processing.ExtendedPApplet;
import com.lukullu.rayMarchingExperiment.objects.FlashLight;
import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.GameplayObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.MetaObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.StaticObject;
import com.lukullu.tbck.utils.DebugUtil;
import com.lukullu.tbck.utils.DeltaTimer;
import com.lukullu.tbck.utils.GameObjectMultiHashMap;
import com.lukullu.tbck.utils.InputManager;
import com.lukullu.undersquare.Constants;
import com.lukullu.undersquare.components.Camera;
import com.lukullu.undersquare.entities.Player;
import com.lukullu.undersquare.objectTypes.Entity;
import com.lukullu.undersquare.objectTypes.Static;
import com.tbck.data.entity.SegmentDataManager;
import com.tbck.math.MyMath;
import com.tbck.math.Vec2;
import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlashLightExperiment extends ExtendedPApplet
{

    public static GameObjectMultiHashMap gameObjects = new GameObjectMultiHashMap();
    public static ArrayList<GameplayObject> toKill = new ArrayList<>();
    public static ArrayList<GameplayObject> toBirth = new ArrayList<>();

    public void setup()
    {

        gameObjects.putEntity(new FlashLight("/shapeFiles/playerShape.psff",new Vec2(600,600) , 0, 40));
        gameObjects.putStatic(new Static    ("/shapeFiles/playerShape.psff",new Vec2(800,500) , PI, 20));
        gameObjects.putStatic(new Static    ("/shapeFiles/playerShape.psff",new Vec2(700,200) , PI, 40));
        gameObjects.putStatic(new Static    ("/shapeFiles/playerShape.psff",new Vec2(700,800) , PI, 30));

    }

    public void draw()
    {

        background(40);

        // calc new frame-time
        DeltaTimer.getInstance().update();

        // Display FPS
        DebugUtil.getInstance().addDynamicText(1/(DeltaTimer.getInstance().getDeltaTime()) + " FPS");

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
        if(gameObjects.get(EntityObject.class) != null)
            for (var gameObject : gameObjects.get(EntityObject.class))
                ((ICollidableObject) gameObject).dynamicCollisionUpdate((List<EntityObject>)(Object)gameObjects.get(EntityObject.class), Constants.COLLISION_RECURSION_MAX_DEPTH);

        if(gameObjects.get(StaticObject.class) != null)
            for(var gameObject : gameObjects.get(StaticObject.class))
                ((ICollidableObject) gameObject).staticCollisionUpdate((List<EntityObject>)(Object)gameObjects.get(EntityObject.class), Constants.COLLISION_RECURSION_MAX_DEPTH);

        if(gameObjects.get(MetaObject.class) != null)
            for(var gameObject : gameObjects.get(MetaObject.class))
                ((ICollidableObject) gameObject).staticCollisionUpdate((List<EntityObject>)(Object)gameObjects.get(EntityObject.class), Constants.COLLISION_RECURSION_MAX_DEPTH);

        // draw every GameObject
        /*for (var entry : gameObjects.entrySet())
        {
            //TODO Draw hierarchy
            Class<?> key = entry.getKey();
            List<GameplayObject> value = entry.getValue();
            for (var gameObject : value)
            {
                gameObject.paint();
            }
        }*/

        // Display Debug Messages
        DebugUtil.getInstance().update();

        for (GameplayObject obj : toKill)
            gameObjects.remove(obj);

        for (GameplayObject obj : toBirth)
            gameObjects.putEntity(obj);

        if(!(toBirth.isEmpty() && toKill.isEmpty()))
        {
            toKill = new ArrayList<>();
            toBirth = new ArrayList<>();
        }

    }

    public static GameObjectMultiHashMap getGameObjects() { return gameObjects; }
    public void keyPressed() { InputManager.getInstance().keyPressed(); }
    public void keyReleased() { InputManager.getInstance().keyReleased(); }

}
