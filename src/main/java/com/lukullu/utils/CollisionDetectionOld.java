package com.lukullu.utils;

import com.lukullu.gameObjects.gameplayObjects.GameplayObject;

import java.util.ArrayList;

public class CollisionDetectionOld {

    public static ArrayList<Vec2> shapeOverlap(GameplayObject poly1, GameplayObject poly2)
    {
        ArrayList<Vec2> output = null;

        Vec2 midPoint = poly1.getPosition();
        output = polygonIntersectionCheck(poly2, poly1, output, midPoint);

        midPoint = poly2.getPosition();
        output = polygonIntersectionCheck(poly1, poly2, output, midPoint);

        return output;
    }

    public static ArrayList<Vec2> shapeOverlap(Vec2 midPos, ArrayList<Vec2> poly1, GameplayObject poly2)
    {
        ArrayList<Vec2> output = null;

        Vec2 midPoint = midPos;
        output = polygonIntersectionCheck(poly2.getVertices(), poly1, output, midPoint);

        midPoint = poly2.getPosition();
        output = polygonIntersectionCheck(poly1, poly2.getVertices(), output, midPoint);

        return output;
    }

    public static boolean shapeOverlapDisplacement(GameplayObject poly1, GameplayObject poly2)
    {
        boolean output = false;

        Vec2 displacement;

        Vec2 midPoint = poly1.getPosition();
        displacement = polygonIntersectionDisplacementCheck(poly2, poly1, midPoint);

        if(!displacement.equals(Vec2.ZERO_VECTOR2)) System.out.println("Displacement 1: "+displacement.toString());

        if(poly1.getPushPriority() <= poly2.getPushPriority())
        {
            poly1.updatePos(displacement.multiply(-1));
        }
        else
        {
            poly2.updatePos(displacement.multiply(1));
        }

        midPoint = poly2.getPosition();
        displacement = polygonIntersectionDisplacementCheck(poly1, poly2, midPoint);

        if(!displacement.equals(Vec2.ZERO_VECTOR2)) System.out.println("Displacement 2: "+displacement.toString());
        if(poly1.getPushPriority() <= poly2.getPushPriority())
        {
            poly1.updatePos(displacement.multiply(-1));
        }
        else
        {
            poly2.updatePos(displacement.multiply(1));
        }

        return output;
    }

    public static boolean areShapesOverlapping(GameplayObject poly1, GameplayObject poly2)
    {
        return shapeOverlap(poly1,poly2) != null;
    }

    public static boolean areShapesOverlapping(Vec2 midPos, ArrayList<Vec2> poly1, GameplayObject poly2)
    {
        return shapeOverlap(midPos,poly1,poly2) != null;
    }

    private static ArrayList<Vec2> polygonIntersectionCheck(GameplayObject poly1, GameplayObject poly2, ArrayList<Vec2> output, Vec2 midPoint) {
        for (Vec2 poly1Vertex : poly2.getVertices())
        {
            for (int i = 0; i < poly1.getVertices().size(); i++) {

                Vec2 poly2Vertex1 = poly1.getVertices().get(i);
                Vec2 poly2Vertex2 = poly1.getVertices().get((i + 1) % poly1.getVertices().size());

                Vec2 intersectionPoint = MyMath.lineSegmentIntersection( midPoint, poly1Vertex, poly2Vertex1, poly2Vertex2 );

                if(intersectionPoint != null)
                {
                    if(output == null) { output = new ArrayList<>(); }
                    output.add(intersectionPoint);
                }
            }
        }
        return output;
    }

    private static ArrayList<Vec2> polygonIntersectionCheck(ArrayList<Vec2> poly1, ArrayList<Vec2> poly2, ArrayList<Vec2> output, Vec2 midPoint) {
        for (Vec2 poly1Vertex : poly2)
        {
            for (int i = 0; i < poly1.size(); i++) {

                Vec2 poly2Vertex1 = poly1.get(i);
                Vec2 poly2Vertex2 = poly1.get((i + 1) % poly1.size());

                Vec2 intersectionPoint = MyMath.lineSegmentIntersection( midPoint, poly1Vertex, poly2Vertex1, poly2Vertex2 );

                if(intersectionPoint != null)
                {
                    if(output == null) { output = new ArrayList<>(); }
                    output.add(intersectionPoint);
                }
            }
        }
        return output;
    }

    private static Vec2 polygonIntersectionDisplacementCheck(GameplayObject poly1, GameplayObject poly2, Vec2 midPoint) {

        int displacement_counter = 0;

        Vec2 output = Vec2.ZERO_VECTOR2;
        for (Vec2 poly1Vertex : poly2.getVertices())
        {
            for (int i = 0; i < poly1.getVertices().size(); i++) {

                Vec2 poly2Vertex1 = poly1.getVertices().get(i);
                Vec2 poly2Vertex2 = poly1.getVertices().get((i + 1) % poly1.getVertices().size());

                Vec2 displacement = MyMath.lineSegmentIntersectionDisplacement( midPoint, poly1Vertex, poly2Vertex1, poly2Vertex2 );

                if(displacement != null)
                {
                    output = output.add(displacement);
                    displacement_counter++;
                }
            }
        }
        // TODO: TESTING
        //output = output.divide(displacement_counter);
        return output;
    }

    public static boolean shapeOverlapSAT(GameplayObject polygon1, GameplayObject polygon2)
    {

        double overlap = Double.MAX_VALUE;

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

                Vec2 axisProjection = new Vec2(
                        -(poly1.getVertices().get(b).y - poly1.getVertices().get(a).y),
                        poly1.getVertices().get(b).x - poly1.getVertices().get(a).x);

                double maxPoly1 = -Double.MAX_VALUE;
                double minPoly1 = Double.MAX_VALUE;

                for(int p = 0; p < poly1.getVertices().size(); p++)
                {
                    double q = poly1.getVertices().get(p).x * axisProjection.x + poly1.getVertices().get(p).y * axisProjection.y;
                    maxPoly1 = Math.max(maxPoly1,q);
                    minPoly1 = Math.min(minPoly1,q);
                }

                double maxPoly2 = -Double.MAX_VALUE;
                double minPoly2 = Double.MAX_VALUE;

                for(int p = 0; p < poly2.getVertices().size(); p++)
                {
                    double q = poly2.getVertices().get(p).x * axisProjection.x + poly2.getVertices().get(p).y * axisProjection.y;
                    maxPoly2 = Math.max(maxPoly2,q);
                    minPoly2 = Math.min(minPoly2,q);
                }

                overlap = Math.min(Math.min(maxPoly1,maxPoly2) - Math.max(minPoly1,minPoly2),overlap);

                if(!(maxPoly2 >= minPoly1 && maxPoly1 >= minPoly2)){return false;}

            }
        }

        Vec2 delta = new Vec2(polygon2.getPosition().x - polygon1.getPosition().x,polygon2.getPosition().y - polygon1.getPosition().y);

        double distance = Math.sqrt(delta.x * delta.x + delta.y + delta.y);

        /*polygon1.updatePos(new Vec2(
                -(overlap * (delta.x / distance)),
                -(overlap * (delta.y / distance))));
         */
        return true;
    }


}
