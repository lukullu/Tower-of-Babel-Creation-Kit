package com.lukullu.utils;

import com.kilix.processing.ProcessingClass;
import com.lukullu.gameObjects.gameplayObjects.GameplayObject;

import java.util.ArrayList;

public class Collision
{

    public static boolean collisionCheckSAT(GameplayObject polygon1, GameplayObject polygon2)
    {

        GameplayObject poly1 = polygon1;
        GameplayObject poly2 = polygon2;

        for(int i = 0; i < 2; i++)
        {
            if(i == 1)
            {
                poly2 = polygon1;
                poly1 = polygon2;
            }

            for (int a = 0; a < poly1.getVertices().size(); a++)
            {
                int b = (a+1) % poly1.getVertices().size();

                Vec2 projectionAxis = new Vec2(
                        -(poly1.getVertices().get(b).y - poly1.getVertices().get(a).y),
                        poly1.getVertices().get(b).x - poly1.getVertices().get(a).x);

                double maxPoly1 = -Double.MAX_VALUE;
                double minPoly1 = Double.MAX_VALUE;
                double maxPoly2 = -Double.MAX_VALUE;
                double minPoly2 = Double.MAX_VALUE;

                for(int p = 0; p < poly1.getVertices().size(); p++)
                {
                    double dotProduct = poly1.getVertices().get(p).x * projectionAxis.x + poly1.getVertices().get(p).y * projectionAxis.y;
                    maxPoly1 = Math.max(maxPoly1,dotProduct);
                    minPoly1 = Math.min(minPoly1,dotProduct);
                }

                for(int p = 0; p < poly2.getVertices().size(); p++)
                {
                    double dotProduct = poly2.getVertices().get(p).x * projectionAxis.x + poly2.getVertices().get(p).y * projectionAxis.y;
                    maxPoly2 = Math.max(maxPoly2,dotProduct);
                    minPoly2 = Math.min(minPoly2,dotProduct);
                }

                if(!(maxPoly2 >= minPoly1 && maxPoly1 >= minPoly2)){return false;}

            }
        }
        return true;
    }

    public static CollisionResult collisionResolutionSAT(GameplayObject polygon1, GameplayObject polygon2)
    {
        double overlap = Double.MAX_VALUE;
        double minOverlap = Double.MAX_VALUE;
        Vec2 transformationAxis = Vec2.ZERO_VECTOR2;
        int axisOriginIndex = -1;

        GameplayObject poly1 = polygon1;
        GameplayObject poly2 = polygon2;

        for(int i = 0; i < 2; i++)
        {
            if(i == 1)
            {
                poly2 = polygon1;
                poly1 = polygon2;
            }

            for (int a = 0; a < poly1.getVertices().size(); a++)
            {
                int b = (a+1) % poly1.getVertices().size();

                Vec2 projectionAxis = new Vec2(
                        -(poly1.getVertices().get(b).y - poly1.getVertices().get(a).y),
                        poly1.getVertices().get(b).x - poly1.getVertices().get(a).x).normalise();

                double maxPoly1 = -Double.MAX_VALUE;
                double minPoly1 = Double.MAX_VALUE;
                double maxPoly2 = -Double.MAX_VALUE;
                double minPoly2 = Double.MAX_VALUE;

                for(int p = 0; p < poly1.getVertices().size(); p++)
                {
                    double dotProduct = poly1.getVertices().get(p).x * projectionAxis.x + poly1.getVertices().get(p).y * projectionAxis.y;
                    maxPoly1 = Math.max(maxPoly1,dotProduct);
                    minPoly1 = Math.min(minPoly1,dotProduct);
                }

                for(int p = 0; p < poly2.getVertices().size(); p++)
                {
                    double dotProduct = poly2.getVertices().get(p).x * projectionAxis.x + poly2.getVertices().get(p).y * projectionAxis.y;
                    maxPoly2 = Math.max(maxPoly2,dotProduct);
                    minPoly2 = Math.min(minPoly2,dotProduct);
                }

                overlap = Math.min(maxPoly1,maxPoly2) - Math.max(minPoly1,minPoly2);

                if(overlap < minOverlap)
                {
                    minOverlap = overlap;
                    transformationAxis = projectionAxis.normalise();
                    axisOriginIndex = i;
                }

                if(!(maxPoly2 >= minPoly1 && maxPoly1 >= minPoly2)){return new CollisionResult(false,Vec2.ZERO_VECTOR2,false);}

            }
        }

        Vec2 generalDirection = polygon1.getPosition().subtract(polygon2.getPosition());

        Vec2 delta = transformationAxis.multiply(-minOverlap).align(generalDirection);

        return new CollisionResult(true, delta, axisOriginIndex == 0);
    }

}
