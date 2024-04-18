package com.lukullu.rayMarchingExperiment.utils;

import com.kilix.processing.ProcessingClass;
import com.lukullu.tbck.gameObjects.gameplayObjects.GameplayObject;
import com.tbck.data.entity.SegmentData;
import com.tbck.math.MyMath;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import java.util.ArrayList;

public class RayMarching
{

    private static final int RAY_MARCHING_REC_MAX_DEPTH = 10;
    private static final double DISTANCE_THRESHOLD_LOWER = 20;
    private static final double DISTANCE_THRESHOLD_UPPER = 1E100;

    public static Vec2 rayMarching(GameplayObject origin, double angle, ArrayList<GameplayObject> objects)
    {
        if(objects.isEmpty())
            return null;

        return rayMarchingRec(origin.getPosition(), origin, angle, objects, RAY_MARCHING_REC_MAX_DEPTH);
    }

    private static Vec2 rayMarchingRec(Vec2 origin, GameplayObject originObj, double angle, ArrayList<GameplayObject> objects, int depth)
    {

        // return if max depth is reached
        if(depth <= 0)
            return null;

        // calc min distance to objects
        double minDistance = Double.MAX_VALUE;
        for(GameplayObject obj : objects)
            if(!obj.equals(originObj))

                // calc distance to all polygons of gameplayObject
                for (Polygon polygon : obj.getPolygons())

                    // calc line intersection points
                    for (int i = 0; i < polygon.getVertices().size(); i++) {
                        int j = (i + 1) % polygon.getVertices().size();

                        Vec2 intersectionPoint = MyMath.lineSegmentIntersection(
                                origin,
                                obj.getPosition(),
                                polygon.getVertices().get(i),
                                polygon.getVertices().get(j));


                        if(intersectionPoint == null)
                            continue;

                        double distance = Vec2.distance(origin, intersectionPoint);

                        if (distance < minDistance)
                            minDistance = distance;

                    }

        // calc origin for next step
        Vec2 newOrigin = origin.add(Vec2.vec2FromAngle(angle,minDistance));

        System.out.println(minDistance);

        if(minDistance < DISTANCE_THRESHOLD_LOWER)
        {
            System.out.println("YAY");
            return newOrigin;
        }

        if(minDistance > DISTANCE_THRESHOLD_UPPER)
        {
            return null;
        }


        return rayMarchingRec(newOrigin, originObj, angle, objects, depth-1);
    }
}
