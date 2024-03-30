package com.lukullu.tbck.gameObjects.gameplayObjects;

import com.lukullu.tbck.enums.Shapes;
import com.lukullu.tbck.utils.Polygon;
import com.lukullu.tbck.utils.Vec2;

import java.util.ArrayList;

public class MeshObject extends GameplayObject
{

    public MeshObject(Shapes shapeDesc, Vec2 position, double rotation, double scaling)
    {
        super(shapeDesc, position, rotation, scaling);
        setPolygons(constructTriangles());
    }

    public MeshObject()
    {

    }

    private ArrayList<Polygon> constructTriangles()
    {
        ArrayList<Polygon> acc = new ArrayList<>();
        for(Polygon polygon : getPolygons())
            acc.addAll(constructTrianglesRec(new ArrayList<>(),polygon.getVertices(),1));
        return acc;
    }

    // depth has to start at 1!
    private ArrayList<Polygon> constructTrianglesRec(ArrayList<Polygon> triangles, ArrayList<Vec2> vertices, int depth)
    {

        // Recursion Beginning
        if(Math.ceil(vertices.size() / 2.0) < 3)
        {
            for (int i = 0; i < vertices.size()-2; i++)
            {
                ArrayList<Vec2> triangle = new ArrayList<>();
                triangle.add(vertices.get(i*2));
                triangle.add(vertices.get(i*2+1));
                triangle.add(vertices.get((i*2+2) % vertices.size()));
                triangles.add(new Polygon(triangle));
            }
            return triangles;
        }

        // Recursion Body
        int trianglesInLayer = (int) Math.floor(shape.size() / Math.pow(2,depth));
        ArrayList<Vec2> newVertices = new ArrayList<>();
        for(int i = 0; i < trianglesInLayer; i++)
        {
            ArrayList<Vec2> triangle = new ArrayList<>();
            triangle.add(vertices.get(i*2));
            triangle.add(vertices.get(i*2+1));
            triangle.add(vertices.get((i*2+2) % vertices.size()));
            triangles.add(new Polygon(triangle));

            newVertices.add(vertices.get((i*2+2) % vertices.size()));
        }

        if(trianglesInLayer * Math.pow(2,depth) != shape.size())
        {
            newVertices.add(vertices.get(0));
        }

        return constructTrianglesRec(triangles,newVertices,depth+1);
    }

}
