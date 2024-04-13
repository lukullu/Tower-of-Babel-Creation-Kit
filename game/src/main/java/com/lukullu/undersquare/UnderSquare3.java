package com.lukullu.undersquare;

import com.kilix.processing.ExtendedPApplet;

import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.GameplayObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.MetaObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.StaticObject;
import com.lukullu.tbck.utils.*;
import com.lukullu.undersquare.components.Camera;
import com.lukullu.undersquare.entities.Player;
import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.lukullu.undersquare.objectTypes.Debris;
import com.lukullu.undersquare.objectTypes.Entity;
import com.lukullu.undersquare.objectTypes.Meta;
import com.lukullu.undersquare.objectTypes.Static;
import com.lukullu.tbck.utils.DebugUtil;
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

        gameObjects.putEntity(new Player("/shapeFiles/playerShape.psff",new Vec2(600,600) , 0, 40));
        gameObjects.putEntity(new Entity("/shapeFiles/playerShape.psff",new Vec2(900,600) , 0, 40));
        gameObjects.putEntity(new Entity("/shapeFiles/segmentDemo.psff",new Vec2(1500,600), 0, 40));
        gameObjects.putEntity(new Debris("/shapeFiles/playerShape.psff",new Vec2(1200,600), 0, 10,3));
        gameObjects.putMeta(  new Meta  ("/shapeFiles/playerShape.psff",new Vec2(1000,700), 0, 40,(nil)->{System.out.println("heya");},false));
        gameObjects.putMeta(  new Meta  ("/shapeFiles/playerShape.psff",new Vec2(400,300) , 0, 40,(res)->{res.collider.reset();},false));
        gameObjects.putStatic(new Static("/shapeFiles/playerShape.psff",new Vec2(800,500) , PI, 40));

        // Write instructions
        DebugUtil.getInstance().addStaticText("Move with: [W, A, S, D]");
        DebugUtil.getInstance().addStaticText("Rotate with: [Q, E]");
        DebugUtil.getInstance().addStaticText("Scale with: [R, F]");
        DebugUtil.getInstance().addStaticText("Activate Slow-Motion with: [L] (Keep Pressed)");
        DebugUtil.getInstance().addStaticText("Cycle Between Cameras with: [C]");
        DebugUtil.getInstance().addStaticText("Activate Experimental Features with: [K] (Keep Pressed)");

        Camera.getInstance().setPossibleTargets((ArrayList<GameplayObject>) gameObjects.get(EntityObject.class));
    }

    public void draw()
    {

        background(40);

        // calc new frame-time
        DeltaTimer.getInstance().update();
        // update Camera
        Camera.getInstance().update();

        // Display FPS
        DebugUtil.getInstance().addDynamicText(1/(DeltaTimer.getInstance().getDeltaTime()) + " FPS");
        // Display Current Camera
        DebugUtil.getInstance().addDynamicText("Current Camera: " + Camera.getInstance().currentTargetIndex);

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
            // ToDo: Space Partitioning with KD-Tree
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
                    ((ICollidableObject) gameObject).staticCollisionUpdate(Constants.COLLISION_RECURSION_MAX_DEPTH);
                }

            }
        }

        // adjust for Camera
        Camera.getInstance().push();

        // draw every GameObject
        for (var entry : gameObjects.entrySet())
        {
            //TODO Draw hierarchy
            Class<?> key = entry.getKey();
            List<GameplayObject> value = entry.getValue();
            for (var gameObject : value)
            {
                gameObject.paint();
            }
        }

        Camera.getInstance().pop();

        // Display Debug Messages
        DebugUtil.getInstance().update();

        for (GameplayObject obj : toKill)
            gameObjects.remove(obj);

        for (GameplayObject obj : toBirth)
            gameObjects.putEntity(obj);

        if(!(toBirth.isEmpty() && toKill.isEmpty()))
        {
            Camera.getInstance().setPossibleTargets((ArrayList<GameplayObject>) gameObjects.get(EntityObject.class));
            toKill = new ArrayList<>();
            toBirth = new ArrayList<>();
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
