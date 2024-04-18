package com.lukullu.tbck.utils;

import com.kilix.processing.ProcessingClass;
import com.lukullu.tbck.gameObjects.ICollidableObject;
import com.tbck.math.LineSegment;
import com.tbck.math.MyMath;
import com.tbck.math.Polygon;
import com.tbck.math.Vec2;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Collision implements ProcessingClass
{
    public static CollisionResult collisionResolutionSAT(ArrayList<Vec2> polygon1, Vec2 polygonCenter1, ArrayList<Vec2> polygon2, Vec2 polygonCenter2, ICollidableObject collider)
    {
        double overlap = Double.MAX_VALUE;
        double minOverlap = Double.MAX_VALUE;
        LineSegment transformationLine = new LineSegment(Vec2.ZERO_VECTOR2,Vec2.ZERO_VECTOR2);
        Vec2 transformationAxis = Vec2.ZERO_VECTOR2;
        int axisOriginIndex = -1;
        ArrayList<Vec2> axisOrigin = null;

        ArrayList<Vec2> poly1 = polygon1;
        ArrayList<Vec2> poly2 = polygon2;

        for(int i = 0; i < 2; i++)
        {
            if(i == 1)
            {
                poly2 = polygon1;
                poly1 = polygon2;
            }

            for (int a = 0; a < poly1.size(); a++)
            {
                int b = (a+1) % poly1.size();

                Vec2 projectionAxis = new Vec2(
                        -(poly1.get(b).y - poly1.get(a).y),
                        poly1.get(b).x - poly1.get(a).x).normalise();

                double maxPoly1 = -Double.MAX_VALUE;
                double minPoly1 = Double.MAX_VALUE;
                double maxPoly2 = -Double.MAX_VALUE;
                double minPoly2 = Double.MAX_VALUE;

                for(int p = 0; p < poly1.size(); p++)
                {
                    double dotProduct = poly1.get(p).x * projectionAxis.x + poly1.get(p).y * projectionAxis.y;
                    maxPoly1 = Math.max(maxPoly1,dotProduct);
                    minPoly1 = Math.min(minPoly1,dotProduct);
                }

                for(int p = 0; p < poly2.size(); p++)
                {
                    double dotProduct = poly2.get(p).x * projectionAxis.x + poly2.get(p).y * projectionAxis.y;
                    maxPoly2 = Math.max(maxPoly2,dotProduct);
                    minPoly2 = Math.min(minPoly2,dotProduct);
                }

                if(!(maxPoly2 >= minPoly1 && maxPoly1 >= minPoly2)){return new CollisionResult(false);}


                overlap = Math.min(maxPoly1,maxPoly2) - Math.max(minPoly1,minPoly2);

                if(overlap < minOverlap)
                {
                    minOverlap = overlap;
                    transformationLine = new LineSegment(poly1.get(a),poly1.get(b));
                    transformationAxis = projectionAxis;
                    axisOriginIndex = i;
                    axisOrigin = poly1;
                }
            }
        }
        return new CollisionResult(true, collider, null, null, null, axisOriginIndex == 0, null,minOverlap,transformationAxis);
    }

    public static CollisionResult interpretCollisionDetectionSAT(ArrayList<Vec2> polygon1, Vec2 polygonCenter1, ArrayList<Vec2> polygon2, Vec2 polygonCenter2, ICollidableObject collider)
    {

        CollisionResult res = collisionResolutionSAT(polygon1,polygonCenter1,polygon2,polygonCenter2,collider);

        if(!res.collisionCheck)
            return res;

        Vec2 delta = res.transformationAxis.multiply(res.minOverlap*1.0001);

        Vec2 finalDelta = delta;
        ArrayList<Vec2> postPolygon = new ArrayList<>(polygon1.stream().map((v)->{return v.add(finalDelta);}).toList());
        CollisionResult collisionAfterDelta = Collision.collisionResolutionSAT(postPolygon,polygonCenter1.add(delta),polygon2,polygonCenter2,collider);

        if(collisionAfterDelta.collisionCheck)
        {
            delta = res.transformationAxis.multiply(res.minOverlap*-1);
        }
        else
        {
            delta = res.transformationAxis.multiply(res.minOverlap*1);
        }

        res.delta = delta;

        return res;
    }

    // ToDo: Test
    public static boolean pointPolygonCollision(Vec2 point, ArrayList<Vec2> polygon)
    {

        Vec2 polygonCenter = Polygon.calcPos(polygon);
        Vec2 lineFromPointToPolygonCenter = polygonCenter.subtract(point);

        for(int i = 0; i < polygon.size(); i++)
        {
            int j = (i+1)%polygon.size();

            if(MyMath.lineSegmentIntersectionCheck(point,lineFromPointToPolygonCenter,polygon.get(i),polygon.get(j)))
                return false;

        }
        return true;
    }


}
