package com.lukullu.rayMarchingExperiment.objects;

import com.lukullu.rayMarchingExperiment.FlashLightExperiment;
import com.lukullu.rayMarchingExperiment.utils.RayMarching;
import com.lukullu.tbck.gameObjects.gameplayObjects.EntityObject;
import com.lukullu.tbck.gameObjects.gameplayObjects.GameplayObject;
import com.lukullu.tbck.utils.GameObjectMultiHashMap;
import com.lukullu.undersquare.UnderSquare3;
import com.lukullu.undersquare.entities.Player;
import com.lukullu.undersquare.objectTypes.Entity;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;
import java.util.List;

public class FlashLight extends Player
{

    private static final int RAY_COUNT = 360;

    public FlashLight(String psff_resource, Vec2 position, double rotation, double scaling) {
        super(psff_resource, position, rotation, scaling);
    }

    @Override
    public void update()
    {
        super.update();

        // calc points where ray hits objects
        ArrayList<Vec2> hits = new ArrayList<>();
        GameObjectMultiHashMap gameObjects = FlashLightExperiment.getGameObjects();
        for (var entry : gameObjects.entrySet()) {
            Class<?> key = entry.getKey();
            ArrayList<GameplayObject> objects = (ArrayList<GameplayObject>) entry.getValue();

            for(int i = 0; i < RAY_COUNT; i++)
            {

                Vec2 result = RayMarching.rayMarching(this,i * (Math.PI*2)/RAY_COUNT,objects);

                if(result != null)
                    hits.add(result);
            }
        }

        for(Vec2 hit : hits)
        {
            fill(255);
            ellipse((float)hit.x,(float)hit.y,5,5);
        }

    }

}
