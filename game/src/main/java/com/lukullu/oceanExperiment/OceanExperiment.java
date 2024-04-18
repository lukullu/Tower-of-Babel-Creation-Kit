package com.lukullu.oceanExperiment;

import com.kilix.processing.ExtendedPApplet;
import com.kilix.processing.ProcessingClass;
import com.lukullu.oceanExperiment.simulation.H2O;
import com.lukullu.oceanExperiment.simulation.Simulation;
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

public class OceanExperiment extends ExtendedPApplet {

    public static GameObjectMultiHashMap gameObjects = new GameObjectMultiHashMap();
    public static ArrayList<GameplayObject> toKill = new ArrayList<>();
    public static ArrayList<GameplayObject> toBirth = new ArrayList<>();

    public static Simulation sim;

    public void setup()
    {
        sim = new Simulation(600);
    }

    public void draw()
    {

        background(40);

        // calc new frame-time
        DeltaTimer.getInstance().update();
        // Display FPS
        DebugUtil.getInstance().addDynamicText(1/(DeltaTimer.getInstance().getDeltaTime()) + " FPS");
        DebugUtil.getInstance().addDynamicText("Target: " +600d/(1920d*1080d) + " \nDensity " + H2O.calcSurroundingDensity(new Vec2(width/2d,height/2d)));

        sim.update();
        sim.paint();

        // Display Debug Messages
        DebugUtil.getInstance().update();

    }

    public static GameObjectMultiHashMap getGameObjects() { return gameObjects; }

    public static Simulation getSim() {
        return sim;
    }

    public void keyPressed() { InputManager.getInstance().keyPressed(); }
    public void keyReleased() { InputManager.getInstance().keyReleased(); }

}
